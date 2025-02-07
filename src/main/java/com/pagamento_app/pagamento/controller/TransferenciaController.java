package com.pagamento_app.pagamento.controller;

import com.pagamento_app.pagamento.model.Transferencia;
import com.pagamento_app.pagamento.service.TransferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequestMapping("/transferencia")
@RequiredArgsConstructor
public class TransferenciaController {
    
    private final TransferenciaService transferenciaService;

    @GetMapping("/pagador/{id}")
    public ResponseEntity<List<Transferencia>> listarTransferenciasPorPagadorId(Integer id) {
        return ResponseEntity.ok(transferenciaService.listarTransferenciasDePagador(id));
    }

}
