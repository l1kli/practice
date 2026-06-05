package todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import todo.entity.Task;
import todo.entity.User;

import java.util.List;

public interface TaskRepository
        extends JpaRepository<Task, Long> {

    List<Task> findByOwner(User owner);
}