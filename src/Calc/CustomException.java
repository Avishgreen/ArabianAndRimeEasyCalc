package Calc;

public class CustomException extends Exception{
    private String message;
    public  CustomException(String errMsg){
        super(errMsg);
        message = errMsg;
    }
    public String getMessage(){
        return message;
    }
}
