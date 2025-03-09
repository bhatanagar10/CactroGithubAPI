package org.server.githubapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssueResponseDTO {
    private int number;
    private String title;
    private String htmlUrl;
}
