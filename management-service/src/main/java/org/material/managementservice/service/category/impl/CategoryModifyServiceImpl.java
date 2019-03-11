package org.material.managementservice.service.category.impl;

import org.material.managementfacade.model.requestmodel.CatAddReq;
import org.material.managementfacade.model.requestmodel.CatDeleteReq;
import org.material.managementfacade.model.requestmodel.CategoryModifyNameRequest;
import org.material.managementfacade.model.tablemodel.MaterialCategoryModel;
import org.material.managementfacade.service.category.CategoryModifyService;
import org.material.managementservice.general.MaterialCategoryErrCode;
import org.material.managementservice.mapper.category.CategoryModifyMapper;
import org.material.managementservice.mapper.general.GeneralMapper;
import org.material.managementservice.service.info.impl.supplier.baseprop.BasePropModifyServiceSupplier;
import org.material.managementservice.service.info.impl.supplier.controlprop.ControlPropModifyServiceSupplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * @author cplayer on 2019-02-25.
 * @version 1.0
 */
@Component
public class CategoryModifyServiceImpl implements CategoryModifyService {
    private final static Logger logger = LoggerFactory.getLogger("zhuriLogger");
    @Autowired
    private CategoryModifyMapper categoryModifyMapper;
    @Autowired
    private GeneralMapper generalMapper;
    @Autowired
    private ControlPropModifyServiceSupplier controlPropModifyServiceSupplier;
    @Autowired
    private BasePropModifyServiceSupplier basePropModifyServiceSupplier;

    /**
     * 添加物料分类信息的功能函数（单次）
     *
     * @param request 请求被添加的物料分类信息
     * @return MaterialCategoryErrCode.successAddCategory 添加成功
     * MaterialCategoryErrCode.failedAddCategory 添加失败
     * @author cplayer
     * @date 2019-03-02 04:37
     */
    @Override
    public int addMaterialCategory (CatAddReq request) {
        // 先检查是否存在当前编码、id、名称
        int count = categoryModifyMapper.countMaterialCategoryByCode(request.getCode());
        if (count > 0) {
            return MaterialCategoryErrCode.existedCategoryCode;
        }
        count = categoryModifyMapper.countMaterialCategoryById(request.getParentId());
        if (count == 0) {
            return MaterialCategoryErrCode.notExistFatherCategory;
        }
        count = categoryModifyMapper.countMaterialCategoryByName(request.getName());
        if (count > 0) {
            return MaterialCategoryErrCode.existedCategoryName;
        }
        // 数据合法，允许添加
        MaterialCategoryModel param = new MaterialCategoryModel();
        param.setCode(request.getCode());
        param.setName(request.getName());
        param.setParentId(request.getParentId());
        param.setType(request.getType());
        int insertResult = categoryModifyMapper.insertMaterialCategoryByParam(param);
        logger.info(String.format("物料分类信息新增的记录id = %d。", param.getId()));
        if (insertResult > 0) {
            return MaterialCategoryErrCode.successAddCategory;
        }
        return MaterialCategoryErrCode.failedAddCategory;
    }

    /**
     * 修改物料分类信息的函数（单次）
     *
     * @param request 请求被修改的物料分类信息
     * @return MaterialCategoryErrCode.successModifyCategory 修改成功
     * MaterialCategoryErrCode.failedModifyCategory 修改失败
     * @author cplayer
     * @date 2019-03-02 05:00
     */
    @Override
    public int updateMaterialCategoryName (CategoryModifyNameRequest request) {
        MaterialCategoryModel param = new MaterialCategoryModel();
        // 确认原物料名称存在
        param.setName(request.getOldName());
        param.setParentId(request.getParentId());
        int count = categoryModifyMapper.countMaterialCategoryByParam(param);
        if (count == 0) {
            return MaterialCategoryErrCode.notExistModifyCategory;
        }
        param = new MaterialCategoryModel();
        // 确认新物料名称不存在
        param.setName(request.getNewName());
        param.setParentId(request.getParentId());
        count = categoryModifyMapper.countMaterialCategoryByParam(param);
        if (count > 0) {
            return MaterialCategoryErrCode.existNewCategoryName;
        }
        int updateResult = categoryModifyMapper.updateMaterialCategoryName(request.getOldName(),
                request.getNewName(),
                request.getParentId());
        if (updateResult > 0) {
            return MaterialCategoryErrCode.successModifyCategory;
        }
        return MaterialCategoryErrCode.failedModifyCategory;
    }

    /**
     * 删除物料分类信息的函数
     *
     * @param request 请求被删除的物料分类信息
     * @return MaterialCategoryErrCode.successDeleteCategory 删除成功
     * MaterialCategoryErrCode.failedDeleteCategory 删除失败
     * @author cplayer
     * @date 2019-03-02 05:17
     */
    @Override
    public int deleteMaterialCategory (CatDeleteReq request) {
        // 传入值必须唯一确定一条记录
        MaterialCategoryModel param = new MaterialCategoryModel();
        param.setId(request.getId());
        param.setCode(request.getCode());
        param.setName(request.getName());
        param.setParentId(request.getParentId());
        List<MaterialCategoryModel> resultList = generalMapper.getMaterialCategoryWithMaterialCategoryParams(param);
        // 待删除的记录不存在，删除终止。
        if (resultList.size() == 0) {
            logger.error(String.format("物料分类信息数据库记录不存在，其中id = %d, 编码 = %s, 名称 = %s, 父分类id = %d。",
                    request.getId(),
                    request.getCode(),
                    request.getName(),
                    request.getParentId()));
            return MaterialCategoryErrCode.notExistDeleteCategory;
        }
        // 数据库记录有重复，删除终止。
        if (resultList.size() > 1) {
            logger.error(String.format("物料分类信息数据库记录有重复，其中id = %d, 编码 = %s, 名称 = %s, 父分类id = %d。",
                    request.getId(),
                    request.getCode(),
                    request.getName(),
                    request.getParentId()));
            return MaterialCategoryErrCode.duplicatedDeleteCategory;
        }
        // 根据id与parentId关系将对应记录及其子类记录全部删除
        Deque<MaterialCategoryModel> queue = new ArrayDeque<>();
        queue.add(resultList.get(0));
        int failed = 0;
        int times = 0;
        while (!queue.isEmpty()) {
            times++;
            param = queue.pollFirst();
            queue.addAll(categoryModifyMapper.getMatarialCategoryByParentId(param.getId()));
            int deleteResult = categoryModifyMapper.deleteMaterialCategoryById(param.getId());
            // 若出错，继续删除
            if (deleteResult == 0) {
                logger.error(String.format("物料分类信息数据库删除出错，对应的数据不存在，数据为：" +
                                "父分类id = %d，名称 = %s。",
                        param.getParentId(),
                        param.getName()));
                failed++;
            }
            deleteResult = controlPropModifyServiceSupplier.deleteAllControlPropertyByCatId(param.getId());
            logger.info(String.format("删除对应的控制属性值的返回值为%d。", deleteResult));
            deleteResult = basePropModifyServiceSupplier.deleteAllMaterialBasePropByCatId(param.getId());
            logger.info(String.format("删除对应基本属性的返回值为%d。", deleteResult));
        }
        if (failed > 0) {
            logger.error(String.format("物料分类信息数据库删除出错，出错次数为%d，总删除次数为%d。",
                    failed,
                    times));
            return MaterialCategoryErrCode.failedDeleteCategory;
        } else {
            return MaterialCategoryErrCode.successDeleteCategory;
        }
    }
}
