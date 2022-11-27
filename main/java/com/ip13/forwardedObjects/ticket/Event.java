package com.ip13.forwardedObjects.ticket;

import java.io.Serial;
import java.io.Serializable;

public class Event implements Serializable {
    private Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Integer minAge; //Поле не может быть null
    private EventType eventType; //Поле может быть null

    @Serial
    private static final long serialVersionUID = 3404504;

    public Event(Integer id, String name, Integer minAge, EventType eventType) {
        this.id = id;
        this.name = name;
        this.minAge = minAge;
        this.eventType = eventType;
    }

    public Event(String name, Integer minAge, EventType eventType) {
        this.name = name;
        this.minAge = minAge;
        this.eventType = eventType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "EVENT_ID: " + id + " EVENT_NAME: " + name + " EVENT_MIN_AGE: " + minAge + " EVENT_TYPE: " + eventType;
    }
}