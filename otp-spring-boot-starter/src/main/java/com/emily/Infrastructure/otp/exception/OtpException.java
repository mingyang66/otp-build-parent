package com.emily.Infrastructure.otp.exception;

public class OtpException extends RuntimeException {

    public OtpException(String message, Throwable cause) {
        super(message, cause);
    }

    public OtpException(String message) {
        super(message);
    }
}
