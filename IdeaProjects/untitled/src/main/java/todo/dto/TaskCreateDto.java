package todo.dto;

public record TaskCreateDto(
        String title,
        String description,
        Long executorId
) {
}