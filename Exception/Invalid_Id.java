package Exception;

public class Invalid_Id extends RuntimeException {
    //This class is used for a customize exception
    public Invalid_Id(String message)
    {
        super(message);
    }
}
