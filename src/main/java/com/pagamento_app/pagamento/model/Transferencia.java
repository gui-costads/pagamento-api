package com.pagamento_app.pagamento.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Transferencia {

    private BigDecimal valor;
    private Integer pagadorId;
    private Integer recebedorId;

}
