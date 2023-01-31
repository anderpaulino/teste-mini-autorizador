package aps.microservice.miniautorizador.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import aps.microservice.miniautorizador.model.Cartao;

public interface CartaoRepository extends JpaRepository<Cartao, UUID>{
    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
