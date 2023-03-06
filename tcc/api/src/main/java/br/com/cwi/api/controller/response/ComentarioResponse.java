package br.com.cwi.api.controller.response;

import lombok.Data;

@Data
public class ComentarioResponse {
    private Long id;
    private String autorApelido;
    private Long autorId;
    private String texto;

}
