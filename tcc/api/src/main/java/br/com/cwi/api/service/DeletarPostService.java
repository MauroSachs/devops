package br.com.cwi.api.service;

import br.com.cwi.api.domain.Post;
import br.com.cwi.api.repository.ComentarioRepository;
import br.com.cwi.api.repository.CurtidaRepository;
import br.com.cwi.api.repository.PostRepository;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeletarPostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BuscarPostService buscarPostService;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private ValidaEditarPostService validaEditarPostService;
    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private CurtidaRepository curtidaRepository;
    public void deletar(Long id) {
        Usuario logado = usuarioAutenticadoService.get();
        Post post = buscarPostService.porId(id);

        validaEditarPostService.validar(logado, post);
        comentarioRepository.deleteAll(post.getComentarios());
        curtidaRepository.deleteAll(post.getCurtidas());
        logado.removerPost(post);
        postRepository.delete(post);
    }
}
