package com.tony.user_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ErrorResponse {
    private String mensaje;

    public ErrorResponse(String mensaje) {
        this.mensaje = mensaje;
    }
}

