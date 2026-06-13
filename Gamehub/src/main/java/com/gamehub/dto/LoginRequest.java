package com.gamehub.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO para la petición de login.
 * Solo se necesita correo y contraseña.
 */
public class LoginRequest {

    @Email(message = "Formato de correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    // ── Getters y Setters ─────────────────────────
    public String getCorreo()               { return correo; }
    public void setCorreo(String correo)    { this.correo = correo; }
    public String getPassword()             { return password; }
    public void setPassword(String password){ this.password = password; }
}