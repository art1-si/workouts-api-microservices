package co.bytesarc.workoutsapi.users.controllers;

import co.bytesarc.workoutsapi.users.dto.*;
import co.bytesarc.workoutsapi.users.exceptions.EmailAlreadyInUseException;
import co.bytesarc.workoutsapi.users.exceptions.InvalidCredentialsException;
import co.bytesarc.workoutsapi.users.exceptions.InvalidJwtException;
import co.bytesarc.workoutsapi.users.exceptions.UserNotFoundException;
import co.bytesarc.workoutsapi.users.models.User;
import co.bytesarc.workoutsapi.users.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthenticationRequest request){
        try{
            User user = authService.createAccount(request.getEmail(), request.getPassword());
            AuthToken token = authService.generateToken(user.getId());
            AuthResponse response = new AuthSuccessResponse(token);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (EmailAlreadyInUseException eaiue){
            return new ResponseEntity<>(AuthErrorResponse.emailAlreadyInUse(eaiue.message),HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse>login(@RequestBody AuthenticationRequest request){
        try {
            User user = authService.login(request.getEmail(), request.getPassword());
            AuthToken token = authService.generateToken(user.getId());
            AuthResponse response = new AuthSuccessResponse( token);
            return ResponseEntity.ok(response);
        } catch (InvalidCredentialsException | UserNotFoundException  e){
            return new ResponseEntity<>(AuthErrorResponse.invalidCredentials(),HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshRequest) {
        try{
        AuthToken newAccessToken = authService.refreshToken(refreshRequest.getRefreshToken());

        return ResponseEntity.ok().body(new AuthSuccessResponse( newAccessToken));

        }catch (InvalidJwtException ije){
            return new ResponseEntity<>(AuthErrorResponse.invalidRefreshToken(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/validateToken")
    public ResponseEntity<AuthResponse> validateToken(@RequestHeader String accessToken) {
        try{
            authService.validateToken(accessToken,1);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }catch (InvalidJwtException ije){
            return new ResponseEntity<>(AuthErrorResponse.invalidTokenToken(), HttpStatus.UNAUTHORIZED);
        }
    }
}
