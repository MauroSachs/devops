package br.com.cwi.api.controller.request;

import br.com.cwi.api.domain.Privacidade;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@Data
public class AlterarPostRequest {
    @NotNull @Enumerated(EnumType.STRING)
    private Privacidade privacidade;
}
