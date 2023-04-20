package com.usmb.bdgestback.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "sharedBd")
public class SharedBd {

    @Column(name = "id")
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "isbn")
    private String isbn;
    @Column(name = "userId")
    private Integer userId;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
}
