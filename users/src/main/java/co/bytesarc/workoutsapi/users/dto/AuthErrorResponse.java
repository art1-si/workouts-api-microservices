package co.bytesarc.workoutsapi.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class AuthErrorResponse extends AuthResponse {
    private String reason;
    private String message;



    public static AuthErrorResponse emailAlreadyInUse(String message){
        return new AuthErrorResponse(
                "EMAIL_USED",
                message
        );
    }

    public static AuthErrorResponse invalidCredentials(){
        return new AuthErrorResponse(
                "INVALID_CREDENTIALS",
                "Invalid Credentials."
        );
    }

    public static AuthErrorResponse invalidTokenToken(){
        return new AuthErrorResponse(
                "INVALID_TOKEN",
                "Invalid Token."
        );
    }

    public static AuthErrorResponse invalidRefreshToken(){
        return new AuthErrorResponse(
                "INVALID_REFRESH_TOKEN",
                "Invalid Refresh Token."
        );
    }
}
