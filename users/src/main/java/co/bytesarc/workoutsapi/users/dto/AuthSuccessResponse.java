package co.bytesarc.workoutsapi.users.dto;

import co.bytesarc.workoutsapi.users.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class AuthSuccessResponse extends AuthResponse{
    private AuthToken token;
}
