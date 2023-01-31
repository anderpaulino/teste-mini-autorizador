package aps.microservice.miniautorizador.exception;

import aps.microservice.miniautorizador.exception.category.ExceptionEnum;

public class CartaoInexistenteException extends RuntimeException {
    public CartaoInexistenteException() {
      super(ExceptionEnum.CARTAO_INEXISTENTE.toString());
    }
  }
