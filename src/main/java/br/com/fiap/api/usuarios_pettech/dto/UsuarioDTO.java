package br.com.fiap.api.usuarios_pettech.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UsuarioDTO(
        Long id,
        @NotBlank(message = "O nome de usuário não pode estar em branco")
        String nome,
        @Email(message = "E-mail inválido.")
        String email,
        @CPF(message = "CPF inválido")
        String CPF,
        LocalDate dataDeNascimento) {
}
