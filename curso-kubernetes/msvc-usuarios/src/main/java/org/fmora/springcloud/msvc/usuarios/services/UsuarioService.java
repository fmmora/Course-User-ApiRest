package org.fmora.springcloud.msvc.usuarios.services;

import org.fmora.springcloud.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {

    List<Usuario> findAll();
    Optional<Usuario> findById(Long id);
    Usuario save(Usuario usuario);
    void deleteById(Long id);
    List<Usuario> findAllById(Iterable<Long> ids);
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
