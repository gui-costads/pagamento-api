package com.pagamento_app.pagamento.types.enums;

import lombok.Getter;

public enum TipoDeUsuario {

    Lojista("Lojista"),
    CLiente("Cliente");

    @Getter
    private final String descricao;

    TipoDeUsuario(String descricao) {
        this.descricao = descricao;
    }

}
