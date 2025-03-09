package org.server.githubapi.service;

import org.server.githubapi.dto.IssueRequestDTO;
import org.server.githubapi.dto.IssueResponseDTO;
import org.server.githubapi.dto.RepositoryDetailDTO;
import org.server.githubapi.dto.UserInfoDTO;
import org.server.githubapi.model.GitHubRepositoryModel;
import reactor.core.publisher.Flux;

public interface GitHubService {
    UserInfoDTO getUserProfile();
    RepositoryDetailDTO getRepositoryDetails(String repoName);
    IssueResponseDTO createIssue(String repoName, IssueRequestDTO issueRequest);
}
