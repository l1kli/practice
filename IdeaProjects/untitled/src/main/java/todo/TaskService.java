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

    public TaskResponseDto create(TaskCreateDto dto) {

        Task task = new Task();

        task.setTitle(dto.title());
        task.setDescription(dto.description());

        if (dto.executorId() != null) {

            User executor =
                    userRepository.findById(dto.executorId())
                            .orElseThrow();

            task.setExecutor(executor);
        }

        Task saved = taskRepository.save(task);

        return map(saved);
    }

    public List<TaskResponseDto> getAll() {

        return taskRepository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    public TaskResponseDto getById(Long id) {

        return map(
                taskRepository.findById(id)
                        .orElseThrow()
        );
    }

    public TaskResponseDto update(
            Long id,
            TaskUpdateDto dto
    ) {

        Task task = taskRepository
                .findById(id)
                .orElseThrow();

        task.setTitle(dto.title());
        task.setDescription(dto.description());

        taskRepository.save(task);

        return map(task);
    }

    public TaskResponseDto changeStatus(Long id) {

        Task task = taskRepository
                .findById(id)
                .orElseThrow();

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

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskResponseDto map(Task task) {

        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus()
        );
    }
}