package todo.dto;

public record RegisterRequest(
        String email,
        String password,
        String confirmPassword
) {
}