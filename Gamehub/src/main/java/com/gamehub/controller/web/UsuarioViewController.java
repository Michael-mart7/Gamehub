package com.gamehub.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioViewController {

    @GetMapping("/usuarios")
    public String usuarios() {

        return "usuarios";
    }
}