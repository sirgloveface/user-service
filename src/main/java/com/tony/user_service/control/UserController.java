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

import com.tony.user_service.dto.UserRequestDTO;
import com.tony.user_service.dto.UserResponseDTO;
import com.tony.user_service.model.User;
import com.tony.user_service.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid; 

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService; 
    
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequest) {
        User newUser = userService.save(userRequest); 
        UserResponseDTO responseDTO = userService.mapToResponseDTO(newUser); 
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @SecurityRequirement(name = "BearerAuth")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @SecurityRequirement(name = "BearerAuth")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User user = userService.findById(id); 
        return ResponseEntity.ok(user);
    }

    @SecurityRequirement(name = "BearerAuth")
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User userDetails) {
        User updatedUser = userService.update(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @SecurityRequirement(name = "BearerAuth")
    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable UUID id, @RequestBody User partialDetails) {
        User patchedUser = userService.patch(id, partialDetails);
        return ResponseEntity.ok(patchedUser);
    }

    @SecurityRequirement(name = "BearerAuth")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}