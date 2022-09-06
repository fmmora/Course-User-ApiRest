package org.fmora.springcloud.msvc.cursos.clients;

import org.fmora.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios",url = "msvc-usuarios:8001")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);

    @PostMapping
    Usuario crear(@RequestBody Usuario usuario);

    @GetMapping("/usuarios-curso")
    List<Usuario> listarPorId(@RequestParam Iterable<Long> ids);

}
