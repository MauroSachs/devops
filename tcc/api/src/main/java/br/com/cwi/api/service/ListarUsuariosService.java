package br.com.cwi.api.service;

import br.com.cwi.api.controller.response.UsuarioResponse;
import br.com.cwi.api.mapper.UsuarioMapper;
import br.com.cwi.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListarUsuariosService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Page<UsuarioResponse> listarPaginado(String text, Pageable pageable) {
        return usuarioRepository.findByNomeContainsIgnoreCaseOrEmailContainsIgnoreCase(text,text, pageable)
                .map(UsuarioMapper::toResponse);
    }
}
