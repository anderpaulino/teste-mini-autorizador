package aps.microservice.miniautorizador.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aps.microservice.miniautorizador.controller.dto.CartaoDto;
import aps.microservice.miniautorizador.controller.mapper.CartaoMapper;
import aps.microservice.miniautorizador.usecase.cartao.CreateCartaoUseCase;
import aps.microservice.miniautorizador.usecase.cartao.GetCartaoUseCase;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/cartoes")
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class CartaoController {

    CreateCartaoUseCase createCartaoUseCase;
    GetCartaoUseCase getCartaoUseCase;
    CartaoMapper cartaoMapper;

    @PostMapping
    public ResponseEntity<CartaoDto> createCartao(@RequestBody CartaoDto cartao) {
      try {  
        return ResponseEntity.status(HttpStatus.CREATED).body(cartaoMapper.toDto(createCartaoUseCase.execute(cartaoMapper.toEntity(cartao))));
      } catch (Exception e) {

        log.error("Erro ao tentar salvar o cartão número {}", cartao.getNumeroCartao(), e);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(cartao);
      }
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<BigDecimal> getSaldo(@PathVariable String numeroCartao) {
      return getCartaoUseCase.execute(numeroCartao)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
    
}


