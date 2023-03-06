package br.com.cwi.api.service;

import br.com.cwi.api.controller.response.PostResponse;
import br.com.cwi.api.domain.Post;
import br.com.cwi.api.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalharPostService {
    @Autowired
    private BuscarPostService buscarPostService;

    public PostResponse detalhar(Long id) {
        Post post = buscarPostService.porId(id);

        return PostMapper.toResponse(post);
    }
}
