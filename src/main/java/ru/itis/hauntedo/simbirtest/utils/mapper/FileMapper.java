package ru.itis.hauntedo.simbirtest.utils.mapper;

import org.mapstruct.Mapper;
import ru.itis.hauntedo.simbirtest.dto.response.FileResponse;
import ru.itis.hauntedo.simbirtest.model.FileEntity;
import ru.itis.hauntedo.simbirtest.service.FileService;

@Mapper(componentModel = "spring", uses = FileService.class)
public interface FileMapper {

    FileResponse toFileResponse(FileEntity file);


}