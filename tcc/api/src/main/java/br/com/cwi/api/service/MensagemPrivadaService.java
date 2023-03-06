package br.com.cwi.api.service;

import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MensagemPrivadaService {

    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;

    public String gerarMensagem() {
        return "Rota privada: " + usuarioAutenticadoService.get().getNome();
    }
}
