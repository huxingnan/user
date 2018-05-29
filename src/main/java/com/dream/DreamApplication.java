package com.dream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.serviceregistry.AutoServiceRegistrationConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author huxingnan
 * @date 2018/5/9 17:25
 */
@ServletComponentScan
@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "com.dream.repository")
public class DreamApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(DreamApplication.class, args);
    }
}
