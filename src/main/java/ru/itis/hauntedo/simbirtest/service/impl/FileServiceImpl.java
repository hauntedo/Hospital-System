package ru.itis.hauntedo.simbirtest.service.impl;

import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.hauntedo.simbirtest.dto.response.FileResponse;
import ru.itis.hauntedo.simbirtest.exception.badrequest.BadFileException;
import ru.itis.hauntedo.simbirtest.exception.internalserver.FileSaveFailureException;
import ru.itis.hauntedo.simbirtest.exception.notfound.FileNotFoundException;
import ru.itis.hauntedo.simbirtest.model.FileEntity;
import ru.itis.hauntedo.simbirtest.repository.FileRepository;
import ru.itis.hauntedo.simbirtest.service.FileService;
import ru.itis.hauntedo.simbirtest.utils.enums.FileType;
import ru.itis.hauntedo.simbirtest.utils.mapper.FileMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final GridFsTemplate gridFsTemplate;
    private final GridFsOperations gridFsOperations;
    private final FileMapper fileMapper;

    @Override
    public FileResponse saveFile(MultipartFile file, String fileType) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String objectId = null;

        try {
            objectId = saveBsonFileInStorage(file.getInputStream(), fileName);

        FileEntity fileEntity = FileEntity.builder()
                .contentType(file.getContentType())
                .originalFileName(fileName)
                .size(file.getSize())
                .objectId(objectId)
                .fileType(FileType.valueOf(fileType))
                .build();

        return fileMapper.toFileResponse(fileRepository.save(fileEntity));

        } catch (IllegalStateException | IllegalArgumentException e) {
            throw new BadFileException(e.getMessage());
        } catch (IOException e) {
            throw new FileSaveFailureException("Error saving file: " + fileName);
        }
    }

    @Override
    public FileEntity getFile(UUID fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(FileNotFoundException::new);
    }

    @Override
    public GridFsResource getFileFromStorage(String objectId) {
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(objectId)));
        if (gridFSFile == null) {
            throw new FileNotFoundException();
        }
        return gridFsOperations.getResource(gridFSFile);
    }

    private String saveBsonFileInStorage(InputStream inputStream, String fileName) {
        return gridFsTemplate.store(inputStream, fileName).toString();
    }

}
