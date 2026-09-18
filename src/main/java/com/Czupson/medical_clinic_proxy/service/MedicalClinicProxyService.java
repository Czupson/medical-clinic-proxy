package com.Czupson.medical_clinic_proxy.service;

import com.Czupson.medical_clinic_proxy.client.MedicalClinicClient;
import com.Czupson.medical_clinic_proxy.dto.PageDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.AppointmentDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.BookAppointmentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicalClinicProxyService {

    private final MedicalClinicClient medicalClinicClient;

    public PageDto<AppointmentDto> getPatientAppointments(Long patientId, int page, int size) {
        return medicalClinicClient.getPatientAppointments(patientId, page, size);
    }

    public AppointmentDto bookAppointment(Long id, BookAppointmentCommand command) {
        return medicalClinicClient.bookAppointment(id, command);
    }
}