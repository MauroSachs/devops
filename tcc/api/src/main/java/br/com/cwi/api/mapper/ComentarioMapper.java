package br.com.cwi.api.mapper;

import br.com.cwi.api.controller.request.ComentarioRequest;
import br.com.cwi.api.controller.response.ComentarioResponse;
import br.com.cwi.api.domain.Comentario;
import br.com.cwi.api.security.domain.Usuario;

public class ComentarioMapper {
    public static Comentario toEntity(ComentarioRequest request , Usuario logado) {
        return Comentario.builder()
                .texto(request.getTexto())
                .usuario(logado)
                .build();
    }
    public static ComentarioResponse toResponse(Comentario comentario) {
        ComentarioResponse response = new ComentarioResponse();
        response.setId(comentario.getId());
        response.setAutorApelido(comentario.getUsuario().getApelido());
        response.setAutorId(comentario.getUsuario().getId());
        response.setTexto(comentario.getTexto());
        return response;
    }
}
