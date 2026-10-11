package com.Czupson.medical_clinic_proxy.dto;

public record ErrorMessageDto(
        int status,
        String message
) {}