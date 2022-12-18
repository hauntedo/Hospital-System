package ru.itis.hauntedo.simbirtest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.hauntedo.simbirtest.dto.request.DoctorRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateDoctorRequest;
import ru.itis.hauntedo.simbirtest.dto.response.DoctorResponse;
import ru.itis.hauntedo.simbirtest.dto.response.PageResponse;
import ru.itis.hauntedo.simbirtest.exception.badrequest.RepeatablePasswordException;
import ru.itis.hauntedo.simbirtest.exception.notfound.DoctorNotFoundException;
import ru.itis.hauntedo.simbirtest.exception.badrequest.OccupiedDataException;
import ru.itis.hauntedo.simbirtest.model.Doctor;
import ru.itis.hauntedo.simbirtest.repository.DoctorRepository;
import ru.itis.hauntedo.simbirtest.repository.UserRepository;
import ru.itis.hauntedo.simbirtest.service.DoctorService;
import ru.itis.hauntedo.simbirtest.utils.enums.Role;
import ru.itis.hauntedo.simbirtest.utils.enums.State;
import ru.itis.hauntedo.simbirtest.utils.mapper.DoctorMapper;

import java.util.UUID;

import static ru.itis.hauntedo.simbirtest.specification.doctor.DoctorSpecification.byMedicalServiceCategory;
import static ru.itis.hauntedo.simbirtest.specification.doctor.DoctorSpecification.byMedicalServiceCode;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DoctorMapper doctorMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public DoctorResponse addDoctor(DoctorRequest doctorRequest) {
        log.info("Check email {}, phone {}", doctorRequest.getEmail(), doctorRequest.getPhone());
        if (userRepository.existsByEmailAndPhone(doctorRequest.getEmail(), doctorRequest.getPhone())) {
            log.error("Email or phone are occupied {} : {}", doctorRequest.getEmail(), doctorRequest.getPhone());
            throw new OccupiedDataException("Email or phone are occupied");
        }
        if (!doctorRequest.getPassword().equals(doctorRequest.getRepeatPassword())) {
            log.error("Passwords dont match");
            throw new RepeatablePasswordException();
        }
        Doctor newDoctor = doctorMapper.toDoctor(doctorRequest);
        newDoctor.setHashPassword(passwordEncoder.encode(doctorRequest.getPassword()));
        newDoctor.setRole(Role.DOCTOR);
        newDoctor.setState(State.CONFIRMED);
        log.info("Save doctor");
        return doctorMapper.toDoctorResponse(doctorRepository.save(newDoctor));
    }

    @Override
    public DoctorResponse updateDoctor(UUID doctorId, UpdateDoctorRequest doctorRequest) {
        log.info("Find doctor by id: {}", doctorId);
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(DoctorNotFoundException::new);
        doctorMapper.updateDoctor(doctorRequest, doctor);
        log.info("Update doctor");
        return doctorMapper.toDoctorResponse(doctorRepository.save(doctor));
    }

    @Override
    public void deleteDoctorById(UUID doctorId) {
        log.info("Delete doctor by id: {}", doctorId);
        doctorRepository.deleteById(doctorId);
    }

    @Override
    public PageResponse<DoctorResponse> getDoctors(int page, int size, String serviceCode, String categoryCode) {
        Specification<Doctor> spec;
        if (serviceCode != null) {
            spec = byMedicalServiceCode(serviceCode);
        } else if (categoryCode != null)  {
            spec = byMedicalServiceCategory(categoryCode);
        } else {
            spec = null;
        }
        log.info("Get list of doctors");
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Doctor> result = doctorRepository
                .findAll(spec ,pageRequest);
        return PageResponse.<DoctorResponse>builder()
                .content(doctorMapper.toList(result.getContent()))
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .build();
    }

    @Override
    public DoctorResponse getDoctorById(UUID doctorId) {

        log.info("Find doctor by id: {}", doctorId);
        return doctorMapper.toDoctorResponse(
                doctorRepository
                .findById(doctorId)
                .orElseThrow(DoctorNotFoundException::new));
    }
}
