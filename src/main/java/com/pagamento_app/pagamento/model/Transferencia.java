package com.pagamento_app.pagamento.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transferencia {

    private Integer id;
    private BigDecimal valor;
    private Integer pagador;
    private Integer recebedor;

}
