package co.bytesarc.workoutsapi.users.exceptions;

public class EmailAlreadyInUseException extends Exception{
    public final String message;

    public EmailAlreadyInUseException(String email){
        this.message = "Email: " + email + " is already in use!";
    }


}
