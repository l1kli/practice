package todo.controller;

import org.springframework.web.bind.annotation.*;
import todo.dto.TaskCreateDto;
import todo.dto.TaskResponseDto;
import todo.dto.TaskUpdateDto;
import todo.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

 private final TaskService taskService;

 public TaskController(
         TaskService taskService
 ) {
  this.taskService = taskService;
 }

 @PostMapping
 public TaskResponseDto create(
         @RequestBody TaskCreateDto dto
 ) {
  return taskService.create(dto);
 }

 @GetMapping
 public List<TaskResponseDto> getAll() {
  return taskService.getAll();
 }

 @GetMapping("/{id}")
 public TaskResponseDto getById(
         @PathVariable Long id
 ) {
  return taskService.getById(id);
 }

 @PutMapping("/{id}")
 public TaskResponseDto update(
         @PathVariable Long id,
         @RequestBody TaskUpdateDto dto
 ) {
  return taskService.update(id, dto);
 }

 @PatchMapping("/{id}/status")
 public TaskResponseDto status(
         @PathVariable Long id
 ) {
  return taskService.changeStatus(id);
 }

 @DeleteMapping("/{id}")
 public void delete(
         @PathVariable Long id
 ) {
  taskService.delete(id);
 }
}