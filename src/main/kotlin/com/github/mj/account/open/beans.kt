package com.github.mj.account.open

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component

@Component
internal class MySpringBean : InitializingBean, DisposableBean {
    override fun afterPropertiesSet() {
        println("MySpringBean is initialized")
    }

    override fun destroy() {
        println("MySpringBean is destroyed")
    }
}

@Component
internal class MyKotlinBean {
    @PostConstruct
    fun postConstruct() {
        println("MyKotlin is initialized")
    }

    @PreDestroy
    fun preDestroy() {
        println("MyKotlin is destroyed")
    }
}