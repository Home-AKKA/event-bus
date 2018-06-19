package com.bus.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Обработчик процессов, которые были выброшены на eventstream.
 */
// Handler
public class BusActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object msg) {
        log.info("<<< " + msg); // Обработывает событие (выполняет команду:)
    }
}
