package org.material.managementweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

// import org.springframework.boot.autoconfigure.domain.EntityScan;
// import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@ComponentScan(basePackages = {"org.material.managementservice",
        "org.material.managementfacade",
        "org.material.managementweb"})
@MapperScan({"org.material.managementservice.mapper.category",
                    "org.material.managementservice.mapper.general",
                    "org.material.managementservice.mapper.info",
                    "org.material.managementservice.mapper"})
// @EnableEurekaClient
public class MaterialManagementMapperApplication {

    public static void main (String[] args) {
        SpringApplication.run(MaterialManagementMapperApplication.class, args);
    }
}
