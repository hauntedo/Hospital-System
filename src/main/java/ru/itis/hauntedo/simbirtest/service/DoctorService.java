package ru.itis.hauntedo.simbirtest.service;

import ru.itis.hauntedo.simbirtest.dto.request.DoctorRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateDoctorRequest;
import ru.itis.hauntedo.simbirtest.dto.response.DoctorResponse;
import ru.itis.hauntedo.simbirtest.dto.response.PageResponse;

import java.util.UUID;

public interface DoctorService {
    DoctorResponse addDoctor(DoctorRequest doctorRequest);

    DoctorResponse updateDoctor(UUID doctorId, UpdateDoctorRequest doctorRequest);

    void deleteDoctorById(UUID doctorId);

    PageResponse<DoctorResponse> getDoctors(int page, int size);

    DoctorResponse getDoctorById(UUID doctorId);
}
