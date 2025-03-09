package org.server.githubapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IssueDTO {
    private int number;
    private String title;
    private String state;
    private String createdAt;
    private String updatedAt;
    private String htmlUrl;
}
