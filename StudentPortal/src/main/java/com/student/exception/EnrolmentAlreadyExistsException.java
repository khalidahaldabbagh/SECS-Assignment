package com.student.exception;

public class EnrolmentAlreadyExistsException extends RuntimeException {
    public EnrolmentAlreadyExistsException() {
        super("This enrolment already exists.");
    }
    public EnrolmentAlreadyExistsException(String message) {
        super(message);
    }
}
