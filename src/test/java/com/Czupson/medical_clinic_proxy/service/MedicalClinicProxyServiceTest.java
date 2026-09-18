package com.Czupson.medical_clinic_proxy.service;

import com.Czupson.medical_clinic_proxy.client.MedicalClinicClient;
import com.Czupson.medical_clinic_proxy.dto.PageDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.AppointmentDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.BookAppointmentCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}