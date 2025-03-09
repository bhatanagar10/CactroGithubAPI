package org.server.githubapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.server.githubapi.dto.IssueRequestDTO;
import org.server.githubapi.dto.IssueResponseDTO;
import org.server.githubapi.dto.RepositoryDetailDTO;
import org.server.githubapi.dto.UserInfoDTO;
import org.server.githubapi.model.GitHubRepositoryModel;
import org.server.githubapi.model.GitHubUserModel;
import org.server.githubapi.service.GitHubService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/github")
@RequiredArgsConstructor
public class GitHubController {

    private final GitHubService gitHubService;

    @GetMapping
    public ResponseEntity<UserInfoDTO> getUserInfo() {
        try {
            return ResponseEntity.ok(gitHubService.getUserProfile());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching GitHub user info: " + e.getMessage(), e);
        }
    }

    @GetMapping("/{repoName}")
    public ResponseEntity<RepositoryDetailDTO> getRepositoryDetails(@PathVariable String repoName) {
        try {
            return ResponseEntity.ok(gitHubService.getRepositoryDetails(repoName));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Repository not found or error fetching details: " + e.getMessage(), e);
        }
    }


    @PostMapping("/{repoName}/issues")
    public ResponseEntity<IssueResponseDTO> createIssue(
            @PathVariable String repoName,
            @Valid @RequestBody IssueRequestDTO issueRequest) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(gitHubService.createIssue(repoName, issueRequest));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error creating issue: " + e.getMessage(), e);
        }
    }
}
