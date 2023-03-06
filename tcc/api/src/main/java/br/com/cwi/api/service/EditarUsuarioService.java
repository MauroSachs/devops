package br.com.cwi.api.service;

import br.com.cwi.api.controller.request.EditarUsuarioRequest;
import br.com.cwi.api.repository.UsuarioRepository;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditarUsuarioService {
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    public void editar(EditarUsuarioRequest request) {
        Usuario logado = usuarioAutenticadoService.get();

        logado.setImagemPerfil(request.getImagem_perfil());
        logado.setApelido(request.getApelido());
        logado.setNome(request.getNome());
        usuarioRepository.save(logado);
    }
}
