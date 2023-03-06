package br.com.cwi.api.controller.request;

import br.com.cwi.api.domain.Privacidade;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class PostRequest {

    @NotBlank
    private String tituloFilme;

    @NotBlank
    private String notaFilme;

    @NotBlank
    private String texto;

    @NotNull
    private Privacidade privacidade;

}
