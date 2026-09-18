package com.Czupson.medical_clinic_proxy.client;

import com.Czupson.medical_clinic_proxy.config.FeignConfiguration;
import com.Czupson.medical_clinic_proxy.dto.PageDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.AppointmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(
        name = "medicalClinicClient",
        url = "${medical-clinic.url}",
        configuration = FeignConfiguration.class,
        fallbackFactory = MedicalClinicFallbackFactory.class
)
public interface MedicalClinicClient {

    @GetMapping("/api/appointments/patient/{patientId}")
    PageDto<AppointmentDto> getPatientAppointments(
            @PathVariable Long patientId,
            @RequestParam int page,
            @RequestParam int size
    );
}