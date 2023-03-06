package br.com.cwi.api.factories;

import br.com.cwi.api.domain.Post;

import java.util.ArrayList;

public class PostFactory {

    public static Post get() {
        return Post.builder()
                .id(SimpleFactory.getRandomLong())
                .texto("teste texto")
                .usuario(UsuarioFactory.get())
                .comentarios(new ArrayList<>())
                .curtidas(new ArrayList<>())
                .build();
    }



}
