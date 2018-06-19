package com.bus.dto;

import java.io.Serializable;

public class EventDTO implements Serializable {

    private final String action;

    public EventDTO(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "Event{" +
                "'" + action + '\'' +
                '}';
    }
}
