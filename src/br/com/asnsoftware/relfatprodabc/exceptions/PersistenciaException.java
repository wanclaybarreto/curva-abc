package br.com.asnsoftware.relfatprodabc.exceptions;

public class PersistenciaException extends Exception {
    
    public PersistenciaException (String msg) {	
            super(msg);	
    }
    public PersistenciaException (String msg, Exception e) {
            super(msg, e);
    }
    
}
