package org.server.githubapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GitHubUserModel {
    private String login;
    private Long id;
    private String name;

    @JsonProperty("avatar_url")
    private String avatarUrl;

    @JsonProperty("html_url")
    private String htmlUrl;

    @JsonProperty("public_repos")
    private Integer publicRepos;

    @JsonProperty("public_gists")
    private Integer publicGists;

    private Integer followers;
    private Integer following;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;
}
