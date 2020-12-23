package com.example.Myspringboot2reactivekotlin.data

import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface CounterRepository {
    fun up(): Mono<Long>
    fun down(): Mono<Long>
    fun get(): Mono<Long>
}