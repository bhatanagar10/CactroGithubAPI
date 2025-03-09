package org.server.githubapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class IssueRequestDTO {
    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Body cannot be blank")
    private String body;
}