package xyz.brianuceda.monolithfood_backend.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2PrincipalDTO {
    private String username;
    private String password;
    private String email;
    private String names;
    private String profileImg;
    private String oauthProviderId;
    @Builder.Default
    private Boolean isOauthRegistered = true;
    
}
