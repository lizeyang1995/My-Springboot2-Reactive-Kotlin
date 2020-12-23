package com.example.Myspringboot2reactivekotlin.data

import reactor.core.publisher.Mono

interface CounterRepository {
    fun up(): Mono<Long>
    fun down(): Mono<Long>
    fun get(): Mono<Long>
}