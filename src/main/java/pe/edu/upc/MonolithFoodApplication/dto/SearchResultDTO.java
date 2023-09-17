package pe.edu.upc.MonolithFoodApplication.dto;

public class SearchResultDTO {
    private String message;

    public SearchResultDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


