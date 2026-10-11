package com.Czupson.medical_clinic_proxy.client;

import com.Czupson.medical_clinic_proxy.config.FeignConfiguration;
import com.Czupson.medical_clinic_proxy.dto.PageDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.AppointmentDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.BookAppointmentCommand;
import com.Czupson.medical_clinic_proxy.dto.doctor.DoctorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(
        name = "medicalClinicClient",
        url = "${medical-clinic.url}",
        configuration = FeignConfiguration.class,
        fallbackFactory = MedicalClinicFallbackFactory.class
)
public interface MedicalClinicClient {

    @GetMapping("/api/appointments")
    PageDto<AppointmentDto> getPatientAppointments(
            @RequestParam("patientId") Long patientId,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    );

    @PatchMapping("/api/appointments/{id}/book")
    AppointmentDto bookAppointment(
            @PathVariable Long id,
            @RequestBody BookAppointmentCommand command
    );

    @GetMapping("/api/appointments/doctor/{doctorId}/available")
    PageDto<AppointmentDto> getAvailableAppointmentsForDoctor(
            @PathVariable Long doctorId,
            @RequestParam int page,
            @RequestParam int size
    );

    @GetMapping("/api/appointments")
    PageDto<AppointmentDto> getAvailableAppointmentsBySpecialization(
            @RequestParam("available") boolean available,
            @RequestParam(value = "specialization", required = false) String specialization,
            @RequestParam("start") String start,
            @RequestParam("end") String end,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    );

    @GetMapping("/api/doctors")
    PageDto<DoctorDto> getDoctorsBySpecialization(
            @RequestParam String specialization,
            @RequestParam int page,
            @RequestParam int size
    );

    @GetMapping("/api/appointments")
    PageDto<AppointmentDto> getDoctorAppointments(
            @RequestParam("doctorId") Long doctorId,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    );

    @DeleteMapping("/api/appointments/{id}/cancel")
    void cancelAppointment(@PathVariable Long id);

    @GetMapping("/api/appointments/specialization/{specialization}")
    PageDto<AppointmentDto> getAppointmentsBySpecializationAndTimeRange(
            @PathVariable String specialization,
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam int page,
            @RequestParam int size
    );
}