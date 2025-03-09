package org.server.githubapi.service;

import lombok.RequiredArgsConstructor;
import org.server.githubapi.dto.*;
import org.server.githubapi.model.GitHubIssueModel;
import org.server.githubapi.model.GitHubRepositoryModel;
import org.server.githubapi.model.GitHubUserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GitHubServiceImpl implements GitHubService {

    private final WebClient webClient;

    @Value("${github.username}")
    private String username;

    /**
     * Get user profile information including followers, following, and repositories
     */
    @Override
    public UserInfoDTO getUserProfile() {
        GitHubUserModel user =  webClient.get()
                .uri("/users/{username}", username)
                .retrieve()
                .bodyToMono(GitHubUserModel.class).block();
        if (user == null) {
            throw new RuntimeException("Failed to fetch GitHub user information");
        }
        List<RepositoryDTO> repositories = new ArrayList<>();
        List<GitHubRepositoryModel> repos = webClient.get()
                .uri("/users/{username}/repos", username)
                .retrieve()
                .bodyToFlux(GitHubRepositoryModel.class).collectList().block();

        if (repos != null) {
            repositories = repos.stream()
                    .map(this::mapToRepositoryDTO)
                    .collect(Collectors.toList());
        }
        return UserInfoDTO.builder()
                .login(user.getLogin())
                .name(user.getName())
                .avatarUrl(user.getAvatarUrl())
                .followers(user.getFollowers())
                .following(user.getFollowing())
                .publicRepos(user.getPublicRepos())
                .repositories(repositories)
                .build();
    }


    /**
     * Get details of a specific repository
     */
    @Override
    public RepositoryDetailDTO getRepositoryDetails(String repoName) {
        GitHubRepositoryModel repo = webClient.get()
                .uri("/repos/{owner}/{repo}", username, repoName)
                .retrieve()
                .bodyToMono(GitHubRepositoryModel.class).block();
        if (repo == null) {
            throw new RuntimeException("Repository not found or error fetching details");
        }
        List<GitHubIssueModel> issues = webClient.get()
                .uri("/repos/{owner}/{repo}/issues?state=all&per_page=5", username, repoName)
                .retrieve()
                .bodyToFlux(GitHubIssueModel.class).collectList().block();

        List<IssueDTO> recentIssues = new ArrayList<>();
        if (issues != null) {
            recentIssues = issues.stream()
                    .map(this::mapToIssueDTO)
                    .collect(Collectors.toList());
        }

        return RepositoryDetailDTO.builder()
                .name(repo.getName())
                .fullName(repo.getFullName())
                .description(repo.getDescription())
                .language(repo.getLanguage())
                .stargazersCount(repo.getStargazersCount())
                .forksCount(repo.getForksCount())
                .openIssuesCount(repo.getOpenIssuesCount())
                .watchersCount(repo.getWatchersCount())
                .createdAt(repo.getCreatedAt())
                .updatedAt(repo.getUpdatedAt())
                .htmlUrl(repo.getHtmlUrl())
                .recentIssues(recentIssues)
                .topics(repo.getTopics())
                .hasWiki(repo.getHasWiki())
                .archived(repo.getArchived())
                .build();
    }
    /**
     * Create a new issue in a repository
     */
    @Override
    public IssueResponseDTO createIssue(String repoName, IssueRequestDTO issueRequest) {
        return webClient.post()
                .uri("/repos/{owner}/{repo}/issues", username, repoName)
                .bodyValue(issueRequest)
                .retrieve()
                .bodyToMono(GitHubIssueModel.class)
                .map(this::mapToIssueResponseDTO).block();
    }

    /**
     * Map GitHub issue model to response DTO
     */
    private IssueResponseDTO mapToIssueResponseDTO(GitHubIssueModel issue) {
        return IssueResponseDTO.builder()
                .number(issue.getNumber())
                .title(issue.getTitle())
                .htmlUrl(issue.getHtmlUrl())
                .build();
    }
    private RepositoryDTO mapToRepositoryDTO(GitHubRepositoryModel repository) {
        return RepositoryDTO.builder()
                .name(repository.getName())
                .description(repository.getDescription())
                .language(repository.getLanguage())
                .stargazersCount(repository.getStargazersCount())
                .forksCount(repository.getForksCount())
                .openIssuesCount(repository.getOpenIssuesCount())
                .createdAt(repository.getCreatedAt())
                .updatedAt(repository.getUpdatedAt())
                .htmlUrl(repository.getHtmlUrl())
                .build();
    }

    private IssueDTO mapToIssueDTO(GitHubIssueModel issue) {
        return IssueDTO.builder()
                .number(issue.getNumber())
                .title(issue.getTitle())
                .state(issue.getState())
                .createdAt(issue.getCreatedAt())
                .updatedAt(issue.getUpdatedAt())
                .htmlUrl(issue.getHtmlUrl())
                .build();
    }
}
