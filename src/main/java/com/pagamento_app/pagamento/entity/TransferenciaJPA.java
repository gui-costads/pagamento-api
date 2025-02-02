package com.pagamento_app.pagamento.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "transferencia")
public class TransferenciaJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotNull(message = "Valor é obrigatório")
    @Column(nullable = false, name = "valor")
    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "pagador_id", nullable = false)
    private UsuarioJPA pagador;

    @ManyToOne
    @JoinColumn(name = "recebedor_id", nullable = false)
    private UsuarioJPA recebedor;

}
