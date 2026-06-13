package com.gamehub.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BibliotecaWebController {

    @GetMapping("/biblioteca")
    public String biblioteca(){

        return "biblioteca";
    }
    @GetMapping("/historial-compras")
    public String historialCompras(){

        return "historial-compras";
    }
}