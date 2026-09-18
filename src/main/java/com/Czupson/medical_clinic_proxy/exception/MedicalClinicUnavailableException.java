package com.Czupson.medical_clinic_proxy.exception;

public class MedicalClinicUnavailableException extends RuntimeException {
    public MedicalClinicUnavailableException() {
        super("Medical clinic service is currently unavailable");
    }
}
