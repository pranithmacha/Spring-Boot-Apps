package com.batch.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = {"com.batch.demo.data"})
@EnableJpaRepositories(basePackages = {"com.batch.demo.repository"})
public class DatasourceConfig {

    private Environment env;

    @Profile("!local")
    @Bean
    public void createJNDIRepository(){

    }

    @Autowired
    public DatasourceConfig(Environment env){
        this.env = env;
    }
}