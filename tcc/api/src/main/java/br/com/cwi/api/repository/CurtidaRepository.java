package br.com.cwi.api.repository;

import br.com.cwi.api.domain.Curtida;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurtidaRepository extends JpaRepository<Curtida, Long> {
}
