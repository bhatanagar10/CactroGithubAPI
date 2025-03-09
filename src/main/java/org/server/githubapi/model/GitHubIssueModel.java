package org.server.githubapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitHubIssueModel {
    private Long id;
    private Integer number;
    private String title;
    private String body;
    private String state;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("closed_at")
    private String closedAt;

    private GitHubUserModel user;
}
