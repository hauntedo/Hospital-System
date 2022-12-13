package ru.itis.hauntedo.simbirtest.api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.hauntedo.simbirtest.dto.request.CreateUserRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdatePasswordRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UpdateUserRequest;
import ru.itis.hauntedo.simbirtest.dto.request.UserPasswordRequest;
import ru.itis.hauntedo.simbirtest.dto.response.ExceptionResponse;
import ru.itis.hauntedo.simbirtest.dto.response.SuccessResponse;
import ru.itis.hauntedo.simbirtest.dto.response.UserResponse;
import ru.itis.hauntedo.simbirtest.validation.ValidationError;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequestMapping("/api/users")
public interface UserApi {

    @Operation(summary = "Get user photo(need authorization)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User photo"),
            @ApiResponse(responseCode = "404", description = "User photo not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            )
    })
    @GetMapping(value = "/{id}/photo")
    ResponseEntity<GridFsResource> getPhotoByUserId(@PathVariable("id") UUID userId);

    @Operation(summary = "Get user by id(need authorization)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User info",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = UserResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            )
                    })
    })
    //@ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UserResponse> getUserById(@PathVariable("id") UUID userId);


    @Operation(summary = "Save user photo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User photo ID",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = UUID.class)
                            )
                    }),
            @ApiResponse(responseCode = "400", description = "File size exceeded or file type was incorrect",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            )
                    })
    })
    //@ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @PostMapping(value = "/{id}/photo")
    ResponseEntity<UUID> saveUserPhoto(@PathVariable("id") UUID userId, @RequestParam("file") UUID fileID);

    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = UserResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Validation error",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ValidationError.class)
                            )
                    }
            )
    })
    //@ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UserResponse> updateUser(@PathVariable("id") UUID userId,
                                            @RequestBody @Valid UpdateUserRequest userRequest);

    @Operation(summary = "Update photo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User photo updated",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = UUID.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class))
                    }),
            @ApiResponse(responseCode = "400", description = "File size exceeded or file type was incorrect",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            )
                    })
    })
    //@ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @PutMapping(value = "/{id}/photo")
    ResponseEntity<UUID> updateUserPhoto(@PathVariable("id") UUID userId, @RequestParam("file") UUID fileId);

//    @Operation(summary = "User login")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "User authorized",
//                    content = {
//                            @Content(mediaType = "application/json",
//                                    schema =
//                                    @Schema(implementation = TokenPairResponse.class)
//                            )
//                    }),
//            @ApiResponse(responseCode = "400", description = "Incorrect data",
//                    content = {
//                            @Content(mediaType = "application/json",
//                                    schema =
//                                    @Schema(implementation = ExceptionResponse.class)
//                            )
//                    })
//    })
//    @PostMapping(value = "/login", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
//    ResponseEntity<TokenPairResponse> login(@RequestBody @Valid UserRequest userRequest);

    @Operation(summary = "User registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User id",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = UUID.class)
                            )
                    }),
            @ApiResponse(responseCode = "400", description = "Email is occupied or validation error",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            ),
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ValidationError.class)
                            )
                    }
            )
    })
    @PostMapping(value = "/signup", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UUID> registration(@RequestBody @Valid CreateUserRequest createUserRequest);

    @Operation(summary = "Update user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password updated",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = SuccessResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            )
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Old password doesnt match or new and repeat passwords" +
                    "dont match or validation error",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            ),
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ValidationError.class)
                            )
                    }
            )
    })
    //@ApiImplicitParam(name = "Authorization", paramType = "header", required = true)
    @PutMapping(value = "/{id}/password", consumes = APPLICATION_JSON_VALUE)
    ResponseEntity<SuccessResponse> updateUserPassword(@PathVariable("id") UUID userId, @RequestBody @Valid UpdatePasswordRequest passwordRequest);

    @Operation(summary = "User confirmation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User confirmed",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = UUID.class)
                            )
                    }),
            @ApiResponse(responseCode = "404", description = "Confirm code not found",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            )
                    }),
            @ApiResponse(responseCode = "400", description = "Confirmation failure",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = ExceptionResponse.class)
                            )
                    })
    })
    @GetMapping(value = "/confirm/{confirm_code}", produces = APPLICATION_JSON_VALUE)
    ResponseEntity<UUID> confirm(@PathVariable("confirm_code") UUID confirmCode);

//    @Operation(summary = "User password")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "User password saved",
//                    content = {
//                            @Content(mediaType = "application/json",
//                                    schema =
//                                    @Schema(implementation = TokenPairResponse.class)
//                            )
//                    }),
//            @ApiResponse(responseCode = "404", description = "User not found",
//                    content = {
//                            @Content(mediaType = "application/json",
//                                    schema =
//                                    @Schema(implementation = ExceptionResponse.class)
//                            )
//                    }),
//            @ApiResponse(responseCode = "400", description = "New and repeat passwords" +
//                    "dont match or validation error",
//                    content = {
//                            @Content(mediaType = "application/json",
//                                    schema =
//                                    @Schema(implementation = ExceptionResponse.class)
//                            ),
//                            @Content(mediaType = "application/json",
//                                    schema =
//                                    @Schema(implementation = ValidationError.class)
//                            )
//                    }
//            )
//    })
//    @PostMapping(value = "/password/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
//    ResponseEntity<TokenPairResponse> createPassword(@PathVariable("id") UUID userId, @RequestBody @Valid UserPasswordRequest passwordRequest);
//


}
