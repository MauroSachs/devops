package br.com.cwi.api.service;

import br.com.cwi.api.domain.Amizade;
import br.com.cwi.api.repository.AmizadeRepository;
import br.com.cwi.api.security.domain.Usuario;
import br.com.cwi.api.security.service.UsuarioAutenticadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AdministrarAmigosService {
    @Autowired
    private CheckAmizadeService buscarAmigosService;
    @Autowired
    private UsuarioAutenticadoService usuarioAutenticadoService;
    @Autowired
    private BuscarUsuarioService buscarUsuarioService;
    @Autowired
    private AmizadeRepository amizadeRepository;


    public void solicitar(Long userId) {
        Usuario solicitado = buscarUsuarioService.porId(userId);
        Usuario logado = usuarioAutenticadoService.get();

        if (userId.equals(logado.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível adicionar a sí mesmo");
        }

        if (buscarAmigosService.checkSolicitacao(logado.getId(),userId) || buscarAmigosService.checkSolicitacao(userId,logado.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Já existe uma solicitação entre os dois usuarios");
        }

        if (buscarAmigosService.checkAmizade(logado.getId(), userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuários já são amigos");
        }

        Amizade amizade = new Amizade();
        amizade.setUsuario1(logado);
        amizade.setUsuario2(solicitado);
        amizadeRepository.save(amizade);
    }
    public void aceitar(Long userId) {
        Usuario logado = usuarioAutenticadoService.get();

        Amizade solicitacao = amizadeRepository.findByUsuario1IdAndUsuario2IdAndAceitoFalse(userId, logado.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe uma solicitação deste usuário"));

        solicitacao.setAceito(true);
        amizadeRepository.save(solicitacao);


    }
    public void recusar(Long userId) {
        Usuario logado = usuarioAutenticadoService.get();

        Amizade solicitacao = amizadeRepository.findByUsuario1IdAndUsuario2IdAndAceitoFalse(userId, logado.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não existe uma solicitação deste usuário"));


        amizadeRepository.delete(solicitacao);
    }
    public void deletar(Long userId) {
        Usuario logado = usuarioAutenticadoService.get();

        Amizade amizade = amizadeRepository.findAllByAceitoTrueAndUsuario1IdOrAceitoTrueAndUsuario2Id(logado.getId(), logado.getId())
                .stream().filter(amizadeDoUser -> amizadeDoUser.getUsuario1().getId().equals(userId) || amizadeDoUser.getUsuario2().getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuários não são amigos"));


        amizadeRepository.delete(amizade);
    }


}
