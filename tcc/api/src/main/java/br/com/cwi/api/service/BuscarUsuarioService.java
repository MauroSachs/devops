package br.com.cwi.api.service;

import br.com.cwi.api.controller.response.UsuarioResponse;
import br.com.cwi.api.mapper.UsuarioMapper;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.repository.UsuarioRepository;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BuscarUsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public Usuario porId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,"Usuário não encontrado"));
    }

    public UsuarioResponse logado() {
        return UsuarioMapper.toResponse(usuarioAutenticadoService.get());
    }
}
