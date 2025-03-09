package org.server.githubapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class GitHubRepositoryModel {
    private Long id;
    private String name;

    @JsonProperty("full_name")
    private String fullName;

    private GitHubUserModel owner;
    private String description;

    @JsonProperty("html_url")
    private String htmlUrl;

    private String homepage;
    private String language;

    @JsonProperty("stargazers_count")
    private Integer stargazersCount;

    @JsonProperty("watchers_count")
    private Integer watchersCount;

    @JsonProperty("forks_count")
    private Integer forksCount;

    @JsonProperty("open_issues_count")
    private Integer openIssuesCount;

    private List<String> topics;

    @JsonProperty("has_wiki")
    private Boolean hasWiki;

    private Boolean archived;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("pushed_at")
    private String pushedAt;
}
