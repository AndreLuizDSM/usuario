package com.javanauta.usuario.business.converter;

import com.javanauta.usuario.business.dto.EnderecoDTO;
import com.javanauta.usuario.business.dto.TelefoneDTO;
import com.javanauta.usuario.business.dto.UsuarioDTO;
import com.javanauta.usuario.infrastructure.Entity.Endereco;
import com.javanauta.usuario.infrastructure.Entity.Telefone;
import com.javanauta.usuario.infrastructure.Entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
    Converter os dados que entram como ClasseDTO para uma ClasseEntity
    e Converter os dados de uma ClasseEntity para uma ClasseDTO
 */

@Component
public class UsuarioConverter {

    // Converter ClasseEntity -> ClasseDTO
    public UsuarioDTO paraUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecoDTO(usuario.getEnderecos()))    /* Cada endereco e telefone salvo
                fará o seu build utilizando os metodos abaixo
                */
                .telefones(paraListaTelefoneDTO(usuario.getTelefones()))
                .build();

    }

    public List<EnderecoDTO> paraListaEnderecoDTO(List<Endereco> enderecos) {
        List<EnderecoDTO> listaEnderecos = new ArrayList<>();
        for (Endereco enderecoDTO : enderecos) {
            listaEnderecos.add(paraEnderecoDTO(enderecoDTO));
        }
        return listaEnderecos;
    }

    public EnderecoDTO paraEnderecoDTO(Endereco endereco){
        return EnderecoDTO.builder()
                .cep(endereco.getCep())
                .rua(endereco.getRua())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .estado(endereco.getEstado())
                .numero(endereco.getNumero())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefoneDTO(List<Telefone> telefone) {
        return telefone.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(Telefone telefone) {
        return TelefoneDTO.builder()
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .build();
    }

    public UsuarioDTO atualizarUsuarioDTO(Usuario usuarioEntity, UsuarioDTO usuarioDTO){
        return UsuarioDTO.builder()
                .nome(usuarioDTO != null ? usuarioDTO.getNome(): usuarioEntity.getSenha())
                .senha(usuarioDTO != null ? usuarioDTO.getSenha(): usuarioEntity.getSenha())
                .email(usuarioDTO != null ? usuarioDTO.getEmail(): usuarioEntity.getSenha())
                .enderecos(usuarioDTO.getEnderecos())
                .telefones(usuarioDTO.getTelefones())
                .build();
    }

    // Converter ClasseDTO -> ClasseEntity
    public Usuario paraUsuario(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEndereco(usuarioDTO.getEnderecos()))    /* Cada endereco e telefone salvo
                fará o seu build utilizando os metodos abaixo
                */
                .telefones(paraListaTelefone(usuarioDTO.getTelefones()))
                .build();

        //    Usuario usuario = new Usuario();
        //    usuario.setEmail(usuarioDTO.getEmail());
        //    usuario.setNome(usuarioDTO.getNome());
    }

    public List<Endereco> paraListaEndereco(List<EnderecoDTO> enderecosDTOS) {
        List<Endereco> enderecos = new ArrayList<>();
        for (EnderecoDTO enderecoDTO : enderecosDTOS) {
            enderecos.add(paraEndereco(enderecoDTO));
        }
        return enderecos;
    }

    public Endereco paraEndereco(EnderecoDTO enderecoDTO){
        return Endereco.builder()
                .cep(enderecoDTO.getCep())
                .rua(enderecoDTO.getRua())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .estado(enderecoDTO.getEstado())
                .numero(enderecoDTO.getNumero())
                .build();
    }

    // Metodo para converter List<Telefone>, e cada Telefone no Array fará um build com o metodo paraTelefone()
    public List<Telefone> paraListaTelefone(List<TelefoneDTO> telefoneDTOS) {
        return telefoneDTOS.stream().map(this::paraTelefone).toList();
    }

    // Metodo para fazer um biuld em cada parâmetro TelefoneDTO
    public Telefone paraTelefone(TelefoneDTO telefoneDTOS) {
        return Telefone.builder()
                .ddd(telefoneDTOS.ddd())
                .numero(telefoneDTOS.numero())
                .build();
    }

    public Usuario atualizarUsuario(Usuario usuarioEntity, UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioEntity != null ? usuarioEntity.getNome() : usuarioDTO.getNome())
                .senha(usuarioEntity != null ? usuarioEntity.getSenha() : usuarioDTO.getSenha())
                .email(usuarioDTO != null ? usuarioDTO.getEmail() : usuarioEntity.getEmail())
                .enderecos(usuarioEntity.getEnderecos())
                .telefones(usuarioEntity.getTelefones())
                .id(usuarioEntity.getId())
                .build();
    }
}
