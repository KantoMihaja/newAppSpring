package com.example.demo.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SupplierDto {
    @JsonProperty("name")
    private String name;
    
    public SupplierDto(String name) {
        this.name = name;
    }
}