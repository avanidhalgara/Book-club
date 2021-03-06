package com.example.bookProjectTekSystem.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "book")
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    private Double price;

    private String description;

    private String imageName;


    public Book() {
    }

    public Book(String name, Category category, Double price, String description, String imageName) {

        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.imageName = imageName;
    }

}
