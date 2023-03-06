package br.com.cwi.api.mapper;

import br.com.cwi.api.controller.response.CurtidaResponse;
import br.com.cwi.api.domain.Curtida;
import br.com.cwi.api.security.domain.Usuario;

public class CurtidaMapper {
    public static Curtida toEntity(Usuario logado) {
        return Curtida.builder()
                .usuario(logado)
                .build();
    }

    public static CurtidaResponse toResponse(Curtida curtida) {
        CurtidaResponse response = new CurtidaResponse();
        response.setId(curtida.getId());
        response.setAutorApelido(curtida.getUsuario().getApelido());
        response.setAutorId(curtida.getUsuario().getId());
        return response;
    }
}
