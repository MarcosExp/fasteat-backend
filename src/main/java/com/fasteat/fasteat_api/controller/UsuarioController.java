package com.fasteat.fasteat_api.controller;

import com.fasteat.fasteat_api.dto.CreateUsuarioRequest;
import com.fasteat.fasteat_api.dto.LoginRequest;
import com.fasteat.fasteat_api.model.Usuario;
import com.fasteat.fasteat_api.repositories.UsuarioRepository;
import com.fasteat.fasteat_api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/*
 * Clase que define el controlador de usuarios
 * Define los metodos para obtener, crear, actualizar y eliminar usuarios  
 * Necesario añadir autenticacion.
 */

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuario", description = "API para la gestión de usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Obtener todos los usuarios", description = "Retorna una lista de todos los usuarios registrados en el sistema")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios encontrada exitosamente")
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Operation(summary = "Obtener usuario por ID", description = "Retorna un usuario específico basado en su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(
            @Parameter(description = "ID del usuario a buscar") @PathVariable int id) {
        return usuarioRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener usuario por email", description = "Retorna un usuario específico basado en su email")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente")
    @GetMapping("/email/{email}")
    public Usuario getUsuarioByEmail(
            @Parameter(description = "Email del usuario a buscar") @PathVariable String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Operation(summary = "Crear nuevo usuario", description = "Crea un nuevo usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente",
            content = @Content(schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "400", description = "Datos de usuario inválidos")
    })
    @PostMapping
    public Usuario createUsuario(
            @Parameter(description = "Datos del usuario a crear") @RequestBody CreateUsuarioRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(usuarioService.encodePassword(request.getPassword()));
        usuario.setRol(Usuario.Rol.CLIENTE);
        
        return usuarioRepository.save(usuario);
    }

    @Operation(summary = "Actualizar usuario", description = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(
            @Parameter(description = "ID del usuario a actualizar") @PathVariable int id,
            @Parameter(description = "Nuevos datos del usuario") @RequestBody Usuario usuarioDetails) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuario.setNombre(usuarioDetails.getNombre());
                usuario.setEmail(usuarioDetails.getEmail());
                
                if (usuarioDetails.getPassword() != null && !usuarioDetails.getPassword().isEmpty()) {
                    String encodedPassword = usuarioService.encodePassword(usuarioDetails.getPassword());
                    usuario.setPassword(encodedPassword);
                }
                
                if (usuarioDetails.getRol() != null) {
                    usuario.setRol(usuarioDetails.getRol());
                }
                
                return ResponseEntity.ok(usuarioRepository.save(usuario));
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario del sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(
            @Parameter(description = "ID del usuario a eliminar") @PathVariable int id) {
        return usuarioRepository.findById(id)
            .map(usuario -> {
                usuarioRepository.delete(usuario);
                return ResponseEntity.ok().<Void>build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Iniciar sesión", description = "Autentica a un usuario en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso"),
        @ApiResponse(responseCode = "400", description = "Credenciales inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Parameter(description = "Credenciales de acceso") @RequestBody LoginRequest loginRequest) {
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail());
        
        if (usuario != null && usuarioService.verifyPassword(loginRequest.getPassword(), usuario.getPassword())) {
            return ResponseEntity.ok(usuario);
        }
        
        return ResponseEntity.badRequest().body("Credenciales inválidas");
    }
}