package org.fmora.springcloud.msvc.cursos.services;

import org.fmora.springcloud.msvc.cursos.clients.UsuarioClientRest;
import org.fmora.springcloud.msvc.cursos.models.Usuario;
import org.fmora.springcloud.msvc.cursos.models.entities.Curso;
import org.fmora.springcloud.msvc.cursos.models.entities.CursoUsuario;
import org.fmora.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CursoServiceImpl implements CursoService{

    @Autowired
    private CursoRepository repository;

    @Autowired
    private UsuarioClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findAll() {
        return (List<Curso> )repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> findByIdConUsuarios(Long id) {
        Optional<Curso> o = repository.findById(id);
        if(o.isPresent()){
            Curso curso = o.get();
            if(!curso.getCursoUsuarios().isEmpty()){
                List<Long> ids = curso.getCursoUsuarios().stream()
                        .map(CursoUsuario::getUsuarioId)
                        .collect(Collectors.toList());
                List<Usuario> usuarios = client.listarPorId(ids);
                curso.setUsuarios(usuarios);
            }
            return Optional.of(curso);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Curso save(Curso curso) {
        return repository.save(curso);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
        repository.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {

        Optional<Curso> cursoBd = repository.findById(cursoId);
        if(cursoBd.isPresent()){
            Usuario usuarioMsvc = client.detalle(usuario.getId());

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            Curso curso = cursoBd.get();
            curso.addCursoUsuarios(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {

        Optional<Curso> cursoBd = repository.findById(cursoId);
        if(cursoBd.isPresent()){
            Usuario usuarioNuevoMsvc = client.crear(usuario);

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());

            Curso curso = cursoBd.get();
            curso.addCursoUsuarios(cursoUsuario);
            repository.save(curso);

            return Optional.of(usuarioNuevoMsvc);

        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> cursoBd = repository.findById(cursoId);
        if(cursoBd.isPresent()){
            Usuario usuarioMsvc = client.detalle(usuario.getId());

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            Curso curso = cursoBd.get();
            curso.removeCursoUsuarios(cursoUsuario);
            repository.save(curso);
            return Optional.of(usuarioMsvc);

        }
        return Optional.empty();
    }

}
