package com.javanauta.usuario.infrastructure.Repository;

import com.javanauta.usuario.infrastructure.Entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//Uma interface Repository para cada Entity
//O que colocar dentro do "< >" = Nome do entity, Tipo do ID
//Por que a Interface herda JpaRepository ? Para que não seja obrigatório o uso dos métodos, utilizamos quando for necessário
//Metodos save(), delete(), findAll(), findById()

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    Optional<Usuario> findByEmail(String email);    //Optional permite fazer a busca, e se não encontrar,não voltar erro

    @Transactional          //Evita erros ao deletar
    void deleteByEmail(String email);
}
