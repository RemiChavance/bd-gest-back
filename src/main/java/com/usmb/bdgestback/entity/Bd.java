package com.usmb.bdgestback.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "Bd")
public class Bd {

    @Column(name = "isbn")
    @Id
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @JdbcTypeCode(Types.BINARY)
    @Column(name = "image")
    private byte[] image;

    @Column(name = "numSerie")
    private int numSerie;

    @ManyToOne
    @JoinColumn(name = "serie_id", nullable = true)
    private Serie serie;

    @ManyToOne
    @JoinColumn(name = "authorScript_id", nullable = true)
    private Author authorScript;

    @ManyToOne
    @JoinColumn(name = "authorDraw_id", nullable = true)
    private Author authorDraw;


    // tests purpose
    @Override
    public String toString() {
        return this.getIsbn();
    }
}