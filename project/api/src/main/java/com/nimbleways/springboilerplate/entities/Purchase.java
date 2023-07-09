package com.nimbleways.springboilerplate.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "purchases")
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Column(name="brand",nullable = false)
    private String brand;

    @Column(name="model",nullable = false)
    private String model;

    @Column(name="price",nullable = false)
    private Double price;

    @Column(name="store",nullable = false)
    private String store;

    @Column(name="rating", nullable = false)
    private int rating;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ElementCollection
    @JoinTable(name = "medias",
            joinColumns = @JoinColumn(name = "purchase_id"))
    @Column(name = "url")
    private List<String> media;
}
