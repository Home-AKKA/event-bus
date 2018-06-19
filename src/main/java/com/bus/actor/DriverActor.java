package com.bus.actor;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.bus.dto.CommandDTO;
import com.bus.dto.EventDTO;

/**
 * DriverActor получает команду и эммитирует события, которые подхватывает подписанный обработчик.
 * Обработчик прослушивает выпущенный EventDTO.class
 */
// Emitter
public class DriverActor extends UntypedActor {

    LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    @Override
    public void onReceive(Object msg) {
        if (msg instanceof CommandDTO) {
            log.info(">>> " + msg); // Эмитируюет событие (отдает команду:)

            String command = ((CommandDTO) msg).getCommand();

            getContext().system().eventStream().publish(new EventDTO(command));
        }
    }


}
