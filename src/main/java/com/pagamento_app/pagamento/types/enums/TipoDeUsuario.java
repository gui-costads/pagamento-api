package com.pagamento_app.pagamento.types.enums;

import lombok.Getter;

public enum TipoDeUsuario {

    LOJISTA("Lojista"),
    CLIENTE("Cliente");

    @Getter
    private final String descricao;

    TipoDeUsuario(String descricao) {
        this.descricao = descricao;
    }

}
