package br.com.cwi.api.service;

import br.com.cwi.api.repository.AmizadeRepository;
import br.com.cwi.api.security.domain.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CheckAmizadeService {
    @Autowired
    private AmizadeRepository amizadeRepository;


    public boolean checkAmizade(Long user1, Long user2) {
        return amizadeRepository.findAllFriendsPageable(user1, "", null).map(Usuario::getId).toList().contains(user2);
    }
    public boolean checkSolicitacao(Long solicitante, Long solicitado) {
        return amizadeRepository.findAllRequests(solicitado).stream().map(Usuario::getId).collect(Collectors.toList()).contains(solicitante);
    }
}
