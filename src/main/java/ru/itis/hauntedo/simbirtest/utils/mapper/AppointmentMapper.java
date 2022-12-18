package ru.itis.hauntedo.simbirtest.utils.mapper;

import org.mapstruct.*;
import ru.itis.hauntedo.simbirtest.dto.request.AppointmentRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateAppointmentRequest;
import ru.itis.hauntedo.simbirtest.dto.response.AppointmentResponse;
import ru.itis.hauntedo.simbirtest.model.Appointment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    AppointmentResponse toResponse(Appointment appointment);

    @Mapping(target = "start", ignore = true)
    Appointment toEntity(AppointmentRequest appointmentRequest);
    List<AppointmentResponse> toList(List<Appointment> appointments);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "start", ignore = true)
    void updateEntity(@MappingTarget Appointment appointment, UpdateAppointmentRequest appointmentRequest);
}
