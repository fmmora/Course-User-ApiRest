package org.fmora.springcloud.msvc.usuarios.services;

import org.fmora.springcloud.msvc.usuarios.clients.CursoClientRest;
import org.fmora.springcloud.msvc.usuarios.models.entity.Usuario;
import org.fmora.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private CursoClientRest clientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return (List<Usuario>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        repository.deleteById(id);
        clientRest.eliminarCursoUsuario(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAllById(Iterable<Long> ids) {
        return (List<Usuario>) repository.findAllById(ids);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return repository.buscarEmail(email);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
