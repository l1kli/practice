package todo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleRuntime(
            RuntimeException e
    ) {
        return Map.of(
                "error",
                e.getMessage()
        );
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(
            MethodArgumentNotValidException e
    ) {

        String message =
                e.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage();

        return Map.of(
                "error",
                message
        );
    }

}