package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@MapperScan(basePackages = "com.example.biz.dao")
//启动类中mapper扫描，把dao下的接口交给Spring管理
public class Application {

	public static void main(String[] args) {
		//启动类，run确定当前工程类型，开启对应的默认的配置，内部创建工程
		SpringApplication.run(Application.class, args);
	}

}

