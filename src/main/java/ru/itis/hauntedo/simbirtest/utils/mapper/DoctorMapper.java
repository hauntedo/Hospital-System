package ru.itis.hauntedo.simbirtest.utils.mapper;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.itis.hauntedo.simbirtest.dto.request.CreateUserRequest;
import ru.itis.hauntedo.simbirtest.dto.request.DoctorRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateDoctorRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateUserRequest;
import ru.itis.hauntedo.simbirtest.dto.response.DoctorResponse;
import ru.itis.hauntedo.simbirtest.model.Doctor;
import ru.itis.hauntedo.simbirtest.model.User;
import ru.itis.hauntedo.simbirtest.service.UserService;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface DoctorMapper {

    DoctorResponse toDoctorResponse(Doctor doctor);

    Doctor toDoctor(DoctorRequest doctorRequest);

    void updateDoctor(UpdateDoctorRequest doctorRequest, @MappingTarget Doctor doctor);


    List<DoctorResponse> toList(List<Doctor> doctors);
}
