package com.Czupson.medical_clinic_proxy.controller;

import com.Czupson.medical_clinic_proxy.dto.ErrorMessageDto;
import com.Czupson.medical_clinic_proxy.dto.PageDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.AppointmentDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.BookAppointmentCommand;
import com.Czupson.medical_clinic_proxy.dto.doctor.DoctorDto;
import com.Czupson.medical_clinic_proxy.service.MedicalClinicProxyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Appointments", description = "Endpoints for managing appointments")
@RestController
@Slf4j
@RequestMapping(value = "/api/proxy/appointments", produces = "application/json")
@RequiredArgsConstructor
public class MedicalClinicProxyController {
    private final MedicalClinicProxyService medicalClinicProxyService;

    @Operation(summary = "Get patient appointments",
            description = "Returns all appointments assigned to the given patient")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Appointments successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Patient not found"),
            @ApiResponse(responseCode = "503", description = "Medical Clinic service is unavailable",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))
    })
    @GetMapping("/patient/{patientId}")
    public PageDto<AppointmentDto> getPatientAppointments(
            @PathVariable Long patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET patient appointments: patientId={}, page={}, size={}", patientId, page, size);
        return medicalClinicProxyService.getPatientAppointments(patientId, page, size);
    }

    @Operation(summary = "Book an appointment", description = "Assigns a patient to an available appointment")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Appointment successfully booked"),
            @ApiResponse(responseCode = "404", description = "Appointment or patient not found"),
            @ApiResponse(responseCode = "409", description = "Appointment is already booked or conflicts with another appointment"),
            @ApiResponse(responseCode = "503", description = "Medical Clinic service is unavailable",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))
    })
    @PatchMapping("/{id}/book")
    public AppointmentDto bookAppointment(
            @PathVariable Long id,
            @RequestBody BookAppointmentCommand command) {
        log.info("PATCH book appointment: appointmentId={}, patientId={}", id, command.patientId());
        return medicalClinicProxyService.bookAppointment(id, command);
    }

    @Operation(summary = "Get available appointments for doctor", description = "Returns all available appointments for the given doctor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Available appointments successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Doctor not found"),
            @ApiResponse(responseCode = "503", description = "Medical Clinic service is unavailable",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))
    })
    @GetMapping("/doctor/{doctorId}/available")
    public PageDto<AppointmentDto> getAvailableAppointmentsForDoctor(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET available appointments for doctor: doctorId={}, page={}, size={}", doctorId, page, size);
        return medicalClinicProxyService.getAvailableAppointmentsForDoctor(doctorId, page, size);
    }

    @Operation(summary = "Get available appointments by specialization", description = "Returns available appointments for a given specialization and time interval")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Available appointments successfully retrieved"),
            @ApiResponse(responseCode = "503", description = "Medical Clinic service is unavailable",
                    content = @Content(schema = @Schema(implementation = ErrorMessageDto.class)))
    })
    @GetMapping("/available")
    public PageDto<AppointmentDto> getAvailableAppointmentsBySpecialization(
            @RequestParam(required = false) String specialization,
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("GET available appointments by specialization: specialization={}, start={}, end={}, page={}, size={}", specialization, start, end, page, size);
        return medicalClinicProxyService.getAvailableAppointmentsBySpecialization(
                specialization, start, end, page, size);
    }

    @GetMapping("/doctors/specialization/{specialization}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get doctors by specialization")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doctors retrieved successfully"),
            @ApiResponse(responseCode = "503", description = "Medical clinic service unavailable")
    })
    public PageDto<DoctorDto> getDoctorsBySpecialization(
            @PathVariable String specialization,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting doctors by specialization: specialization={}, page={}, size={}", specialization, page, size);
        return medicalClinicProxyService.getDoctorsBySpecialization(specialization, page, size);
    }

    @GetMapping("/doctor/{doctorId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all appointments for a doctor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Doctor not found"),
            @ApiResponse(responseCode = "503", description = "Medical clinic service unavailable")
    })
    public PageDto<AppointmentDto> getDoctorAppointments(
            @PathVariable Long doctorId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting appointments for doctor: doctorId={}, page={}, size={}", doctorId, page, size);
        return medicalClinicProxyService.getDoctorAppointments(doctorId, page, size);
    }

    @DeleteMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Cancel an appointment")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Appointment cancelled"),
            @ApiResponse(responseCode = "404", description = "Appointment not found"),
            @ApiResponse(responseCode = "409", description = "Appointment is not booked"),
            @ApiResponse(responseCode = "503", description = "Medical clinic service unavailable")
    })
    public void cancelAppointment(@PathVariable Long id) {
        log.info("Cancelling appointment: appointmentId={}", id);
        medicalClinicProxyService.cancelAppointment(id);
    }

    @GetMapping("/specialization/{specialization}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get appointments by specialization and time range")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully"),
            @ApiResponse(responseCode = "503", description = "Medical clinic service unavailable")
    })
    public PageDto<AppointmentDto> getAppointmentsBySpecializationAndTimeRange(
            @PathVariable String specialization,
            @RequestParam String start,
            @RequestParam String end,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Getting appointments by specialization: specialization={}, start={}, end={}, page={}, size={}", specialization, start, end, page, size);
        return medicalClinicProxyService.getAppointmentsBySpecializationAndTimeRange(
                specialization, start, end, page, size);
    }
}