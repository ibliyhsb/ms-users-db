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
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<User> found = UserRepository.findById(id);
        return found.map(entity -> ResponseEntity.ok(toDto(entity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        Optional<User> found = UserRepository.findById(id);
        if (found.isEmpty()) return ResponseEntity.notFound().build();

        User entity = found.get();
        entity.setUsername(userDto.getUsername());
        entity.setEmail(userDto.getEmail());
        entity.setPassword(userDto.getPassword());
        User saved = UserRepository.save(entity);
        return ResponseEntity.ok(toDto(saved));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (!UserRepository.existsById(id)) return ResponseEntity.notFound().build();
        UserRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private UserDto toDto(User entity) {
        return new UserDto(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getPassword());
    }


    private User toEntity(UserDto dto) {
        return new User(dto.getId(), dto.getUsername(), dto.getEmail(), dto.getPassword());
    }
}