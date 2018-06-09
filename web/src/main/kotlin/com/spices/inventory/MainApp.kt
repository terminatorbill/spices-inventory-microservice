package com.spices.inventory

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication(scanBasePackages = ["com.spices.inventory"])
@EnableJpaRepositories(basePackages = ["com.spices.inventory.persistence"])
@EnableJpaAuditing
@EnableTransactionManagement
class MainApp

fun main(args: Array<String>) {
    SpringApplication.run(MainApp::class.java, *args)
}