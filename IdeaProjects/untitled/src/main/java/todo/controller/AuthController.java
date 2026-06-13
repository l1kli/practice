package todo.controller;

import org.springframework.web.bind.annotation.*;
import todo.dto.JwtResponse;
import todo.dto.LoginRequest;
import todo.dto.RegisterRequest;
import todo.AuthService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthController {

 private final AuthService authService;

 public AuthController(
         AuthService authService
 ) {
  this.authService = authService;
 }

 @PostMapping("/register")
 public JwtResponse register(
         @Valid @RequestBody RegisterRequest request
 ) {
  return authService.register(request);
 }

 @PostMapping("/login")
 public JwtResponse login(
         @Valid @RequestBody LoginRequest request
 ) {
  return authService.login(request);
 }

 @PostMapping("/logout")
 public String logout() {
  return "Logout success";
 }
}