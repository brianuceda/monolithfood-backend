package xyz.brianuceda.monolithfood_backend.exceptions;

import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

public class BlacklistedTokenException extends RuntimeException {
  private ResponseDTO response;

  public BlacklistedTokenException(ResponseDTO response) {
    super(response.getMessage());
    this.response = response;
  }

  public BlacklistedTokenException(String response) {
    super(response);
    this.response = new ResponseDTO();
    this.response.setStatusCode(401);
    this.response.setType(ResponseType.ERROR);
  }

  public BlacklistedTokenException() {
  }

  public ResponseDTO getResponse() {
    return response;
  }
}