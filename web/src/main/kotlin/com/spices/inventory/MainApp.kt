package com.spices.inventory

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.ws.rs.ext.Provider

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = ["com.spices.inventory"], includeFilters = [ComponentScan.Filter(Provider::class)])
@EnableJpaRepositories(basePackages = ["com.spices.inventory.persistence"])
@EnableJpaAuditing
@EnableTransactionManagement
class MainApp

fun main(args: Array<String>) {
    SpringApplication.run(MainApp::class.java, *args)
}