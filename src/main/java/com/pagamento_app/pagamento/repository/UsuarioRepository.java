package com.pagamento_app.pagamento.repository;

import com.pagamento_app.pagamento.entity.UsuarioJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioJPA, Integer> {
}
