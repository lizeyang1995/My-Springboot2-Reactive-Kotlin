package com.example.Myspringboot2reactivekotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.example.Myspringboot2reactivekotlin.data.CounterRepository"])
class MySpringboot2ReactiveKotlinApplication

fun main(args: Array<String>) {
	runApplication<MySpringboot2ReactiveKotlinApplication>(*args)
}
