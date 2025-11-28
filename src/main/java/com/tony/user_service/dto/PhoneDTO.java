package com.tony.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;




@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO {

    @NotBlank(message = "El número de teléfono es obligatorio.") 
    @JsonProperty("numero")
    private String number;
    
    @NotBlank(message = "El código de ciudad es obligatorio.")
    @JsonProperty("codigoCiudad")
    private String cityCode;
    
    @NotBlank(message = "El código de país es obligatorio.")
    @JsonProperty("codigoPais")
    private String countryCode;

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

   
    public String getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
