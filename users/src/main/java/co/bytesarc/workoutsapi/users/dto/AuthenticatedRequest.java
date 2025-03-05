package co.bytesarc.workoutsapi.users.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticatedRequest {
    private String accessToken;


}
