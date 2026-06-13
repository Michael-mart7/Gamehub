package com.gamehub.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReporteWebController {

    @GetMapping("/reportes")
    public String reportes(){

        return "reportes";
    }
}