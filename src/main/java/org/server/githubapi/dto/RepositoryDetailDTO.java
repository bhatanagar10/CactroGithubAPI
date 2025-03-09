package org.server.githubapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RepositoryDetailDTO {
    private String name;
    private String fullName;
    private String description;
    private String language;
    private int stargazersCount;
    private int forksCount;
    private int openIssuesCount;
    private int watchersCount;
    private String createdAt;
    private String updatedAt;
    private String htmlUrl;
    private List<IssueDTO> recentIssues;
    private List<String> topics;
    private boolean hasWiki;
    private boolean archived;
}
