package com.pagamento_app.pagamento.service;

import com.pagamento_app.pagamento.entity.UsuarioJPA;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class NotificacaoService {
    private final RestTemplate restTemplate;
    private static final String NOTIFICATION_URL = "https://util.devi.tools/api/v1/notify";

    private final Logger log = LoggerFactory.getLogger(NotificacaoService.class);

    public void enviarNotificacao(UsuarioJPA usuario, BigDecimal valor) {
        Map<String, Object> request = new HashMap<>();
        request.put("email", usuario.getEmail());
        request.put("valor", valor);
        request.put("mensagem", "Você recebeu uma transferência de R$ " + valor);

        try {
            restTemplate.postForEntity(NOTIFICATION_URL, request, Void.class);
        } catch (Exception e) {
            // Log error but don't throw exception since notification is not critical
            log.error("Erro ao enviar notificação: " + e.getMessage());
        }
    }
}
