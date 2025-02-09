package com.pagamento_app.pagamento.service;

import com.pagamento_app.pagamento.entity.UsuarioJPA;
import com.pagamento_app.pagamento.exception.UsuarioNotFoundException;
import com.pagamento_app.pagamento.model.Usuario;
import com.pagamento_app.pagamento.repository.UsuarioRepository;

import java.util.List;

import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;


    public List<Usuario> listarUsuarios() {

        return usuarioRepository.findAll().stream().map(usuario -> modelMapper.map(usuario, Usuario.class)).toList();
    }

    public Usuario buscarPorId(Integer id) {
        return modelMapper.map(usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuário não encontrado com o ID: " + id)), Usuario.class);
    }

    public Usuario salvar(Usuario usuario) {
        UsuarioJPA usuarioJPA = modelMapper.map(usuario, UsuarioJPA.class);
        usuarioJPA = usuarioRepository.save(usuarioJPA);
        return modelMapper.map(usuarioJPA, Usuario.class);
    }

    public void excluir(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
