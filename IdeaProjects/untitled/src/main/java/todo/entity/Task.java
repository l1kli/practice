package todo.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "tasks")
public class Task {

 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;

 @Column(nullable = false, length = 200)
 private String title;

 @Column(length = 5000)
 private String description;

 @Enumerated(EnumType.STRING)
 @Column(nullable = false)
 private TaskStatus status;

 @Column(nullable = false)
 private Timestamp createdAt;

 private Timestamp completedAt;

 @ManyToOne
 @JoinColumn(name = "owner_id")
 private User owner;

 @ManyToOne
 @JoinColumn(name = "executor_id")
 private User executor;

 @PrePersist
 public void init() {
  createdAt = new Timestamp(System.currentTimeMillis());
  status = TaskStatus.WAITING;
 }

 public Task() {
 }

 public Long getId() {
  return id;
 }

 public String getTitle() {
  return title;
 }

 public void setTitle(String title) {

  if (title == null || title.length() > 200) {
   throw new IllegalArgumentException("Title max 200 chars");
  }

  this.title = title;
 }

 public String getDescription() {
  return description;
 }

 public void setDescription(String description) {
  this.description = description;
 }

 public TaskStatus getStatus() {
  return status;
 }

 public void setStatus(TaskStatus status) {
  this.status = status;
 }

 public Timestamp getCreatedAt() {
  return createdAt;
 }

 public Timestamp getCompletedAt() {
  return completedAt;
 }

 public void setCompletedAt(Timestamp completedAt) {
  this.completedAt = completedAt;
 }

 public User getOwner() {
  return owner;
 }

 public void setOwner(User owner) {
  this.owner = owner;
 }

 public User getExecutor() {
  return executor;
 }

 public void setExecutor(User executor) {
  this.executor = executor;
 }
}