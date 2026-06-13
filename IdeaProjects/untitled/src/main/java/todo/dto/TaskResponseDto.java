package todo.dto;

import todo.entity.TaskStatus;

public record TaskResponseDto(
        Long id,
        String title,
        String description,
        TaskStatus status,
        String ownerEmail,
        String executorEmail
) {
}