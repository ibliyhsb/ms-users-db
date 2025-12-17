package cl.duoc.ms_users_db.controller;

import cl.duoc.ms_users_db.model.dto.UserDto;
import cl.duoc.ms_users_db.model.entities.User;
import cl.duoc.ms_users_db.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/db/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository UserRepository;

    // CREATE
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User entity = toEntity(userDto);
        User saved = UserRepository.save(entity);
        return ResponseEntity.ok(toDto(saved));
    }

    // READ ALL
    @GetMapping
    public List<UserDto> getAllUsers() {
        return UserRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        Optional<User> found = UserRepository.findById(id);
        return found.map(entity -> ResponseEntity.ok(toDto(entity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        if (id == null || id <= 0 || userDto == null) return ResponseEntity.badRequest().build();
        log.debug("DB PUT /db/users/{} received DTO: {}", id, userDto);
        Optional<User> found = UserRepository.findById(id);
        if (found.isEmpty()) return ResponseEntity.notFound().build();

        User entity = found.get();
        entity.setUsername(userDto.getNombreUsuario());
        entity.setEmail(userDto.getCorreoUsuario());
        entity.setPassword(userDto.getPasswordUsuario());
        User saved = UserRepository.save(entity);
        log.debug("DB updated entity id={} -> username={}, email={}", saved.getId(), saved.getUsername(), saved.getEmail());
        return ResponseEntity.ok(toDto(saved));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        if (!UserRepository.existsById(id)) return ResponseEntity.notFound().build();
        UserRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        if (username == null || password == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<User> found = UserRepository.findByUsernameAndPassword(username, password);
        if (found.isPresent()) {
            return ResponseEntity.ok(toDto(found.get()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    private UserDto toDto(User entity) {
        return new UserDto(
            entity.getId(),
            entity.getUsername(), // nombreUsuario
            entity.getEmail(),    // correoUsuario
            entity.getPassword()  // passwordUsuario
        );
    }

    private User toEntity(UserDto dto) {
        return new User(
            dto.getId(),
            dto.getNombreUsuario(), // nombreUsuario
            dto.getCorreoUsuario(), // correoUsuario
            dto.getPasswordUsuario() // passwordUsuario
        );
    }
}