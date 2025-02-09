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
import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@RequiredArgsConstructor
@Service
public class TransferenciaService {

    private TransferenciaRepository transferenciaRepository;
    private ModelMapper modelMapper;
    private UsuarioRepository usuarioRepository;
    private AutorizadorExternoService autorizadorExternoService;
    private NotificacaoService notificacaoService;

    public Transferencia salvar(Transferencia transferencia) {

        if(!autorizadorExternoService.autorizarTransferencia()){
            throw new RuntimeException("Transferência não autorizada");
        }
        
        HashMap<String,UsuarioJPA> usuarios = validarTransferencia(transferencia);

        UsuarioJPA pagador = usuarios.get("Pagador");
        UsuarioJPA recebedor = usuarios.get("Recebedor");

         pagador.setSaldo(pagador.getSaldo().subtract(transferencia.getValor()));
        recebedor.setSaldo(recebedor.getSaldo().add(transferencia.getValor()));
         
         usuarioRepository.save(pagador);
         usuarioRepository.save(recebedor);

        TransferenciaJPA transferenciaJPA = modelMapper.map(transferencia, TransferenciaJPA.class);
        transferenciaJPA = transferenciaRepository.save(transferenciaJPA);

        CompletableFuture.runAsync(() -> {
            notificacaoService.enviarNotificacao(recebedor, transferencia.getValor());
        });

        return modelMapper.map(transferenciaJPA, Transferencia.class);
    }

    public List<Transferencia> listarTransferenciasDePagador(Integer id) {
        List<TransferenciaJPA> transferencias = transferenciaRepository.findByPagadorId(id);
        return transferencias.stream().map(transferenciaJPA -> modelMapper.map(transferenciaJPA, Transferencia.class)).toList();
    }

    public List<Transferencia> listarTransferenciasDeRecebedor(Integer id) {
        List<TransferenciaJPA> transferencias = transferenciaRepository.findByRecebedorId(id);
        return transferencias.stream().map(transferenciaJPA -> modelMapper.map(transferenciaJPA, Transferencia.class)).toList();
    }


    private HashMap<String,UsuarioJPA> validarTransferencia(Transferencia transferencia) {

        HashMap<String,UsuarioJPA> usuarios = new HashMap<>();

        UsuarioJPA pagador = usuarioRepository.findById(transferencia.getPagadorId())
            .orElseThrow(() -> new IllegalArgumentException("Pagador não encontrado"));
            
        UsuarioJPA recebedor = usuarioRepository.findById(transferencia.getRecebedorId())
            .orElseThrow(() -> new IllegalArgumentException("Recebedor não encontrado"));
            
        if (pagador.getTipoDeUsuario() == TipoDeUsuario.LOJISTA) {
            throw new IllegalArgumentException("Lojistas não podem realizar transferências");
        }
        
        if (pagador.getSaldo().compareTo(transferencia.getValor()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar a transferência");
        }
        
        if (transferencia.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero");
        }

        usuarios.put("Pagador", pagador);
        usuarios.put("Recebedor", recebedor);

        return usuarios;


    }


    


}
