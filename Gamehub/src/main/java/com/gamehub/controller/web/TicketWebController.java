package com.gamehub.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TicketWebController {

    // =====================
    // MIS TICKETS (USUARIO)
    // =====================

    @GetMapping("/tickets")
    public String tickets(){

        return "tickets";
    }

    // =====================
    // PANEL SOPORTE
    // =====================

    @GetMapping("/soporte")
    public String soporte(){

        return "soporte";
    }
}