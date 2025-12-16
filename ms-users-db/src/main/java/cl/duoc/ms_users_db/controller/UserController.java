package cl.duoc.ms_users_db.controller;

import cl.duoc.ms_users_db.model.dto.UserDto;
import cl.duoc.ms_users_db.model.entities.User;
import cl.duoc.ms_users_db.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/db/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // CREATE
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        User entity = toEntity(userDto);
        User saved = userRepository.save(entity);
        return ResponseEntity.ok(toDto(saved));
    }

    // READ ALL
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<User> found = userRepository.findById(id);
        return found.map(entity -> ResponseEntity.ok(toDto(entity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        Optional<User> found = userRepository.findById(id);
        if (found.isEmpty()) return ResponseEntity.notFound().build();

        User entity = found.get();
        entity.setNombreUsuario(userDto.getNombreUsuario());
        entity.setCorreoUsuario(userDto.getCorreoUsuario());
        entity.setPasswordUsuario(userDto.getPasswordUsuario());
        User saved = userRepository.save(entity);
        return ResponseEntity.ok(toDto(saved));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) return ResponseEntity.notFound().build();
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private UserDto toDto(User entity) {
        return new UserDto(
            entity.getId(),
            entity.getNombreUsuario(), // nombreUsuario
            entity.getCorreoUsuario(),    // correoUsuario
            entity.getPasswordUsuario()  // passwordUsuario
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