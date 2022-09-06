package org.fmora.springcloud.msvc.cursos.models.entities;

import lombok.Data;
import org.fmora.springcloud.msvc.cursos.models.Usuario;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "curso")
@Data
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El campo nombre es requerido")
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "curso_id")
    private List<CursoUsuario> cursoUsuarios;

    @Transient
    private List<Usuario> usuarios;

    public Curso(){
        this.cursoUsuarios = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public void addCursoUsuarios(CursoUsuario cursoUsuario){
        this.cursoUsuarios.add(cursoUsuario);
    }

    public void removeCursoUsuarios(CursoUsuario cursoUsuario){
        this.cursoUsuarios.remove(cursoUsuario);
    }

}
