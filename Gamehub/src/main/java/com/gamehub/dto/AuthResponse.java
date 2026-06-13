package com.gamehub.dto;

/**
 * DTO de respuesta tras un login exitoso.
 * Devuelve el token JWT y los datos básicos del usuario.
 */
public class AuthResponse {

    private String token;
    private String correo;
    private String rol;
    private String nombre;

    public AuthResponse(String token, String correo, String rol, String nombre) {
        this.token  = token;
        this.correo = correo;
        this.rol    = rol;
        this.nombre = nombre;
    }

    // ── Getters ───────────────────────────────────────
    public String getToken()  { return token; }
    public String getCorreo() { return correo; }
    public String getRol()    { return rol; }
    public String getNombre() { return nombre; }
}