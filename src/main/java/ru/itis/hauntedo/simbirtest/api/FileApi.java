package ru.itis.hauntedo.simbirtest.api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.hauntedo.simbirtest.dto.response.ExceptionResponse;
import ru.itis.hauntedo.simbirtest.dto.response.FileResponse;

import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequestMapping("/api/files")
public interface FileApi {

    @Operation(summary = "File upload(need authorization)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "File info",
                    content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema =
                                    @Schema(implementation = FileResponse.class)))}),
            @ApiResponse(responseCode = "400", description = "Bad file",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "500", description = "Saving file failure",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            )
                    })
    })
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @PostMapping(value = "/upload", produces = APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FileResponse> upload(@RequestPart("file") MultipartFile file,
                                        @RequestPart("fileType") String fileType);

    @Operation(summary = "File download(need authorization)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File"),
            @ApiResponse(responseCode = "404", description = "File not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            )
                    })
    })
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    @ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @GetMapping(value = "/download/{id}")
    ResponseEntity<GridFsResource> downloadFile(@PathVariable("id") UUID fileId);


}
