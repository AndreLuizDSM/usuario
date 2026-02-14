package com.javanauta.usuario.controller;

import com.javanauta.usuario.business.UsuarioService;
import com.javanauta.usuario.business.ViaCepService;
import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.business.dto.ViaCepDTO;
import com.javanauta.usuario.infrastructure.security.JwtUtil;
import com.javanauta.usuario.infrastructure.security.SecurityConfig;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/usuario")
@RestController
@RequiredArgsConstructor
@Tag(name = "Usuário", description = "Cadastro e login de usuários")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class UsuarioController {

    private final ViaCepService cepService;
    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTO));
    }

    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTO> salvarEndereco(@RequestHeader(name = "Authorization", required = false) String token,
                                                      @RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.ok(usuarioService.cadastroEnderecoDTO(token, enderecoDTO));
    }

    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO> salvarTelefone(@RequestHeader(name = "Authorization", required = false) String token,
                                                      @RequestBody TelefoneDTO telefoneDTO){
        return ResponseEntity.ok(usuarioService.cadastroTelefoneDTO(token, telefoneDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO usuarioDTO){
        return ResponseEntity.ok(usuarioService.autenticadorJWT(usuarioDTO));
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscarEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(usuarioService.retornarEmail(email));
    }

    @DeleteMapping("/{email}") // var na URI , nome deve ser igual ao parâmetro
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletarPorEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizarDadosUsuario(@RequestBody UsuarioDTO usuarioDTO,
                                                            @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(token, usuarioDTO));
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizarDadosEndereco(@RequestBody EnderecoDTO enderecoDTO,
                                                              @RequestParam("id") Long id) {
        return ResponseEntity.ok(usuarioService.atualizaEndereco(id, enderecoDTO));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizarDadosTelefone(@RequestBody TelefoneDTO telefoneDTO,
                                                              @RequestParam("id") Long id) {
        return ResponseEntity.ok(usuarioService.atualizaTelefone(id, telefoneDTO));
    }

    @GetMapping("/endereco/{cep}/")
    public ResponseEntity<ViaCepDTO> buscarCep(@PathVariable("cep") String cep) {
        return ResponseEntity.ok(cepService.buscaCep(cep));
    }
}
