package br.com.cwi.api.controller.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class ComentarioRequest {
    @NotBlank
    private String texto;
}
