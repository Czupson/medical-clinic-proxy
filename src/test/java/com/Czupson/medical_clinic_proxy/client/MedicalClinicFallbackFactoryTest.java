package com.Czupson.medical_clinic_proxy.client;

import com.Czupson.medical_clinic_proxy.dto.PageDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.AppointmentDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.BookAppointmentCommand;
import com.Czupson.medical_clinic_proxy.dto.doctor.DoctorDto;
import com.Czupson.medical_clinic_proxy.exception.MedicalClinicUnavailableException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MedicalClinicFallbackFactoryTest {
    private final MedicalClinicFallbackFactory fallbackFactory = new MedicalClinicFallbackFactory();

    @Test
    void getPatientAppointments_WhenMedicalClinicUnavailable_ReturnsEmptyPage() {
        MedicalClinicFallbackFactory factory = new MedicalClinicFallbackFactory();
        MedicalClinicClient fallback = factory.create(new RuntimeException("Medical Clinic unavailable"));
        PageDto<AppointmentDto> result = fallback.getPatientAppointments(1L, 0, 10);
        assertTrue(result.content().isEmpty());
        assertEquals(0, result.totalElements());
        assertEquals(0, result.totalPages());
        assertEquals(0, result.pageNumber());
        assertEquals(10, result.pageSize());
    }

    @Test
    void getAvailableAppointmentsForDoctor_WhenMedicalClinicUnavailable_ReturnsEmptyPage() {
        MedicalClinicFallbackFactory factory = new MedicalClinicFallbackFactory();
        MedicalClinicClient fallback = factory.create(new RuntimeException("Medical Clinic unavailable"));
        PageDto<AppointmentDto> result = fallback.getAvailableAppointmentsForDoctor(1L, 0, 10);
        assertTrue(result.content().isEmpty());
        assertEquals(0, result.totalElements());
        assertEquals(0, result.totalPages());
        assertEquals(0, result.pageNumber());
        assertEquals(10, result.pageSize());
    }

    @Test
    void getAvailableAppointmentsBySpecialization_WhenMedicalClinicUnavailable_ReturnsEmptyPage() {
        MedicalClinicFallbackFactory factory = new MedicalClinicFallbackFactory();
        MedicalClinicClient fallback = factory.create(new RuntimeException("Medical Clinic unavailable"));
        PageDto<AppointmentDto> result = fallback.getAvailableAppointmentsBySpecialization("Kardiolog",
                "2026-10-01T00:00:00", "2026-10-02T00:00:00", 0, 10);
        assertTrue(result.content().isEmpty());
        assertEquals(0, result.totalElements());
        assertEquals(0, result.totalPages());
        assertEquals(0, result.pageNumber());
        assertEquals(10, result.pageSize());
    }

    @Test
    void bookAppointment_WhenMedicalClinicUnavailable_ThrowsException() {
        MedicalClinicFallbackFactory factory = new MedicalClinicFallbackFactory();
        MedicalClinicClient fallback = factory.create(new RuntimeException("Medical Clinic unavailable"));
        MedicalClinicUnavailableException exception =
                assertThrows(MedicalClinicUnavailableException.class,
                        () -> fallback.bookAppointment(1L, new BookAppointmentCommand(1L)));
        assertEquals("Medical clinic service is currently unavailable", exception.getMessage());
    }

    @Test
    void getDoctorsBySpecialization_ShouldReturnEmptyPage() {
        MedicalClinicClient fallback = fallbackFactory.create(new RuntimeException("Medical clinic unavailable"));
        PageDto<DoctorDto> result = fallback.getDoctorsBySpecialization("Kardiolog", 0, 10);
        assertTrue(result.content().isEmpty());
        assertEquals(0, result.totalElements());
        assertEquals(0, result.totalPages());
        assertEquals(0, result.pageNumber());
        assertEquals(10, result.pageSize());
    }

    @Test
    void getDoctorAppointments_ShouldReturnEmptyPage() {
        MedicalClinicClient fallback = fallbackFactory.create(new RuntimeException("Medical clinic unavailable"));
        PageDto<AppointmentDto> result = fallback.getDoctorAppointments(1L, 0, 10);
        assertTrue(result.content().isEmpty());
        assertEquals(0, result.totalElements());
        assertEquals(0, result.totalPages());
        assertEquals(0, result.pageNumber());
        assertEquals(10, result.pageSize());
    }

    @Test
    void getAppointmentsBySpecializationAndTimeRange_ShouldReturnEmptyPage() {
        MedicalClinicClient fallback = fallbackFactory.create(new RuntimeException("Medical clinic unavailable"));
        PageDto<AppointmentDto> result = fallback.getAppointmentsBySpecializationAndTimeRange(
                "Kardiolog", "2026-10-01T00:00:00", "2026-10-02T00:00:00", 0, 10);
        assertTrue(result.content().isEmpty());
        assertEquals(0, result.totalElements());
        assertEquals(0, result.totalPages());
        assertEquals(0, result.pageNumber());
        assertEquals(10, result.pageSize());
    }

    @Test
    void cancelAppointment_ShouldThrowMedicalClinicUnavailableException() {
        MedicalClinicClient fallback = fallbackFactory.create(new RuntimeException("Medical clinic unavailable"));
        assertThrows(MedicalClinicUnavailableException.class, () -> fallback.cancelAppointment(1L));
    }
}