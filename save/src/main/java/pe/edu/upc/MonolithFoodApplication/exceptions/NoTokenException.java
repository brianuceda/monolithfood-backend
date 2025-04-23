package pe.edu.upc.MonolithFoodApplication.exceptions;

import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

public class NoTokenException extends RuntimeException {
  private ResponseDTO response;

  public NoTokenException(ResponseDTO response) {
    super(response.getMessage());
    this.response = response;
  }

  public NoTokenException(String response) {
    super(response);
    this.response.setStatusCode(401);
    this.response.setType(ResponseType.ERROR);
  }

  public NoTokenException() {
  }
  
  public ResponseDTO getResponse() {
    return response;
  }
}