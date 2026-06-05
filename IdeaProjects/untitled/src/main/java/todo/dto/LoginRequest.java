package todo.dto;

public record LoginRequest(
        String email,
        String password
) {
}