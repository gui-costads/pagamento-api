package com.pagamento_app.pagamento.entity;

import com.pagamento_app.pagamento.exception.UsuarioNotFoundException;
import com.pagamento_app.pagamento.model.Usuario;
import com.pagamento_app.pagamento.repository.UsuarioRepository;
import com.pagamento_app.pagamento.service.UsuarioService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.pagamento_app.pagamento.types.enums.TipoDeUsuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UsuarioJPATest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Spy
    private UsuarioRepository usuarioRepository;

    @Spy
    ModelMapper modelMapper;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

   @Test
   public void testListarUsuarios() {
       UsuarioJPA usuario1 = new UsuarioJPA();
       usuario1.setId(1);
       usuario1.setNome("John");
       usuario1.setSobrenome("Doe");

       UsuarioJPA usuario2 = new UsuarioJPA();
       usuario2.setId(2);
       usuario2.setNome("Jane");
       usuario2.setSobrenome("Doe");

       when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

       Usuario usuarioModel1 = new Usuario();
       usuarioModel1.setNome("John");

       Usuario usuarioModel2 = new Usuario();
       usuarioModel2.setNome("Jane");

       when(modelMapper.map(usuario1, Usuario.class)).thenReturn(usuarioModel1);
       when(modelMapper.map(usuario2, Usuario.class)).thenReturn(usuarioModel2);

       List<Usuario> usuarios = usuarioService.listarUsuarios();
       assertEquals(2, usuarios.size());
       assertEquals("John", usuarios.get(0).getNome());
       assertEquals("Jane", usuarios.get(1).getNome());
   }

    @Test
    public void testBuscarPorId() {
        UsuarioJPA usuarioJPA = new UsuarioJPA();
        usuarioJPA.setId(1);
        usuarioJPA.setNome("John");
        usuarioJPA.setSobrenome("Doe");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioJPA));

        Usuario usuario = usuarioService.buscarPorId(1);
        assertNotNull(usuario);
        assertEquals("John", usuario.getNome());
    }

    @Test
    public void testSalvarUsuario() {
        Usuario usuarioInput = new Usuario();
        usuarioInput.setNome("John");
        usuarioInput.setSobrenome("Doe");
        usuarioInput.setTipoDeUsuario(TipoDeUsuario.CLIENTE);
        usuarioInput.setSaldo(new BigDecimal(0));
        usuarioInput.setEmail("email@email.com");
        usuarioInput.setSenha("senha");
        usuarioInput.setDocumento("123456789");
    
        UsuarioJPA usuarioJPA = new UsuarioJPA();
        usuarioJPA.setId(1);
        usuarioJPA.setNome("John");
        usuarioJPA.setSobrenome("Doe");
        usuarioJPA.setTipoDeUsuario(TipoDeUsuario.CLIENTE);
        usuarioJPA.setSaldo(new BigDecimal(0));
        usuarioJPA.setEmail("email@email.com");
        usuarioJPA.setSenha("senha");
        usuarioJPA.setDocumento("123456789");
    
        when(usuarioRepository.save(any(UsuarioJPA.class))).thenReturn(usuarioJPA);
        
        Usuario usuario = usuarioService.salvar(usuarioInput);
        
        assertNotNull(usuario);
        assertEquals("John", usuario.getNome());
        assertEquals("Doe", usuario.getSobrenome());
        assertEquals(TipoDeUsuario.CLIENTE, usuario.getTipoDeUsuario());
        assertEquals(new BigDecimal(0), usuario.getSaldo());
        assertEquals("email@email.com", usuario.getEmail());
        assertEquals("senha", usuario.getSenha());
        assertEquals("123456789", usuario.getDocumento());
    }

    @Test
    public void testExcluirUsuario() {
        usuarioService.excluir(1);

        verify(usuarioRepository, times(1)).deleteById(1);
    }

}
