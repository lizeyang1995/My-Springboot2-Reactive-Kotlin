package com.example.Myspringboot2reactivekotlin.api

import com.example.Myspringboot2reactivekotlin.data.CounterRepository
import com.example.Myspringboot2reactivekotlin.model.CounterDown
import com.example.Myspringboot2reactivekotlin.model.CounterState
import com.example.Myspringboot2reactivekotlin.model.CounterUp
import com.example.Myspringboot2reactivekotlin.service.EventBus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono

@Component
class CounterHandler(private val eventBus: EventBus,
                     private val counterRepository: CounterRepository) {

    fun get(serverRequest: ServerRequest): Mono<ServerResponse> =
            ServerResponse
                    .ok()
                    .body(
                            counterRepository.get()
                                    .map { CounterState(it) }
                    )

    fun up(serverRequest: ServerRequest): Mono<ServerResponse> =
            ServerResponse
                    .ok()
                    .body(
                            counterRepository.up()
                                    .map { CounterState(it) }
                                    .doOnNext { eventBus.publish(CounterUp(it.value)) }
                    )

    fun down(serverRequest: ServerRequest): Mono<ServerResponse> =
            ServerResponse
                    .ok()
                    .body(
                            counterRepository.down()
                                    .map { CounterState(it) }
                                    .doOnNext { eventBus.publish(CounterDown(it.value)) }
                    )

    fun stream(serverRequest: ServerRequest): Mono<ServerResponse> =
            ServerResponse
                    .ok()
                    .bodyToServerSentEvents(eventBus.subscribe())

}