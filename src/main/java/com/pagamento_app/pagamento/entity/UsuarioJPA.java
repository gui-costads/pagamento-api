package com.pagamento_app.pagamento.entity;

import com.pagamento_app.pagamento.model.Transferencia;
import com.pagamento_app.pagamento.types.enums.TipoDeUsuario;
import com.pagamento_app.pagamento.validation.ValidEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "usuario")
public class UsuarioJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres")
    @Column(nullable = false, name = "nome")
    private String nome;

    @NotBlank(message = "Sobrenome é obrigatório")
    @Size(min = 3, max = 100, message = "Sobrenome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false, name = "sobrenome")
    private String sobrenome;

    @NotBlank(message = "CPF/CNPJ é obrigatório")
    @Pattern(
            regexp = "^(\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}|\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2})$",
            message = "Documento inválido (Use formato CPF: 123.456.789-10 ou CNPJ: 12.345.678/0001-99)"
    )
    @Column(nullable = false, unique = true)
    private String documento;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    @Column(nullable = false, name = "email")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Column(nullable = false, name = "senha")
    private String senha;

    @ValidEnum(
            enumClass = TipoDeUsuario.class,
            message="Tipo de usuário inválido",
            ignoreCase=true
    )
    @Column(nullable = false, name = "tipo_de_usuario")
    @Enumerated(EnumType.STRING)
    private TipoDeUsuario tipoDeUsuario;

    @OneToMany(mappedBy = "pagador", cascade = CascadeType.ALL)
    private List<TransferenciaJPA> transferenciasEnviadas;

    @OneToMany(mappedBy = "recebedor", cascade = CascadeType.ALL)
    private List<TransferenciaJPA> transferenciasRecebidas;

}
