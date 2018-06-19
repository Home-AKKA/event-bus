package com.bus;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.bus.actor.BusActor;
import com.bus.actor.DriverActor;
import com.bus.dto.CommandDTO;
import com.bus.dto.EventDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Создание акторов: DriverActor — который будет обрабатывать команды, а DriverActor — будет публицировать события типа-EventDTO на Akka 'system-event-bus'.
 * Мы настраиваем BusActor, который будет прослушивать экземпляры EventDTO в потоке событий.
 */
public class AppBus {

    private static final Logger log = LoggerFactory.getLogger(AppBus.class);
    /**
     * Создание акторов: DriverActor — который будет обрабатывать команды, а DriverActor — будет публицировать события типа-EventDTO на Akka 'system-event-bus'.
     * Мы настраиваем BusActor, который будет прослушивать экземпляры EventDTO в потоке событий.
     *
     * Akka — это самостоятельный инструмент. Ему не нужен сервер приложений, достаточно JVM и Java SE.
     * С помощью Akka можно объединить несколько JVM в кластер.
     * Akka предлагает модель актеров вместо объектно-ориентированной, которая считается распараллеленной по умолчанию.
     *
     * Для быстрого старта нужно знать о нескольких классах из пакета akka.actor:
     * — ActorSystem — класс имплементирующий систему актеров;
     *    — вызовите ActorSystem.create() для создания системы актеров и получения объекта ActorSystem;
     *    — вызовите ActorSystem::actorOf() для создания экземпляра актера и получения его ActorRef;
     * — UntypedActor — класс, который нужно унаследовать для создания класса актера и переопределить метод onReceive для обработки входящих сообщений данным актером;
     * — ActorRef — класс, инкапсулирующий ссылку на актер. Он же используется для отправки сообщений актеру.
     *    — используйте ActorRef::tell() для отправки сообщений актеру.
     */
    // 1. запускаем актор-систему
    private final static ActorSystem actorSystem = ActorSystem.create("system-event-bus");

    public static void main(String... args) {
        // 2. сконфигурировали модель акторов для Akka в 'application.conf'

        // 3. создаем экземпляры своих акторов в актор-систему
        ActorRef busActor = actorSystem.actorOf(Props.create(BusActor.class)); // Handler
        actorSystem.eventStream().subscribe(busActor, EventDTO.class); // (обработчик подписывается на издателя-эммитер...)

        // 4. отправляем сообщения актеру
        ActorRef driverActor = actorSystem.actorOf(Props.create(DriverActor.class)); // Emitter(s)
        driverActor.tell(new CommandDTO("завести мотор"), null);
        driverActor.tell(new CommandDTO("ехать"), null);
        driverActor.tell(new CommandDTO("повернуть направо"), null);
        driverActor.tell(new CommandDTO("остановиться"), null);

//        Thread.sleep(2000);
        log.debug("Запуск Actor-системы...");

        actorSystem.shutdown(); // to exit
    }
}
