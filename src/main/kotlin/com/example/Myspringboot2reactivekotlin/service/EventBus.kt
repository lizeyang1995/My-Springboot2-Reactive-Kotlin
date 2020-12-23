package com.example.Myspringboot2reactivekotlin.service

import com.example.Myspringboot2reactivekotlin.model.CounterEvent
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.ReplayProcessor
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Service
class EventBus(@Value("\${events.replay.size:16}") replaySize: Int = 16) {
    private val eventProcessor: ReplayProcessor<CounterEvent> = ReplayProcessor.create(replaySize)

    init {
        log.info("Replay size is $replaySize")
    }

    fun publish(event: CounterEvent) {
        log.info("Publishing event: {}", event)
        eventProcessor.onNext(event)
        log.info("Published to eventProcessor: {}", event)
    }

    fun subscribe(): Flux<CounterEvent> =
            Flux.merge(eventProcessor)
                    .onBackpressureDrop()
                    .doOnCancel { log.debug("Subscription cancelled") }
                    .doOnError { log.warn("Error in EventBus", it) }
                    .doOnSubscribe { log.debug("Subscription created") }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(EventBus::class.java)
    }
}