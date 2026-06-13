package com.gamehub.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NoticiaWebController {

    @GetMapping("/gestion-noticias")
    public String noticias(){

        return "gestion-noticias";
    }
}