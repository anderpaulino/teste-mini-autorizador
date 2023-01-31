package aps.microservice.miniautorizador.controller.mapper;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import org.mapstruct.Named;

import jakarta.xml.bind.DatatypeConverter;

public interface MD5Mapper {

  @Named("senha")
  default String toMd5(String senha) {
    return Optional.ofNullable(senha).map(s -> {
      MessageDigest md;
      try {
          md = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException e) {
          throw new RuntimeException(e);
      }
      md.update(("SEGREDO" + senha).getBytes());
      byte[] digest = md.digest();
      String myHash = DatatypeConverter
        .printHexBinary(digest).toUpperCase();
  
      return myHash;
    }).orElse(null);
  }
}
