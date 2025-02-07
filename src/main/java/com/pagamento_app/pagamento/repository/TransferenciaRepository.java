package com.pagamento_app.pagamento.repository;

import java.util.List;

import com.pagamento_app.pagamento.entity.TransferenciaJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferenciaRepository extends JpaRepository<TransferenciaJPA, Integer> {
    List<TransferenciaJPA> findByPagadorID(Integer id);
    List<TransferenciaJPA> findByRecebedorID(Integer id);
}
