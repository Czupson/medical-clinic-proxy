package com.Czupson.medical_clinic_proxy.service;

import com.Czupson.medical_clinic_proxy.client.MedicalClinicClient;
import com.Czupson.medical_clinic_proxy.dto.PageDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.AppointmentDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.BookAppointmentCommand;
import com.Czupson.medical_clinic_proxy.dto.doctor.DoctorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicalClinicProxyService {

    private final MedicalClinicClient medicalClinicClient;

    public PageDto<AppointmentDto> getPatientAppointments(Long patientId, int page, int size) {
        log.info("Getting appointments for patient: patientId={}, page={}, size={}", patientId, page, size);
        PageDto<AppointmentDto> result = medicalClinicClient.getPatientAppointments(patientId, page, size);
        log.info("Retrieved patient appointments: patientId={}, totalElements={}", patientId, result.totalElements());
        return result;
    }

    public AppointmentDto bookAppointment(Long id, BookAppointmentCommand command) {
        log.info("Booking appointment: appointmentId={}, patientId={}", id, command.patientId());
        AppointmentDto result = medicalClinicClient.bookAppointment(id, command);
        log.info("Appointment booked: appointmentId={}, patientId={}", result.id(), result.patientId());
        return result;
    }

    public PageDto<AppointmentDto> getAvailableAppointmentsForDoctor(Long doctorId, int page, int size) {
        log.info("Getting available appointments for doctor: doctorId={}, page={}, size={}", doctorId, page, size);
        PageDto<AppointmentDto> result = medicalClinicClient.getAvailableAppointmentsForDoctor(doctorId, page, size);
        log.info("Retrieved available appointments for doctor: doctorId={}, totalElements={}", doctorId, result.totalElements());
        return result;
    }

    public PageDto<AppointmentDto> getAvailableAppointmentsBySpecialization(
            String specialization, String start, String end, int page, int size) {
        log.info("Getting available appointments by specialization: specialization={}, start={}, end={}, page={}, size={}", specialization, start, end, page, size);
        PageDto<AppointmentDto> result = medicalClinicClient.getAvailableAppointmentsBySpecialization(
                specialization, start, end, page, size);
        log.info("Retrieved available appointments: specialization={}, totalElements={}", specialization, result.totalElements());
        return result;
    }

    public PageDto<DoctorDto> getDoctorsBySpecialization(String specialization, int page, int size) {
        log.info("Getting doctors by specialization: specialization={}, page={}, size={}", specialization, page, size);
        PageDto<DoctorDto> result = medicalClinicClient.getDoctorsBySpecialization(specialization, page, size);
        log.info("Retrieved doctors by specialization: specialization={}, totalElements={}", specialization, result.totalElements());
        return result;
    }

    public PageDto<AppointmentDto> getDoctorAppointments(Long doctorId, int page, int size) {
        log.info("Getting appointments for doctor: doctorId={}, page={}, size={}", doctorId, page, size);
        PageDto<AppointmentDto> result = medicalClinicClient.getDoctorAppointments(doctorId, page, size);
        log.info("Retrieved appointments for doctor: doctorId={}, totalElements={}", doctorId, result.totalElements());
        return result;
    }

    public void cancelAppointment(Long id) {
        log.info("Cancelling appointment: appointmentId={}", id);
        medicalClinicClient.cancelAppointment(id);
        log.info("Appointment cancelled: appointmentId={}", id);
    }

    public PageDto<AppointmentDto> getAppointmentsBySpecializationAndTimeRange(
            String specialization, String start, String end, int page, int size) {
        log.info("Getting appointments by specialization: specialization={}, start={}, end={}, page={}, size={}", specialization, start, end, page, size);
        PageDto<AppointmentDto> result = medicalClinicClient.getAppointmentsBySpecializationAndTimeRange(
                specialization, start, end, page, size);
        log.info("Retrieved appointments by specialization: specialization={}, totalElements={}", specialization, result.totalElements());
        return result;
    }
}