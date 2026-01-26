package com.javanauta.usuario.business;

import com.javanauta.usuario.business.converter.UsuarioConverter;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.Entity.Usuario;
import com.javanauta.usuario.infrastructure.Repository.UsuarioRepository;
import com.javanauta.usuario.infrastructure.exceptions.ConflictException;
import com.javanauta.usuario.infrastructure.exceptions.ResourceNotValid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter  usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvarUsuario (UsuarioDTO usuarioDTO) {   // Recebi um UsuarioDTO
        existsByEmail(usuarioDTO.getEmail());

        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));

        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO); //Converti UsuarioDTO para UsuarioEntity
        usuario = usuarioRepository.save(usuario);  //Salvei no banco de dados o UsuarioEntity
        return usuarioConverter.paraUsuarioDTO(usuario);    //Converti UsuarioEntity para UsuarioDTO e retornei
    }

    public void existsByEmail(String email) {
        if(!email.contains("@") || !email.contains(".")) {
            throw new ResourceNotValid("Email incompleto, acrescente '@' ou '.' " + email );
        }
        boolean existe = usuarioRepository.existsByEmail(email);
        if (existe) {
            throw new ConflictException("Email já existe: " + email);
        }
    }

    public Usuario retornarEmail(String email){
        //Usuario.builder().build().getEmail();
        //UsuarioDTO usuarioDTO = usuarioConverter.paraUsuarioDTO(email);
        return usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new ConflictException("Email não encontrado: " + email));

    }

    public void deletarPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }
}
