package org.material.managementweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.material.managementservice",
        "org.material.managementfacade",
        "org.material.managementweb"})
public class MaterialManagementMapperApplication {

    public static void main (String[] args) {
        SpringApplication.run(MaterialManagementMapperApplication.class, args);
    }
}
