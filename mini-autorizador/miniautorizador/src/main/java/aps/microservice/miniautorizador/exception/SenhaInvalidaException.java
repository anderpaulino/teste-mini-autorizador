package aps.microservice.miniautorizador.exception;

import aps.microservice.miniautorizador.exception.category.ExceptionEnum;

public class SenhaInvalidaException extends RuntimeException {
    public SenhaInvalidaException() {
      super(ExceptionEnum.SENHA_INVALIDA.toString());
    }
  }
