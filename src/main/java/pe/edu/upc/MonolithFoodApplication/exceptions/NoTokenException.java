package pe.edu.upc.MonolithFoodApplication.exceptions;

import pe.edu.upc.MonolithFoodApplication.dtos.ResponseDTO;

public class NoTokenException extends RuntimeException {

  private ResponseDTO response;

  public NoTokenException(ResponseDTO response) {
    super(response.getMessage());
    this.response = response;
  }
  public NoTokenException(String response) {
    super(response);
    this.response.setStatusCode(401);
  }
  public NoTokenException() {
  }

  public ResponseDTO getResponse() {
    return response;
  }

}