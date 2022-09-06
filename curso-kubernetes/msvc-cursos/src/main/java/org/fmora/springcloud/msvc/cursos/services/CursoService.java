package org.fmora.springcloud.msvc.cursos.services;

import org.fmora.springcloud.msvc.cursos.models.Usuario;
import org.fmora.springcloud.msvc.cursos.models.entities.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {

    List<Curso> findAll();
    Optional<Curso> findById(Long id);
    Optional<Curso> findByIdConUsuarios(Long id);
    Curso save(Curso curso);
    void deleteById(Long id);
    void eliminarCursoUsuarioPorId(Long id);

    Optional<Usuario> asignarUsuario(Usuario usuario,Long cursoId);//el usuario ya existe y lo asigna a un curso
    Optional<Usuario> crearUsuario(Usuario usario,Long cursoId);//el usuario no existe, lo crea y lo asigna a un curso
    Optional<Usuario> eliminarUsuario(Usuario usuario,Long cursoId);//elimina el usuario del curso

}
