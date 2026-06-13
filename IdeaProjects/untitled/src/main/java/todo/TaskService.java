package todo;

import org.springframework.stereotype.Service;
import todo.dto.TaskCreateDto;
import todo.dto.TaskResponseDto;
import todo.dto.TaskUpdateDto;
import todo.entity.Task;
import todo.entity.TaskStatus;
import todo.entity.User;
import todo.repository.TaskRepository;
import todo.repository.UserRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskResponseDto create(TaskCreateDto dto, String email) {

        Task task = new Task();

        User owner = userRepository
                .findByEmail(email)
                .orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );

        task.setOwner(owner);

        task.setTitle(dto.title());
        task.setDescription(dto.description());

        if (dto.executorId() != null) {

            User executor =
                    userRepository.findById(dto.executorId())
                            .orElseThrow(
                                    () -> new RuntimeException(
                                            "User not found"
                                    )
                            );

            task.setExecutor(executor);
        }

        Task saved = taskRepository.save(task);

        return map(saved);
    }

    public List<TaskResponseDto> getAll(
            String email
    ) {

        User owner =
                userRepository
                        .findByEmail(email)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "User not found"
                                )
                        );

        return taskRepository
                .findByOwner(owner)
                .stream()
                .map(this::map)
                .toList();
    }
    public TaskResponseDto getById(
            Long id,
            String email
    ) {

        return map(
                getTaskForUser(id, email)
        );
    }

    public TaskResponseDto update(
            Long id,
            TaskUpdateDto dto,
            String email
    ) {

        Task task =
                getTaskForUser(id, email);

        task.setTitle(dto.title());
        task.setDescription(dto.description());

        taskRepository.save(task);

        return map(task);
    }

    public TaskResponseDto changeStatus(
            Long id,
            String email
    ) {

        Task task =
                getTaskForUser(id, email);

        switch (task.getStatus()) {

            case WAITING -> {
                task.setStatus(TaskStatus.DONE);
                task.setCompletedAt(
                        new Timestamp(System.currentTimeMillis())
                );
            }

            case DONE -> {
                task.setStatus(TaskStatus.WAITING);
                task.setCompletedAt(null);
            }
        }

        taskRepository.save(task);

        return map(task);
    }

    public void delete(
            Long id,
            String email
    ) {

        Task task =
                getTaskForUser(id, email);

        taskRepository.delete(task);
    }

    private Task getTaskForUser(
            Long id,
            String email
    ) {

        Task task = taskRepository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Task not found"
                        )
                );

        if (task.getOwner() == null ||
                !task.getOwner()
                        .getEmail()
                        .equals(email)) {

            throw new RuntimeException(
                    "Access denied"
            );
        }

        return task;
    }

    private TaskResponseDto map(Task task) {

        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getOwner() != null
                        ? task.getOwner().getEmail()
                        : null,
                task.getExecutor() != null
                        ? task.getExecutor().getEmail()
                        : null
        );
    }
}