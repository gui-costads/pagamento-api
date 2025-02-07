package com.pagamento_app.pagamento.service;

import com.pagamento_app.pagamento.entity.TransferenciaJPA;
import com.pagamento_app.pagamento.entity.UsuarioJPA;
import com.pagamento_app.pagamento.model.Transferencia;
import com.pagamento_app.pagamento.repository.TransferenciaRepository;
import com.pagamento_app.pagamento.types.enums.TipoDeUsuario;
import com.pagamento_app.pagamento.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TransferenciaService {

    private TransferenciaRepository transferenciaRepository;
    private ModelMapper modelMapper;
    private UsuarioRepository usuarioRepository;
    private AutorizadorExternoService autorizadorExternoService;

    public Transferencia salvar(Transferencia transferencia) {

        if(!autorizadorExternoService.autorizarTransferencia())
        validarTransferencia(transferencia);
        TransferenciaJPA transferenciaJPA = modelMapper.map(transferencia, TransferenciaJPA.class);
        transferenciaJPA = transferenciaRepository.save(transferenciaJPA);
        return modelMapper.map(transferenciaJPA, Transferencia.class);
    }

    public List<Transferencia> listarTransferenciasDePagador(Integer id) {
        List<TransferenciaJPA> transferencias = transferenciaRepository.findByPagadorID(id);
        return transferencias.stream().map(transferenciaJPA -> modelMapper.map(transferenciaJPA, Transferencia.class)).toList();
    }
    

    private void validarTransferencia(Transferencia transferencia) {
        UsuarioJPA pagador = usuarioRepository.findById(transferencia.getPagadorId())
            .orElseThrow(() -> new IllegalArgumentException("Pagador não encontrado"));
            
        UsuarioJPA beneficiario = usuarioRepository.findById(transferencia.getRecebedorId())
            .orElseThrow(() -> new IllegalArgumentException("Beneficiário não encontrado"));
            
        if (pagador.getTipoDeUsuario() == TipoDeUsuario.LOJISTA) {
            throw new IllegalArgumentException("Lojistas não podem realizar transferências");
        }
        
        if (pagador.getSaldo().compareTo(transferencia.getValor()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar a transferência");
        }
        
        if (transferencia.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero");
        }
    }
    
    


}
