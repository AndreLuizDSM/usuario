package com.javanauta.usuario.business;

import com.javanauta.usuario.business.converter.UsuarioConverter;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.Entity.Usuario;
import com.javanauta.usuario.infrastructure.Repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter  usuarioConverter;

    public UsuarioDTO salvarUsuario (UsuarioDTO usuarioDTO) {   // Recebi um UsuarioDTO
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO); //Converti UsuarioDTO para UsuarioEntity
        usuario = usuarioRepository.save(usuario);  //Salvei no banco de dados o UsuarioEntity
        return usuarioConverter.paraUsuarioDTO(usuario);    //Converti UsuarioEntity para UsuarioDTO e retornei
    }
}
