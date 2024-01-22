package br.com.fiap.api.usuarios_pettech.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record UsuarioDTO(
        Long id,
        @NotBlank
        String nome,
        String email,
        String CPF,
        LocalDate dataDeNascimento) {
}
