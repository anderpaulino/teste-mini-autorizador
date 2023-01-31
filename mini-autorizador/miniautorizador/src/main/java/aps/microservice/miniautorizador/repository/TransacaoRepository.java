package aps.microservice.miniautorizador.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import aps.microservice.miniautorizador.model.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, UUID>{
}
