package inq.upfit.controller;

import inq.upfit.dto.AssignmentCreateRequest;
import inq.upfit.dto.AssignmentResponse;
import inq.upfit.service.AssignmentService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "assignments", description = "과제 API")
@RequiredArgsConstructor
@RestController
public class AssignmentController {
    private final AssignmentService assignmentService;

    @ApiResponse(responseCode = "201", description = "assignment created",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AssignmentResponse.class)
            )
    )
    @PostMapping("/api/admin/assignments")
    public ResponseEntity<AssignmentResponse> createAssignment(@RequestBody @Valid AssignmentCreateRequest createRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assignmentService.createAssignment(createRequest));
    }
}
