package br.com.cwi.api.service;

import br.com.cwi.api.controller.request.AlterarPostRequest;
import br.com.cwi.api.domain.Post;
import br.com.cwi.api.repository.PostRepository;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlterarPostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BuscarPostService buscarPostService;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private ValidaEditarPostService validaEditarPostService;

    public void alterar(AlterarPostRequest request, Long id) {
        Usuario logado = usuarioAutenticadoService.get();
        Post post = buscarPostService.porId(id);

        validaEditarPostService.validar(logado, post);

        post.setPrivacidade(request.getPrivacidade());

        postRepository.save(post);
    }
}
