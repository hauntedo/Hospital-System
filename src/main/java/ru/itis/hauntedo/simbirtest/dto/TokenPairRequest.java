package ru.itis.hauntedo.simbirtest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Tokens")
public class TokenPairRequest {

    @JsonProperty("access_token")
    @Schema(name = "access token", example = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJST0xFIjoiVVNFUiIsInN1YiI6InJ1c3RlbS5rYXJpbW92LjIwMDJAZ21haWwuY29tIiwiZXhwIjoxNjU3Nzk1MzU0LCJpYXQiOjE2NTc3OTE3NTR9.B2OBHzciO2RgxZASAkX59urtJ7vRuBNX_t4LrAkCHDiDlYvwgQvz1_YjksEXFYY7SRlVaY_0WyVrTUIMhEdSiQ")
    private String accessToken;

    @JsonProperty("refresh_token")
    @Schema(name = "refresh token", example = "f86246e1-5153-4823-a30f-b380d745cfd4")
    private String refreshToken;

}
