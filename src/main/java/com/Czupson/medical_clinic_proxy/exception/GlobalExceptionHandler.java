package com.Czupson.medical_clinic_proxy.exception;

import com.Czupson.medical_clinic_proxy.dto.ErrorMessageDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MedicalClinicUnavailableException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorMessageDto handleMedicalClinicUnavailable(MedicalClinicUnavailableException exception) {
        return new ErrorMessageDto(HttpStatus.SERVICE_UNAVAILABLE.value(), exception.getMessage());
    }
}