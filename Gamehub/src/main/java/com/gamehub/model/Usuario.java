package com.gamehub.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usuarios") 
public class Usuario {

    @Id
    private String id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "Formato de correo inválido")
    @NotBlank(message = "El correo es obligatorio")
    @Indexed(unique = true)
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

  
    private String rol;

    private boolean activo = true;

    private LocalDateTime fechaRegistro = LocalDateTime.now();

    private String fotoPerfil;

    private String descripcion;
    private Double saldo = 200000.0;
}

