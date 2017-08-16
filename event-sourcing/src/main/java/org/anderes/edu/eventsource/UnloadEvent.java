package org.anderes.edu.eventsource;

import java.time.LocalDateTime;

public class UnloadEvent extends ShippingEvent {

    private Cargo cargo;
    private Port port;

    public UnloadEvent(LocalDateTime date, Cargo cargo, Port port) {
        super(date);
        this.port = port;
        this.cargo = cargo;
    }

    @Override
    public void process() {
        cargo.handleUnloadEvent(this);
    }

    public Port getPort() {
        return port;
    }

    public Cargo getCargo() {
        return cargo;
    }

    @Override
    public String toString() {
        return String.format("UnloadEvent (%s) [cargo=%s, port=%s]", super.toString(), cargo, port);
    }

}
