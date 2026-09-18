package com.Czupson.medical_clinic_proxy.controller;

import com.Czupson.medical_clinic_proxy.dto.PageDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.AppointmentDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.BookAppointmentCommand;
import com.Czupson.medical_clinic_proxy.exception.MedicalClinicUnavailableException;
import com.Czupson.medical_clinic_proxy.service.MedicalClinicProxyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
}