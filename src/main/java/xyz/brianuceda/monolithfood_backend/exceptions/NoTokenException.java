package xyz.brianuceda.monolithfood_backend.exceptions;

import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

public class NoTokenException extends RuntimeException {
  private ResponseDTO response;

  public NoTokenException(ResponseDTO response) {
    super(response.getMessage());
    this.response = response;
  }

  public NoTokenException(String response) {
    super(response);
    this.response = new ResponseDTO();
    this.response.setStatusCode(401);
    this.response.setType(ResponseType.ERROR);
  }

  public NoTokenException() {
  }
  
  public ResponseDTO getResponse() {
    return response;
  }
}