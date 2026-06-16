package todo.controller;

import org.springframework.web.bind.annotation.*;
import todo.dto.TaskCreateDto;
import todo.dto.TaskResponseDto;
import todo.dto.TaskUpdateDto;
import todo.TaskService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskResponseDto create(@RequestBody TaskCreateDto dto, Principal principal) {
        return taskService.create(dto, principal.getName());
    }

    @GetMapping
    public List<TaskResponseDto> getAll(Principal principal) {
        return taskService.getAll(principal.getName());
    }

    @GetMapping("/{id}")
    public TaskResponseDto getById(@PathVariable Long id, Principal principal) {
        return taskService.getById(id, principal.getName());
    }

    @PutMapping("/{id}")
    public TaskResponseDto update(@PathVariable Long id, @RequestBody TaskUpdateDto dto, Principal principal) {
        return taskService.update(id, dto, principal.getName());
    }

    @PatchMapping("/{id}/status")
    public TaskResponseDto status(@PathVariable Long id, Principal principal) {
        return taskService.changeStatus(id, principal.getName());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Principal principal) {
        taskService.delete(id, principal.getName());
    }
}