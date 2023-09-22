package pe.edu.upc.MonolithFoodApplication.exceptions;

import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

public class BlacklistedTokenException extends RuntimeException {

  private ResponseDTO response;

  public BlacklistedTokenException(ResponseDTO response) {
    super(response.getMessage());
    this.response = response;
  }
  public BlacklistedTokenException(String response) {
    super(response);
    this.response.setStatusCode(401);
  }
  public BlacklistedTokenException() {
  }

  public ResponseDTO getResponse() {
    return response;
  }

}