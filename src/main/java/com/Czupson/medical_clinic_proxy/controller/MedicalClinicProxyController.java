package com.Czupson.medical_clinic_proxy.controller;

import com.Czupson.medical_clinic_proxy.dto.PageDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.AppointmentDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.BookAppointmentCommand;
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

    @PatchMapping("/{id}/book")
    public AppointmentDto bookAppointment(
            @PathVariable Long id,
            @RequestBody BookAppointmentCommand command) {
        return medicalClinicProxyService.bookAppointment(id, command);
    }

    @GetMapping("/doctor/{doctorId}/available")
    public PageDto<AppointmentDto> getAvailableAppointmentsForDoctor(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return medicalClinicProxyService.getAvailableAppointmentsForDoctor(doctorId, page, size);
    }
}