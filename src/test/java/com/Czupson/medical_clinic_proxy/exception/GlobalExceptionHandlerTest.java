package com.Czupson.medical_clinic_proxy.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    @Test
    void handleMedicalClinicUnavailable_ReturnsExceptionMessage() {
        // given
        MedicalClinicUnavailableException exception = new MedicalClinicUnavailableException();
        // when
        String result = handler.handleMedicalClinicUnavailable(exception);
        // then
        assertEquals("Medical clinic service is currently unavailable", result);
    }
}