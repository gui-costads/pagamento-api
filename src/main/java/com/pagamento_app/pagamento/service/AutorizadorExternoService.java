package com.pagamento_app.pagamento.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutorizadorExternoService {
        private final RestTemplate restTemplate;
        private static final String AUTHORIZATION_URL = "https://util.devi.tools/api/v2/authorize";
    
        public boolean autorizarTransferencia() {
            try {
                ResponseEntity<Map> response = restTemplate.getForEntity(AUTHORIZATION_URL, Map.class);
                return response.getStatusCode() == HttpStatus.OK;
            } catch (Exception e) {
                throw new RuntimeException("Serviço autorizador indisponível");
            }
        }
    }
