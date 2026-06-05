package todo.service;

import org.junit.jupiter.api.Test;
import todo.TaskService;
import todo.entity.Task;
import todo.entity.TaskStatus;
import todo.repository.TaskRepository;
import todo.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Test
    void changeStatusToDone() {

        TaskRepository taskRepository =
                mock(TaskRepository.class);

        UserRepository userRepository =
                mock(UserRepository.class);

        Task task = new Task();
        task.setTitle("Task");

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        TaskService service =
                new TaskService(
                        taskRepository,
                        userRepository
                );

        service.changeStatus(1L);

        assertEquals(
                TaskStatus.DONE,
                task.getStatus()
        );
    }
    @Test
    void changeStatusToWaiting() {

        TaskRepository taskRepository =
                mock(TaskRepository.class);

        UserRepository userRepository =
                mock(UserRepository.class);

        Task task = new Task();

        task.setTitle("Task");
        task.setStatus(TaskStatus.DONE);

        when(taskRepository.findById(1L))
                .thenReturn(Optional.of(task));

        TaskService service =
                new TaskService(
                        taskRepository,
                        userRepository
                );

        service.changeStatus(1L);

        assertEquals(
                TaskStatus.WAITING,
                task.getStatus()
        );
    }
}