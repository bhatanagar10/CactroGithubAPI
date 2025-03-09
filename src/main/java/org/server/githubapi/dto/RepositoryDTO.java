package org.server.githubapi.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RepositoryDTO {
    private String name;
    private String description;
    private String language;
    private int stargazersCount;
    private int forksCount;
    private int openIssuesCount;
    private String createdAt;
    private String updatedAt;
    private String htmlUrl;
}
