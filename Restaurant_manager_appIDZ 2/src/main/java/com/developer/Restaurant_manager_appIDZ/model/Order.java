package com.developer.Restaurant_manager_appIDZ.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private Integer userId;
    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private  List<Dish> dishes;
    @Column
    private int timeToReady;
    @Column
    private Status status;

    @Column
    private double totalPrice;
    @Column
    private Double paymentAmount;



    public synchronized void increaseTimeToReady(int min) {
        timeToReady += min;
    }

    @JsonCreator
    public Order(Dish[] dishes) {
        this.dishes = Arrays.stream(dishes).toList();
        double paymentAmount = 0;
        for (Dish dish : dishes) {
            paymentAmount += dish.getPrice();
            increaseTimeToReady(dish.getCookTime());
        }
        this.paymentAmount = paymentAmount;
        status = Status.CREATED;
    }
    public boolean addDish(Dish dish) {
        increaseTimeToReady(dish.getCookTime());
        timeToReady += dish.getCookTime();
        status = Status.PREPARING;
        return dishes.add(dish);
    }

    public boolean isActive() {
        return status != Status.PREPARING;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", dishes=" + dishes +
                ", timeToReady=" + timeToReady +
                ", status=" + status +
                '}';
    }
}