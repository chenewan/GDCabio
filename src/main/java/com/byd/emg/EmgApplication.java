package com.byd.emg;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@SpringBootApplication
//@EnableAutoConfiguration 用于自动配置。它会根据你的pom配置（实际上应该是根据具体的依赖）
//来判断出这是一个web应用还是maven应用，并会创建相应的XX...应用环境。
@EnableAutoConfiguration
@EnableTransactionManagement(proxyTargetClass = true)   //开启事物管理功能
@EnableScheduling//定时任务
//@EnableAsync
@MapperScan("com.byd.emg.mapper")
public class EmgApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(EmgApplication.class, args);
    }
    @Override//为了打包springboot项目
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}


