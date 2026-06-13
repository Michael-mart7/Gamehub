package com.gamehub.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EstadisticasWebController {

    @GetMapping("/estadisticas")
    public String estadisticas(){

        return "estadisticas";
    }
}