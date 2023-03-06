package br.com.cwi.api.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ValidaNotaService {
    public void validar(String nota) {
        try {
            int notaInt = Integer.parseInt(nota);
            if(notaInt>10 || notaInt<0) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nota deve ser um número de 1-10");
        }
        catch (NumberFormatException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nota deve ser um número de 1-10");
        }
    }
}