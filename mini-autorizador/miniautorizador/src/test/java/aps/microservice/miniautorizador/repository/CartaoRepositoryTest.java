package aps.microservice.miniautorizador.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import aps.microservice.miniautorizador.model.Cartao;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
public class CartaoRepositoryTest {
  @Autowired
  private CartaoRepository cartaoRepository;

  @Test
  public void whenFindByNumeroCartao_thenReturnCartao() {
    Cartao cartao = new Cartao(null, "23467745645676005456363456345", "secret", BigDecimal.valueOf(500.0));
    cartaoRepository.save(cartao);

    Cartao found = cartaoRepository.findByNumeroCartao("23467745645676005456363456345").get();
    assertThat(found.getNumeroCartao()).isEqualTo(cartao.getNumeroCartao());
  }

  @Test
  public void whenFindByNumeroCartao_thenReturnEmptyOptional() {
    Optional<Cartao> found = cartaoRepository.findByNumeroCartao("non-existent-card-number");
    assertThat(found.isPresent()).isFalse();
  }
}
