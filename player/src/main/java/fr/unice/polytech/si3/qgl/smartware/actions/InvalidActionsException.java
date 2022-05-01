package fr.unice.polytech.si3.qgl.smartware.actions;

public class InvalidActionsException extends IllegalArgumentException{
    public InvalidActionsException(String errorMessage){
        super(errorMessage);
    }
}
