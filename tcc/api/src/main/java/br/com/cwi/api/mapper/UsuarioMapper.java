package br.com.cwi.api.mapper;

import br.com.cwi.api.controller.request.UsuarioRequest;
import br.com.cwi.api.controller.response.UsuarioResponse;
import br.com.cwi.api.security.domain.Usuario;

import java.util.Objects;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioRequest request) {
        Usuario entity = new Usuario();
        entity.setNome(request.getNome());
        entity.setEmail(request.getEmail());
        entity.setApelido(getUsuarioApelido(request));
        entity.setDataNascimento(request.getData_nascimento());
        entity.setImagemPerfil(request.getImagem_perfil());
        return entity;
    }

    public static UsuarioResponse toResponse(Usuario entity) {
        UsuarioResponse response = new UsuarioResponse();
        response.setId(entity.getId());
        response.setNome(entity.getNome());
        response.setEmail(entity.getEmail());
        response.setApelido(entity.getApelido());
        response.setData_nascimento(entity.getDataNascimento());
        response.setImagem_perfil(entity.getImagemPerfil());
        return response;
    }

    private static String getUsuarioApelido(UsuarioRequest request) {
        if(Objects.isNull(request.getApelido())) {
            return request.getNome().trim().split(" ")[0];
        }
        return request.getApelido();
    }
}
