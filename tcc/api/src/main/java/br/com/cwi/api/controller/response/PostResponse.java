package br.com.cwi.api.controller.response;

import br.com.cwi.api.domain.Privacidade;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostResponse {
    private Long id;
    private String usuarioNome;
    private Long usuarioId;
    private String tituloFilme;
    private String notaFilme;
    private String texto;
    private LocalDateTime dataInclusao;
    private Privacidade privacidade;
    private List<ComentarioResponse> comentarios = new ArrayList<>();
    private List<CurtidaResponse> curtidas = new ArrayList<>();
}
