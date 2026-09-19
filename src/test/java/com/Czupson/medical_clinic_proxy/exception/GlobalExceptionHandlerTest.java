package com.Czupson.medical_clinic_proxy.exception;

import com.Czupson.medical_clinic_proxy.dto.ErrorMessageDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleMedicalClinicUnavailable_ReturnsErrorMessage() {
        // given
        MedicalClinicUnavailableException exception = new MedicalClinicUnavailableException();
        // when
        ErrorMessageDto result = handler.handleMedicalClinicUnavailable(exception);
        // then
        assertEquals(503, result.status());
        assertEquals("Medical clinic service is currently unavailable", result.message());
    }
}