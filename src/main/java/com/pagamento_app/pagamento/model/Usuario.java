package com.pagamento_app.pagamento.model;

import lombok.Data;

import java.util.List;
import java.math.BigDecimal;

import com.pagamento_app.pagamento.types.enums.TipoDeUsuario;

@Data
public class Usuario {

    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private BigDecimal saldo;
    private TipoDeUsuario tipoDeUsuario;
    private List<Transferencia> transferencia;
}
