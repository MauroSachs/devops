package br.com.cwi.api.service;

import br.com.cwi.api.controller.request.ComentarioRequest;
import br.com.cwi.api.domain.Comentario;
import br.com.cwi.api.domain.Post;
import br.com.cwi.api.mapper.ComentarioMapper;
import br.com.cwi.api.repository.ComentarioRepository;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CriarComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private BuscarPostService buscarPostService;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public void comentar(ComentarioRequest request, Long id) {
       Usuario logado = usuarioAutenticadoService.get();

        Post post = buscarPostService.porId(id);

        Comentario comentario = ComentarioMapper.toEntity(request, logado);
        post.adicionarComentario(comentario);

        comentarioRepository.save(comentario);
    }
}
