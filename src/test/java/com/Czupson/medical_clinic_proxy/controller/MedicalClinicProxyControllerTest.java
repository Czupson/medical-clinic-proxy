package com.Czupson.medical_clinic_proxy.controller;

import com.Czupson.medical_clinic_proxy.dto.PageDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.AppointmentDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.BookAppointmentCommand;
import com.Czupson.medical_clinic_proxy.dto.doctor.DoctorDto;
import com.Czupson.medical_clinic_proxy.exception.MedicalClinicUnavailableException;
import com.Czupson.medical_clinic_proxy.service.MedicalClinicProxyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MedicalClinicProxyController.class)
class MedicalClinicProxyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MedicalClinicProxyService medicalClinicProxyService;

    @Test
    void getPatientAppointments_AppointmentsExist_AppointmentsReturned()
            throws Exception {
        // given
        Long patientId = 1L;
        int page = 0;
        int size = 10;
        PageDto<AppointmentDto> pageDto = new PageDto<>(List.of(), page, size, 0, 0);
        when(medicalClinicProxyService.getPatientAppointments(patientId, page, size)).thenReturn(pageDto);
        // when and then
        mockMvc.perform(get("/api/proxy/appointments/patient/{patientId}", patientId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.totalPages").value(0));
        verify(medicalClinicProxyService).getPatientAppointments(eq(patientId), eq(page), eq(size));
    }

    @Test
    void bookAppointment_MedicalClinicUnavailable_Returns503() throws Exception {
        // given
        Long appointmentId = 3L;
        doThrow(new MedicalClinicUnavailableException())
                .when(medicalClinicProxyService).bookAppointment(eq(appointmentId), any(BookAppointmentCommand.class));
        // when and then
        mockMvc.perform(patch("/api/proxy/appointments/{id}/book", appointmentId)
                        .contentType("application/json")
                        .content("{\"patientId\":1}"))
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    void getAvailableAppointmentsForDoctor_AppointmentsExist_AppointmentsReturned() throws Exception {
        // given
        Long doctorId = 1L;
        int page = 0;
        int size = 10;
        PageDto<AppointmentDto> pageDto = new PageDto<>(List.of(), page, size, 0, 0);
        when(medicalClinicProxyService.getAvailableAppointmentsForDoctor(doctorId, page, size)).thenReturn(pageDto);
        // when and then
        mockMvc.perform(get("/api/proxy/appointments/doctor/{doctorId}/available", doctorId)
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.totalPages").value(0));
        verify(medicalClinicProxyService).getAvailableAppointmentsForDoctor(eq(doctorId), eq(page), eq(size));
    }

    @Test
    void getAvailableAppointmentsBySpecialization_AppointmentsExist_AppointmentsReturned() throws Exception {
        // given
        String specialization = "Kardiolog";
        String start = "2026-10-01T00:00:00";
        String end = "2026-10-02T00:00:00";
        int page = 0;
        int size = 10;
        PageDto<AppointmentDto> pageDto = new PageDto<>(List.of(), page, size, 0, 0);
        when(medicalClinicProxyService.getAvailableAppointmentsBySpecialization(specialization, start, end, page, size)).thenReturn(pageDto);
        // when and then
        mockMvc.perform(get("/api/proxy/appointments/available")
                                .param("specialization", specialization)
                                .param("start", start)
                                .param("end", end)
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.pageNumber").value(0))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.totalPages").value(0));
        verify(medicalClinicProxyService).getAvailableAppointmentsBySpecialization(eq(specialization), eq(start), eq(end), eq(page), eq(size));
    }

    @Test
    void getDoctorsBySpecialization_ShouldReturnDoctors() throws Exception {
        String specialization = "Kardiolog";
        DoctorDto doctor = new DoctorDto(1L, "Jan", "Nowak", specialization);
        PageDto<DoctorDto> response = new PageDto<>(List.of(doctor), 0, 10, 1, 1);
        when(medicalClinicProxyService.getDoctorsBySpecialization(specialization, 0, 10)).thenReturn(response);
        mockMvc.perform(get("/api/proxy/appointments/doctors/specialization/{specialization}", specialization))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].firstName").value("Jan"))
                .andExpect(jsonPath("$.content[0].lastName").value("Nowak"))
                .andExpect(jsonPath("$.content[0].specialization").value("Kardiolog"));
        verify(medicalClinicProxyService).getDoctorsBySpecialization(specialization, 0, 10);
    }

    @Test
    void getDoctorAppointments_ShouldReturnAppointments() throws Exception {
        Long doctorId = 1L;
        AppointmentDto appointment = new AppointmentDto(1L,
                LocalDateTime.of(2026, 10, 1, 10, 0),
                LocalDateTime.of(2026, 10, 1, 10, 30), doctorId, 1L);
        PageDto<AppointmentDto> response = new PageDto<>(List.of(appointment), 0, 10, 1, 1);
        when(medicalClinicProxyService.getDoctorAppointments(doctorId, 0, 10)).thenReturn(response);
        mockMvc.perform(get("/api/proxy/appointments/doctor/{doctorId}", doctorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].doctorId").value(1))
                .andExpect(jsonPath("$.content[0].patientId").value(1));
        verify(medicalClinicProxyService).getDoctorAppointments(doctorId, 0, 10);
    }

    @Test
    void cancelAppointment_ShouldReturnNoContent() throws Exception {
        Long appointmentId = 1L;
        mockMvc.perform(delete("/api/proxy/appointments/{id}/cancel", appointmentId))
                .andExpect(status().isNoContent());
        verify(medicalClinicProxyService).cancelAppointment(appointmentId);
    }

    @Test
    void getAppointmentsBySpecializationAndTimeRange_ShouldReturnAppointments() throws Exception {
        String specialization = "Kardiolog";
        String start = "2026-10-01T00:00:00";
        String end = "2026-10-02T00:00:00";
        AppointmentDto appointment = new AppointmentDto(1L,
                LocalDateTime.of(2026, 10, 1, 10, 0),
                LocalDateTime.of(2026, 10, 1, 10, 30), 1L, 1L);
        PageDto<AppointmentDto> response = new PageDto<>(List.of(appointment), 0, 10, 1, 1);
        when(medicalClinicProxyService.getAppointmentsBySpecializationAndTimeRange(specialization, start, end, 0, 10)).thenReturn(response);
        mockMvc.perform(get("/api/proxy/appointments/specialization/{specialization}", specialization)
                                .param("start", start)
                                .param("end", end))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].doctorId").value(1))
                .andExpect(jsonPath("$.content[0].patientId").value(1));
        verify(medicalClinicProxyService).getAppointmentsBySpecializationAndTimeRange(
                specialization, start, end, 0, 10);
    }

    @Test
    void getAvailableAppointments_WithoutSpecialization_ShouldReturnAppointments() throws Exception {
        String start = "2026-10-01T00:00:00";
        String end = "2026-10-02T00:00:00";
        AppointmentDto appointment = new AppointmentDto(2L,
                LocalDateTime.of(2026, 10, 1, 11, 0),
                LocalDateTime.of(2026, 10, 1, 11, 30), 1L, null);
        PageDto<AppointmentDto> response = new PageDto<>(List.of(appointment), 0, 10, 1, 1);
        when(medicalClinicProxyService.getAvailableAppointmentsBySpecialization(null, start, end, 0, 10)).thenReturn(response);
        mockMvc.perform(get("/api/proxy/appointments/available")
                                .param("start", start)
                                .param("end", end))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(2))
                .andExpect(jsonPath("$.content[0].doctorId").value(1))
                .andExpect(jsonPath("$.content[0].patientId").doesNotExist());
        verify(medicalClinicProxyService).getAvailableAppointmentsBySpecialization(null, start, end, 0, 10);
    }
}