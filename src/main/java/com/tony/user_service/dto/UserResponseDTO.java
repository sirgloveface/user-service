package com.tony.user_service.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.NoArgsConstructor; 
import lombok.AllArgsConstructor; 



@Data
@Builder
@NoArgsConstructor 
@AllArgsConstructor
public class UserResponseDTO {
    private UUID id;
    private LocalDateTime creado;
    private LocalDateTime modificado;
    private LocalDateTime ultimoLogin;
    private String token;
    private Boolean activo;
    private String name;
    private String email;
}