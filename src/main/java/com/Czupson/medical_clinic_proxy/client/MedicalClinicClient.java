package com.Czupson.medical_clinic_proxy.client;

import com.Czupson.medical_clinic_proxy.config.FeignConfiguration;
import com.Czupson.medical_clinic_proxy.dto.PageDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.AppointmentDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.BookAppointmentCommand;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PatchMapping;


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

    @PatchMapping("/api/appointments/{id}/book")
    AppointmentDto bookAppointment(
            @PathVariable Long id,
            @RequestBody BookAppointmentCommand command
    );
}