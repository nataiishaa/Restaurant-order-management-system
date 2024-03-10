package com.developer.Restaurant_manager_appIDZ.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Setter
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @Column(unique = true)
    private  String name;
    @Column
    private  String description;
    @Column
    private double price;
    @Column
    private int quantity;
    @Column
    private int cookTime;



    @Override
    public String toString() {
        return  "Dish{" +
                "id=" + id + "name " + name + "description " + description +  "price " + price + "cooking time "+ cookTime;

    }
}