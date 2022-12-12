package ru.itis.hauntedo.simbirtest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(name = "page", description = "response using pagination")
public class PageResponse<T> {

    @JsonProperty("content")
    @Schema(name = "content", description = "list using pagination")
    private List<T> content;

    @JsonProperty("total_pages")
    @Schema(name = "total pages")
    private int totalPages;

    @JsonProperty("total_elements")
    @Schema(name = "total elements")
    private long totalElements;


}
