package com.batch.demo.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name="DUMMY_CAR")
public class DummyCar implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "year")
    private String year;

    @Column(name = "color")
    private String color;

    @Column(name = "make")
    private String make;

    @Column(name = "auto_enabled")
    private boolean autoEnabled;
}
