package com.usmb.bdgestback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "Author")
public class Author {

    @Column(name = "id")
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name")
    private String name;

    // tests purpose
    public Author(int id) {
        this(id, null);
    }
}
