package org.fmora.springcloud.msvc.usuarios.models.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El campo nombre es requerido")
    private String nombre;

    @Email(message = "El campo email debe tener un formato correcto")
    @NotBlank(message = "El campo email es requerido")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "El campo password es requerido")
    private String password;
}
