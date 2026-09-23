package com.Czupson.medical_clinic_proxy.service;

import com.Czupson.medical_clinic_proxy.client.MedicalClinicClient;
import com.Czupson.medical_clinic_proxy.dto.PageDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.AppointmentDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.BookAppointmentCommand;
import com.Czupson.medical_clinic_proxy.dto.doctor.DoctorDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalClinicProxyServiceTest {

    @Mock
    private MedicalClinicClient medicalClinicClient;

    @InjectMocks
    private MedicalClinicProxyService medicalClinicProxyService;

    @Test
    void getPatientAppointments_ReturnsAppointments() {
        // given
        Long patientId = 1L;
        int page = 0;
        int size = 10;
        PageDto<AppointmentDto> expected = new PageDto<>(List.of(), page, size, 0, 0);
        when(medicalClinicClient.getPatientAppointments(patientId, page, size)).thenReturn(expected);
        // when
        PageDto<AppointmentDto> result = medicalClinicProxyService.getPatientAppointments(
                       patientId, page, size);
        // then
        assertEquals(expected, result);
        verify(medicalClinicClient).getPatientAppointments(patientId, page, size);
    }

    @Test
    void bookAppointment_ReturnsAppointment() {
        // given
        Long appointmentId = 1L;
        BookAppointmentCommand command = new BookAppointmentCommand(1L);
        AppointmentDto expected = new AppointmentDto(1L, null, null, 1L, 1L);
        when(medicalClinicClient.bookAppointment(appointmentId, command)).thenReturn(expected);
        // when
        AppointmentDto result = medicalClinicProxyService.bookAppointment(appointmentId, command);
        // then
        assertEquals(expected, result);
        verify(medicalClinicClient).bookAppointment(appointmentId, command);
    }

    @Test
    void getAvailableAppointmentsForDoctor_ReturnsAppointments() {
        // given
        Long doctorId = 1L;
        int page = 0;
        int size = 10;
        PageDto<AppointmentDto> expected = new PageDto<>(List.of(), page, size, 0, 0);
        when(medicalClinicClient.getAvailableAppointmentsForDoctor(doctorId, page, size)).thenReturn(expected);
        // when
        PageDto<AppointmentDto> result = medicalClinicProxyService.getAvailableAppointmentsForDoctor(doctorId, page, size);
        // then
        assertEquals(expected, result);
        verify(medicalClinicClient).getAvailableAppointmentsForDoctor(doctorId, page, size);
    }

    @Test
    void getAvailableAppointmentsBySpecialization_ReturnsAppointments() {
        // given
        String specialization = "Kardiolog";
        String start = "2026-10-01T00:00:00";
        String end = "2026-10-02T00:00:00";
        int page = 0;
        int size = 10;
        PageDto<AppointmentDto> expected = new PageDto<>(List.of(), page, size, 0, 0);
        when(medicalClinicClient.getAvailableAppointmentsBySpecialization(specialization, start, end, page, size)).thenReturn(expected);
        // when
        PageDto<AppointmentDto> result = medicalClinicProxyService.getAvailableAppointmentsBySpecialization(
                        specialization, start, end, page, size);
        // then
        assertEquals(expected, result);
        verify(medicalClinicClient).getAvailableAppointmentsBySpecialization(specialization, start, end, page, size);
    }

    @Test
    void getDoctorsBySpecialization_ShouldReturnDoctors() {
        String specialization = "Kardiolog";
        int page = 0;
        int size = 10;
        DoctorDto doctor = new DoctorDto(1L, "Jan", "Nowak", specialization);
        PageDto<DoctorDto> expected = new PageDto<>(List.of(doctor), page, size, 1, 1);
        when(medicalClinicClient.getDoctorsBySpecialization(specialization, page, size)).thenReturn(expected);
        PageDto<DoctorDto> result = medicalClinicProxyService.getDoctorsBySpecialization(specialization, page, size);
        assertEquals(expected, result);
        verify(medicalClinicClient).getDoctorsBySpecialization(specialization, page, size);
    }

    @Test
    void getDoctorAppointments_ShouldReturnAppointments() {
        Long doctorId = 1L;
        int page = 0;
        int size = 10;
        AppointmentDto appointment = new AppointmentDto(1L,
                LocalDateTime.of(2026, 10, 1, 10, 0),
                LocalDateTime.of(2026, 10, 1, 10, 30), doctorId, 1L);
        PageDto<AppointmentDto> expected = new PageDto<>(List.of(appointment), page, size, 1, 1);
        when(medicalClinicClient.getDoctorAppointments(doctorId, page, size)).thenReturn(expected);
        PageDto<AppointmentDto> result = medicalClinicProxyService.getDoctorAppointments(doctorId, page, size);
        assertEquals(expected, result);
        verify(medicalClinicClient).getDoctorAppointments(doctorId, page, size);
    }

    @Test
    void cancelAppointment_ShouldCallClient() {
        Long appointmentId = 1L;
        medicalClinicProxyService.cancelAppointment(appointmentId);
        verify(medicalClinicClient).cancelAppointment(appointmentId);
    }

    @Test
    void getAppointmentsBySpecializationAndTimeRange_ShouldReturnAppointments() {
        String specialization = "Kardiolog";
        String start = "2026-10-01T00:00:00";
        String end = "2026-10-02T00:00:00";
        int page = 0;
        int size = 10;
        AppointmentDto appointment = new AppointmentDto(1L,
                LocalDateTime.of(2026, 10, 1, 10, 0),
                LocalDateTime.of(2026, 10, 1, 10, 30), 1L, 1L);
        PageDto<AppointmentDto> expected = new PageDto<>(List.of(appointment), page, size, 1, 1);
        when(medicalClinicClient.getAppointmentsBySpecializationAndTimeRange(
                specialization, start, end, page, size)).thenReturn(expected);
        PageDto<AppointmentDto> result = medicalClinicProxyService.getAppointmentsBySpecializationAndTimeRange(
                specialization, start, end, page, size);
        assertEquals(expected, result);
        verify(medicalClinicClient).getAppointmentsBySpecializationAndTimeRange(specialization, start, end, page, size);
    }

    @Test
    void getAvailableAppointmentsBySpecialization_WithoutSpecialization_ShouldReturnAppointments() {
        String start = "2026-10-01T00:00:00";
        String end = "2026-10-02T00:00:00";
        int page = 0;
        int size = 10;
        AppointmentDto appointment = new AppointmentDto(2L,
                LocalDateTime.of(2026, 10, 1, 11, 0),
                LocalDateTime.of(2026, 10, 1, 11, 30), 1L, null);
        PageDto<AppointmentDto> expected = new PageDto<>(List.of(appointment), page, size, 1, 1);
        when(medicalClinicClient.getAvailableAppointmentsBySpecialization(
                null, start, end, page, size)).thenReturn(expected);
        PageDto<AppointmentDto> result = medicalClinicProxyService.getAvailableAppointmentsBySpecialization(
                null, start, end, page, size);
        assertEquals(expected, result);
        verify(medicalClinicClient).getAvailableAppointmentsBySpecialization(null, start, end, page, size);
    }
}