package com.ip13.forwardedObjects.ticket;

import java.io.Serial;
import java.io.Serializable;

public class Coordinates implements Serializable {
    private Integer x; //Максимальное значение поля: 490, Поле не может быть null
    private Double y; //Значение поля должно быть больше -835, Поле не может быть null

    @Serial
    private static final long serialVersionUID = 3404503;

    public Coordinates(Integer x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "X: " + x + ", Y: " + y;
    }
}
