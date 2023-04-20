package com.usmb.bdgestback.controller;

import com.usmb.bdgestback.entity.Author;
import com.usmb.bdgestback.entity.Bd;
import com.usmb.bdgestback.entity.Serie;
import com.usmb.bdgestback.repository.AuthorRepository;
import com.usmb.bdgestback.repository.BdRepository;
import com.usmb.bdgestback.repository.SerieRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/api/init")
@RequiredArgsConstructor
public class InitDatabaseController {

    private final BdRepository bdRepository;
    private final SerieRepository serieRepository;
    private final AuthorRepository authorRepository;
    private final ResourceLoader resourceLoader;

    @PostMapping()
    public ResponseEntity<String> init() {
        // delete all data
        this.bdRepository.deleteAll();
        this.serieRepository.deleteAll();
        this.authorRepository.deleteAll();

        // load bd
        try {

            // load author
            InputStream in = this.resourceLoader.getResource("classpath:author.json").getInputStream();
            JSONArray dataAuthor = new JSONObject(new JSONTokener(in)).getJSONArray("data");



            for (int i=0; i<dataAuthor.length(); i++) {
                JSONObject a = dataAuthor.getJSONObject(i);
                Author author = Author.builder().name(a.getString("name")).build();
                authorRepository.save(author);
            }


            // load serie
            InputStream inS = this.resourceLoader.getResource("classpath:serie.json").getInputStream();
            JSONArray dataSerie = new JSONObject(new JSONTokener(inS)).getJSONArray("data");

            for (int i=0; i<dataSerie.length(); i++) {
                JSONObject s = dataSerie.getJSONObject(i);
                Serie serie = Serie.builder().title(s.getString("title")).build();
                serieRepository.save(serie);
            }


            // load bd
            InputStream inB = this.resourceLoader.getResource("classpath:bd.json").getInputStream();
            JSONArray dataBd = new JSONObject(new JSONTokener(inB)).getJSONArray("data");

            for (int i=0; i<dataBd.length(); i++) {
                JSONObject bdJson = dataBd.getJSONObject(i);
                byte[] byteArray = Base64.getDecoder().decode(bdJson.getString("image").getBytes());

                Bd bd = Bd.builder()
                        .isbn(bdJson.getString("isbn"))
                        .numSerie(bdJson.getInt("num_serie"))
                        .title(bdJson.getString("titre"))
                        .image(byteArray)
                        .build();

                // add serie
                Optional<Serie> serie = this.serieRepository.findByTitle(bdJson.getString("serie"));
                if (serie.isPresent()) {
                    bd.setSerie((serie.get()));
                }

                // add author
                Optional<Author> authorScenario = this.authorRepository.findByName(bdJson.getString("scenario"));
                if (authorScenario.isPresent()) {
                    bd.setAuthorScript((authorScenario.get()));
                }
                Optional<Author> authorDraw = this.authorRepository.findByName(bdJson.getString("dessin"));
                if (authorDraw.isPresent()) {
                    bd.setAuthorDraw((authorDraw.get()));
                }


                bdRepository.save(bd);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok("done");
    }
}