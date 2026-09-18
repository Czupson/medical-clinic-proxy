package com.Czupson.medical_clinic_proxy.controller;

import com.Czupson.medical_clinic_proxy.dto.PageDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.AppointmentDto;
import com.Czupson.medical_clinic_proxy.service.MedicalClinicProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proxy/appointments")
@RequiredArgsConstructor
public class MedicalClinicProxyController {
    private final MedicalClinicProxyService medicalClinicProxyService;

    @GetMapping("/patient/{patientId}")
    public PageDto<AppointmentDto> getPatientAppointments(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return medicalClinicProxyService.getPatientAppointments(patientId, page, size);
    }
}