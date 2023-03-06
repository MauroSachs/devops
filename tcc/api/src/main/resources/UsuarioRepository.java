package br.com.cwi.api.security.repository;

import br.com.cwi.api.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Page<Usuario> findByNomeContainsIgnoreCaseOrEmailContainsIgnoreCase(String titulo, String descricao, Pageable pageable);
    Usuario findByEmail(String email);
}
