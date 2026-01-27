package com.javanauta.usuario.business;

import com.javanauta.usuario.business.converter.UsuarioConverter;
import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.Entity.Endereco;
import com.javanauta.usuario.infrastructure.Entity.Telefone;
import com.javanauta.usuario.infrastructure.Entity.Usuario;
import com.javanauta.usuario.infrastructure.Repository.EnderecoRepository;
import com.javanauta.usuario.infrastructure.Repository.TelefoneRepository;
import com.javanauta.usuario.infrastructure.Repository.UsuarioRepository;
import com.javanauta.usuario.infrastructure.exceptions.ConflictException;
import com.javanauta.usuario.infrastructure.exceptions.ResourceNotValid;
import com.javanauta.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioConverter  usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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
            throw new ConflictException("Email já cadastrado " + email);
        }
    }

    public UsuarioDTO retornarEmail(String email){
        try {

            return usuarioConverter.paraUsuarioDTO
                (usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new ConflictException("Email não encontrado: " + email)));

        } catch (Exception e) {
            throw new ConflictException("Email não encontrado " + email);
        }
    }

    public void deletarPorEmail(String email) {

        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizarUsuario (String token, UsuarioDTO usuarioDTO) {
        //Buscar o email através do token (tirar a obrigatoriedade do email)
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        //Se tiver uma nova senha, fazer encriptografia
        usuarioDTO.setSenha(usuarioDTO.getSenha() != null
                ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

        //Buscar os dados do usuário no banco de dados
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new RuntimeException("Email não encontrado " + usuarioDTO.getEmail()) );
        //usuarioEntity.setEmail(usuarioDTO.getEmail());
        //Mesclou os dados que recebeu na requisição DTO e atualizou o que foi passado no banco de dados
        Usuario usuario = usuarioConverter.atualizarUsuario(usuarioEntity ,usuarioDTO);
        //Salvou o que foi convertido e depois converteu usuario para usuarioDTO, tendo o retorno.
        usuarioRepository.save(usuario);
            return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {

        Endereco enderecoEntity = enderecoRepository.findById(idEndereco).orElseThrow(
                ()-> new ConflictException("ID do endereço não encontrado " + idEndereco));
        Endereco endereco = usuarioConverter.atualizarEndereco(enderecoEntity ,enderecoDTO);
        enderecoRepository.save(endereco);
            return usuarioConverter.paraEnderecoDTO(endereco);
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO) {

        Telefone telefoneEntity = telefoneRepository.findById(idTelefone).orElseThrow(
                ()-> new ConflictException("ID do telefone não encontrado " + idTelefone));
        Telefone telefone = usuarioConverter.atualizarTelefone(telefoneEntity, telefoneDTO);
        telefoneRepository.save(telefone);
            return usuarioConverter.paraTelefoneDTO(telefone);
    }

    public EnderecoDTO cadastroEnderecoDTO (String token ,EnderecoDTO enderecoDTO) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new ConflictException("Email não encontrado " + email)
        );
        Endereco endereco = usuarioConverter.paraEnderecoEntity(enderecoDTO, usuarioEntity.getId());

        Endereco enderecoEntity = enderecoRepository.save(endereco);

            return usuarioConverter.paraEnderecoDTO(enderecoEntity);

    }

    public TelefoneDTO cadastroTelefoneDTO (String token ,TelefoneDTO telefoneDTO) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));

        Usuario usuario = usuarioRepository.findByEmail(email).orElseThrow(
                ()-> new ConflictException("Email não encontrado " + email)
        );

        Telefone telefoneEntity = usuarioConverter.paraTelefoneEntity(telefoneDTO, usuario.getId());

        telefoneRepository.save(telefoneEntity);

            return usuarioConverter.paraTelefoneDTO(telefoneEntity);
    }
}
