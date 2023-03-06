package br.com.cwi.api.controller.response;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioResponse {
    private Long id;
    private String nome;
    private String email;
    private String apelido;
    private String imagem_perfil;
    private LocalDate data_nascimento;
}
