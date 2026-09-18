package com.Czupson.medical_clinic_proxy.client;

import com.Czupson.medical_clinic_proxy.dto.PageDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.AppointmentDto;
import com.Czupson.medical_clinic_proxy.dto.appointment.BookAppointmentCommand;
import com.Czupson.medical_clinic_proxy.exception.MedicalClinicUnavailableException;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MedicalClinicFallbackFactory
        implements FallbackFactory<MedicalClinicClient> {

    @Override
    public MedicalClinicClient create(Throwable cause) {
        return new MedicalClinicClient() {

            @Override
            public PageDto<AppointmentDto> getPatientAppointments(Long patientId, int page, int size) {
                return new PageDto<>(List.of(), page, size, 0, 0);
            }

            @Override
            public AppointmentDto bookAppointment(
                    Long id,
                    BookAppointmentCommand command) {
                throw new MedicalClinicUnavailableException();
            }

            @Override
            public PageDto<AppointmentDto> getAvailableAppointmentsForDoctor(Long doctorId, int page, int size) {
                return new PageDto<>(List.of(), page, size, 0, 0);
            }

            @Override
            public PageDto<AppointmentDto> getAvailableAppointmentsBySpecialization(String specialization, String start, String end, int page, int size) {
                return new PageDto<>(List.of(), page, size, 0, 0);
            }
        };
    }
}