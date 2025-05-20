package com.fasteat.fasteat_api.controller;

import com.fasteat.fasteat_api.dto.CreateUsuarioRequest;
import com.fasteat.fasteat_api.dto.LoginRequest;
import com.fasteat.fasteat_api.model.Usuario;
import com.fasteat.fasteat_api.repositories.UsuarioRepository;
import com.fasteat.fasteat_api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*
 * Clase que define el controlador de usuarios
 * Define los metodos para obtener, crear, actualizar y eliminar usuarios  
 * Necesario añadir autenticacion.
 */

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable int id) {
        return usuarioRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public Usuario getUsuarioByEmail(@PathVariable String email) {
        return usuarioRepository.findByEmail(email);
    }

    @PostMapping
    public Usuario createUsuario(@RequestBody CreateUsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(usuarioService.encodePassword(request.getPassword()));
        usuario.setRol(Usuario.Rol.CLIENTE);
        
        return usuarioRepository.save(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable int id, @RequestBody Usuario usuarioDetails) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuario.setNombre(usuarioDetails.getNombre());
                usuario.setEmail(usuarioDetails.getEmail());
                
                // Si se proporciona una nueva contraseña, la hasheamos
                if (usuarioDetails.getPassword() != null && !usuarioDetails.getPassword().isEmpty()) {
                    String encodedPassword = usuarioService.encodePassword(usuarioDetails.getPassword());
                    usuario.setPassword(encodedPassword);
                }
                
                // Actualizar rol si se proporciona
                if (usuarioDetails.getRol() != null) {
                    usuario.setRol(usuarioDetails.getRol());
                }
                
                return ResponseEntity.ok(usuarioRepository.save(usuario));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable int id) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuarioRepository.delete(usuario);
                return ResponseEntity.ok().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail());
        
        if (usuario != null && usuarioService.verifyPassword(loginRequest.getPassword(), usuario.getPassword())) {
            return ResponseEntity.ok(usuario);
        }
        
        return ResponseEntity.badRequest().body("Credenciales inválidas");
    }
}