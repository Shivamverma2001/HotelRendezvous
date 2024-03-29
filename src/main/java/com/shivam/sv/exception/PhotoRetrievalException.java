package com.shivam.sv.exception;

public class PhotoRetrievalException extends RuntimeException {
    public PhotoRetrievalException(String errorRetrievingPhoto) {
        super(errorRetrievingPhoto);
    }
}
