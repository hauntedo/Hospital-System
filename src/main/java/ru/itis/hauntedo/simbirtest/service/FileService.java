package ru.itis.hauntedo.simbirtest.service;

import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.hauntedo.simbirtest.dto.response.FileResponse;
import ru.itis.hauntedo.simbirtest.model.FileEntity;

import java.util.UUID;

public interface FileService {

    FileResponse saveFile(MultipartFile file, String fileType);

    FileEntity getFile(UUID id);

    GridFsResource getFileFromStorage(String objectId);
}
