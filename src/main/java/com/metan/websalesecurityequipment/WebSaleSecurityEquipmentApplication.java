package com.metan.websalesecurityequipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class WebSaleSecurityEquipmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebSaleSecurityEquipmentApplication.class, args);
    }

}
