package co.bytesarc.workoutsapi.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthToken {
    private String accessToken;
    private String refreshToken;
}
