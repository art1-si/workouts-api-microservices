package co.bytesarc.workoutsapi.users.service;

import co.bytesarc.workoutsapi.users.dto.AuthToken;
import co.bytesarc.workoutsapi.users.exceptions.InvalidCredentialsException;
import co.bytesarc.workoutsapi.users.exceptions.InvalidJwtException;
import co.bytesarc.workoutsapi.users.exceptions.UserNotFoundException;
import co.bytesarc.workoutsapi.users.exceptions.EmailAlreadyInUseException;
import co.bytesarc.workoutsapi.users.models.User;
import co.bytesarc.workoutsapi.users.repository.AuthRepository;
import co.bytesarc.workoutsapi.users.utils.JwtTokenProvider;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(AuthRepository authRepository, JwtTokenProvider jwtTokenProvider){
        this.authRepository = authRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    };

    public User createAccount(String email, String password) throws EmailAlreadyInUseException{
        if (authRepository.findUserByEmail(email)!=null){
            throw new EmailAlreadyInUseException(email);
        }
        String hashedPassword = BCrypt.hashpw(password,BCrypt.gensalt());

        return authRepository.createUser(email,hashedPassword);
    }

    public User login(String email, String password) throws UserNotFoundException, InvalidCredentialsException{
        User user = authRepository.findUserByEmail(email);
        if (user == null){
            throw new UserNotFoundException(email);
        }

        if (!BCrypt.checkpw(password, user.getPassword())){
            throw new InvalidCredentialsException();
        }

        return user;
    }

    public AuthToken generateToken(int userId){
        return jwtTokenProvider.generateAccessToken(userId);
    }

    public AuthToken refreshToken(String refreshToken) throws InvalidJwtException {
        return jwtTokenProvider.refreshToken(refreshToken);
    }


    public void validateToken(String accessToken, int userId) throws InvalidJwtException {
        jwtTokenProvider.validateToken(accessToken);
    }

  
}
