package org.fmora.springcloud.msvc.cursos.controllers;

import org.fmora.springcloud.msvc.cursos.models.Usuario;
import org.fmora.springcloud.msvc.cursos.models.entities.Curso;
import org.fmora.springcloud.msvc.cursos.models.entities.CursoUsuario;
import org.fmora.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class CursoController {

    @Autowired
    private CursoService service;

    @GetMapping
    public List<Curso> listar(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Curso> curso = service.findByIdConUsuarios(id);//service.findById(id);
        if(curso.isPresent()){
            return ResponseEntity.ok(curso.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody Curso curso, BindingResult result){

        if(result.hasErrors()){
            return validate(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @RequestBody Curso curso,BindingResult result ,@PathVariable Long id){

        if(result.hasErrors()){
            return validate(result);
        }

        Optional<Curso> cursoId = service.findById(id);
        if(cursoId.isPresent()){
            Curso cursoBD = cursoId.get();
            cursoBD.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoBD));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Curso> cursoId = service.findById(id);
        if(cursoId.isPresent()){
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> usuarioOptional;
        try {
            usuarioOptional = service.asignarUsuario(usuario, cursoId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje","No existe el usuario por el id " +
                            "o se produjo un error en la comunicacion " +e.getMessage()));
        }
        if(usuarioOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();

    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> usuarioOptional;
        try {
            usuarioOptional = service.crearUsuario(usuario, cursoId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje","No se pudo crear el usuario " +
                            "o error en la comunicacion " +e.getMessage()));
        }
        if(usuarioOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> usuarioOptional;
        try {
            usuarioOptional = service.eliminarUsuario(usuario, cursoId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje","No existe el usuario por el id " +
                            "o se produjo un error en la comunicacion " +e.getMessage()));
        }
        if(usuarioOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/eliminar-curso-usuario/{id}")
    public ResponseEntity<?> eliminarCursoUsuario(@PathVariable Long id){
        service.eliminarCursoUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }


    private ResponseEntity<?> validate(BindingResult result) {
        Map<String,String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> errores.put(error.getField(),error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }

}
