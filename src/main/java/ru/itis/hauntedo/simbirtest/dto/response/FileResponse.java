package ru.itis.hauntedo.simbirtest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(name = "file", description = "info about file")
public class FileResponse {

    @JsonProperty("id")
    @Schema(name = "file id")
    private UUID id;

    @JsonProperty("original_filename")
    @Schema(name = "file original name", example = "CV_John_Doe.pdf")
    private String originalFileName;

    @JsonProperty("content_type")
    @Schema(name = "file mime type", example = "image/png")
    private String contentType;

    @JsonProperty("file_size")
    @Schema(name = "file size", example = "1231")
    private long size;

    @JsonProperty("file_type")
    @Schema(name = "file type", example = "USER_PHOTO")
    private String fileType;
}
