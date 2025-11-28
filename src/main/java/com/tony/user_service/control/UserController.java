package com.tony.user_service.control;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tony.user_service.dto.ErrorResponse;
import com.tony.user_service.dto.UserRequestDTO;
import com.tony.user_service.dto.UserResponseDTO;
import com.tony.user_service.model.User;
import com.tony.user_service.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid; 

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "API de gestión de usuarios")
public class UserController {

    @Autowired
    private UserService userService; 
    

    @Operation(
        summary = "Crear nuevo usuario",
        description = "Registra un nuevo usuario en el sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Usuario creado exitosamente",
            content = @Content(schema = @Schema(implementation = UserResponseDTO.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        ),
        @ApiResponse(
            responseCode = "409",
            description = "El email ya existe",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequest) {
        User newUser = userService.save(userRequest); 
        UserResponseDTO responseDTO = userService.mapToResponseDTO(newUser); 
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }


    @SecurityRequirement(name = "BearerAuth")
    @Operation(
        summary = "Obtener lista de todos los usuarios",
        description = "Recupera una lista paginada o completa de todos los usuarios registrados en el sistema.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Lista de usuarios recuperada exitosamente."
            ),
            @ApiResponse(
                responseCode = "401", 
                description = "No autorizado: Requiere token Bearer válido.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "403", 
                description = "Prohibido: El usuario no tiene permisos suficientes.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "500", 
                description = "Error interno del servidor.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @SecurityRequirement(name = "BearerAuth")
    @Operation(
        summary = "Obtener usuario por ID",
        description = "Recupera los detalles de un usuario específico usando su identificador único id.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Usuario encontrado y detalles retornados."
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Usuario no encontrado con el ID proporcionado.",
                 content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "401", 
                description = "No autorizado: Requiere token Bearer válido.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User user = userService.findById(id); 
        return ResponseEntity.ok(user);
    }


    @SecurityRequirement(name = "BearerAuth")
    @Operation(
        summary = "Actualizar usuario por ID (Sustitución completa)",
        description = "Reemplaza el recurso de usuario completo con la información proporcionada. Todos los campos deben ser enviados.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Usuario actualizado exitosamente y retornado."
            ),
            @ApiResponse(
                responseCode = "400", 
                description = "Datos inválidos en la solicitud.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Usuario no encontrado con el ID proporcionado.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "401", 
                description = "No autorizado: Requiere token Bearer válido.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User userDetails) {
        User updatedUser = userService.update(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @SecurityRequirement(name = "BearerAuth")
    @Operation(
        summary = "Modificar usuario por ID (Actualización parcial)",
        description = "Aplica cambios a los campos de usuario especificados en el cuerpo, dejando los demás sin modificar.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Usuario modificado exitosamente y retornado."
            ),
            @ApiResponse(
                responseCode = "400", 
                description = "Datos de entrada inválidos o formato de PATCH incorrecto.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Usuario no encontrado con el ID proporcionado.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "401", 
                description = "No autorizado: Requiere token Bearer válido.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                
            )
        }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable UUID id, @RequestBody User partialDetails) {
        User patchedUser = userService.patch(id, partialDetails);
        return ResponseEntity.ok(patchedUser);
    }


    @SecurityRequirement(name = "BearerAuth")
    @Operation(
        summary = "Eliminar usuario por ID",
        description = "Elimina permanentemente el usuario especificado por su ID.",
        responses = {
            @ApiResponse(
                responseCode = "204", 
                description = "Usuario eliminado exitosamente (No Content)."
            ),
            @ApiResponse(
                responseCode = "404", 
                description = "Usuario no encontrado con el ID proporcionado.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "401", 
                description = "No autorizado: Requiere token Bearer válido.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                responseCode = "403", 
                description = "Prohibido: El usuario no tiene permisos para eliminar.",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
        }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}