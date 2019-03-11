package org.material.managementweb.web;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cplayer on 2019-03-11 16:29
 * @version 1.0
 * 处理错误的控制器
 */
@Controller
public class WebErrorController implements ErrorController {
    /**
     * 配置404的默认返回页面为vue的主页面
     *
     * 若需要有对应的404页面在vue的主路由中处理。
     *
     * @author cplayer
     * @date 2019-03-11 16:34
     * @param request http请求
     *
     * @return 对应的页面
     *
     */
    @RequestMapping("/error")
    public String handleError (HttpServletRequest request) {
        return "/index.html";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
