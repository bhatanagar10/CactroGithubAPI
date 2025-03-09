package org.server.githubapi.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserInfoDTO {
    private String login;
    private String name;
    private String avatarUrl;
    private int followers;
    private int following;
    private int publicRepos;
    private List<RepositoryDTO> repositories;
}