package com.tony.user_service.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
@Getter
@Setter
public class UserRequestDTO {

    @Value("${app.validation.email-regex}")
    private String emailRegex;

    @Value("${app.validation.password-regex}")
    private String passwordRegex;

    @NotBlank(message = "El nombre es obligatorio.")
    @JsonProperty("nombre")
    private String name;
    
    @NotBlank(message = "El correo es obligatorio.")
   // @Pattern(regexp = "${app.validation.email-regex}", message = "El correo no sigue el formato aaaaaaa@dominio.cl.")
    @JsonProperty("correo")
    private String email;
    
    @NotBlank(message = "La contraseña es obligatoria.")
   // @Pattern(regexp = "${app.validation.password-regex}", 
  //           message = "La contraseña debe tener entre 8-20 chars, mayúscula, minúscula, número y símbolo.")
    @JsonProperty("contrasena")
    private String password;

    @Valid 
    @NotEmpty(message = "Debe proporcionar al menos un teléfono valido.")
    @JsonProperty("telefonos")
    private List<PhoneDTO> phones;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

}
