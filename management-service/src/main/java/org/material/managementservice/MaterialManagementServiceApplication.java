package org.material.managementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.material.managementservice",
        "org.material.managementfacade"})
public class MaterialManagementServiceApplication {

    public static void main (String[] args) {
        SpringApplication.run(MaterialManagementServiceApplication.class, args);
    }
}
