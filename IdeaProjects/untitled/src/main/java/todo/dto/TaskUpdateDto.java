package todo.dto;

public record TaskUpdateDto(
        String title,
        String description
) {
}