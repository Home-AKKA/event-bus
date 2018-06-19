package com.bus.dto;

import java.io.Serializable;

public class CommandDTO implements Serializable {

    private final String command;

    public CommandDTO(String command) {
        this.command = command;
    }

    public String getCommand() {
        return this.command;
    }

    @Override
    public String toString() {
        return "Command{" +
                "'" + command + '\'' +
                '}';
    }
}
