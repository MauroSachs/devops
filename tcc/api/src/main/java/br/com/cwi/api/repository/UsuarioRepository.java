package br.com.cwi.api.repository;

import br.com.cwi.api.security.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<Usuario> findByNomeContainsIgnoreCaseOrEmailContainsIgnoreCase(String nome, String email, Pageable pageable);
    Optional<Usuario> findByEmail(String email);
}
