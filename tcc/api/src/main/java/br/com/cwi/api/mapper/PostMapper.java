package br.com.cwi.api.mapper;

import br.com.cwi.api.controller.request.PostRequest;
import br.com.cwi.api.controller.response.PostResponse;
import br.com.cwi.api.domain.Post;
import br.com.cwi.api.security.domain.Usuario;

import java.time.LocalDateTime;

public class PostMapper {



    public static PostResponse toResponse(Post entity) {
        PostResponse response = new PostResponse();
        response.setId(entity.getId());
        response.setUsuarioNome(entity.getUsuario().getApelido());
        response.setUsuarioId(entity.getUsuario().getId());
        response.setTituloFilme(entity.getTituloFilme());
        response.setId(entity.getId());
        response.setNotaFilme(entity.getNotaFilme());
        response.setTexto(entity.getTexto());
        response.setPrivacidade(entity.getPrivacidade());
        response.setDataInclusao(entity.getDataInclusao());
        entity.getComentarios().forEach(comentario -> response.getComentarios()
                .add(ComentarioMapper.toResponse(comentario)));
        entity.getCurtidas().forEach(curtida -> response.getCurtidas()
                .add(CurtidaMapper.toResponse(curtida)));
        return response;
    }


    public static Post toEntity(PostRequest request, Usuario user) {
        return Post.builder()
                .usuario(user)
                .privacidade(request.getPrivacidade())
                .texto(request.getTexto())
                .tituloFilme(request.getTituloFilme())
                .notaFilme(request.getNotaFilme())
                .dataInclusao(LocalDateTime.now())
                .build();


    }
}
