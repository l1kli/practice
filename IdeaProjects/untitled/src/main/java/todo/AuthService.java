package todo;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import todo.dto.JwtResponse;
import todo.dto.LoginRequest;
import todo.dto.RegisterRequest;
import todo.entity.User;
import todo.repository.UserRepository;

@Service
public class AuthService {

 private final UserRepository userRepository;
 private final PasswordEncoder passwordEncoder;
 private final JwtService jwtService;

 public AuthService(
         UserRepository userRepository,
         PasswordEncoder passwordEncoder,
         JwtService jwtService
 ) {
  this.userRepository = userRepository;
  this.passwordEncoder = passwordEncoder;
  this.jwtService = jwtService;
 }

 public JwtResponse register(
         RegisterRequest request
 ) {

  if (userRepository.existsByEmail(request.email())) {
   throw new RuntimeException("Email already exists");
  }

  if (!request.password()
          .equals(request.confirmPassword())) {
   throw new RuntimeException("Passwords do not match");
  }

  if (request.password().length() < 4) {
   throw new RuntimeException("Password too short");
  }

  User user = new User();

  user.setEmail(request.email());
  user.setPassword(
          passwordEncoder.encode(request.password())
  );

  userRepository.save(user);

  String token =
          jwtService.generateToken(user.getEmail());

  return new JwtResponse(token);
 }

 public JwtResponse login(
         LoginRequest request
 ) {

  User user = userRepository
          .findByEmail(request.email())
          .orElseThrow();

  if (!passwordEncoder.matches(
          request.password(),
          user.getPassword()
  )) {

   throw new RuntimeException(
           "Wrong password"
   );
  }

  String token =
          jwtService.generateToken(user.getEmail());

  return new JwtResponse(token);
 }
}