package aps.microservice.miniautorizador.exception;

public class CartaoJaExisteException extends RuntimeException {
  public CartaoJaExisteException(String message) {
    super(message);
  }
}
