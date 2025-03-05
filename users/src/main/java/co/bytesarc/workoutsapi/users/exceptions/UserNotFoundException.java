package co.bytesarc.workoutsapi.users.exceptions;

public class UserNotFoundException extends Exception{
    public final String email;

    public UserNotFoundException(String email){
        this.email = email;
    }
}
