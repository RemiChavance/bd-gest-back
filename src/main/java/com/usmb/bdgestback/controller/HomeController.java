package com.usmb.bdgestback.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    @GetMapping()
    public String helloApi()
    {
        return "Bienvenue sur l'API BD Gest";
    }
}
