package ru.itis.hauntedo.simbirtest.utils.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.itis.hauntedo.simbirtest.dto.request.AppointmentDetailRequest;
import ru.itis.hauntedo.simbirtest.dto.response.AppointmentDetailResponse;
import ru.itis.hauntedo.simbirtest.model.AppointmentDetail;

@Mapper(componentModel = "spring")
public interface AppointmentDetailMapper {

    AppointmentDetailResponse toResponse(AppointmentDetail appointmentDetail);

    AppointmentDetail toEntity(AppointmentDetailRequest appointmentDetailRequest);

    void update(@MappingTarget AppointmentDetail appointmentDetail, AppointmentDetailRequest appointmentDetailRequest);
}
