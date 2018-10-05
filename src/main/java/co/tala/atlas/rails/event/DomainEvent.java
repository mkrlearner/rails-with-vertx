package co.tala.atlas.rails.event;

import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class DomainEvent extends ApplicationEvent {
    private String uuid;
    public LocalDateTime occurredOn;

    public DomainEvent(Object source) {
        super(source);
        uuid = UUID.randomUUID().toString();
        occurredOn = LocalDateTime.now();
    }
}
