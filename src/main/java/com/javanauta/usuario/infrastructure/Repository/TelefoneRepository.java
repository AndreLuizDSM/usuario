package com.javanauta.usuario.infrastructure.Repository;

import com.javanauta.usuario.infrastructure.Entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Uma interface Repository para cada Entity
//O que colocar dentro do "< >" = Nome do entity, Tipo do ID
//Por que a Interface herda JpaRepository ? Para que não seja obrigatório o uso dos métodos, utilizamos quando for necessário
//Metodos save(), delete(), findAll(), findById()

@Repository
public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
