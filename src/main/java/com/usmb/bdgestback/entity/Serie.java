package com.usmb.bdgestback.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "serie")
public class Serie {

    @Column(name = "id")
    @Id @GeneratedValue
    private Integer id;

    @Column(name = "title")
    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "serie")
    private List<Bd> bds;

    // tests purpose
    public Serie(int id) {
        this(id, null, null);
    }
}
