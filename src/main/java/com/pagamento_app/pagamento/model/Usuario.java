package com.pagamento_app.pagamento.model;

import lombok.Data;

import java.util.List;

@Data
public class Usuario {

    private Integer id;
    private String nome;
    private String sobrenome;
    private String email;
    private String senha;
    private Enum tipoDeUsuario;
    private List<Transferencia> transferencia;
}
