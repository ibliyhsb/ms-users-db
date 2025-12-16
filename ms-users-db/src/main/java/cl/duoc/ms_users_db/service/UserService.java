package cl.duoc.ms_users_db.service;

import cl.duoc.ms_users_db.model.dto.UserDto;
import cl.duoc.ms_users_db.model.entities.User;
import cl.duoc.ms_users_db.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    private UserDto toDto(User entity) {
        return new UserDto(entity.getId(), entity.getNombreUsuario(), entity.getCorreoUsuario(), entity.getPasswordUsuario());
    }

    private User toEntity(UserDto dto) {
        return new User(dto.getId(), dto.getNombreUsuario(), dto.getCorreoUsuario(), dto.getPasswordUsuario());
    }

    public UserDto createUser(UserDto dto) {
        User entity = userRepository.save(toEntity(dto));
        return toDto(entity);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id).map(this::toDto);
    }

    public Optional<UserDto> updateUser(Long id, UserDto dto) {
        return userRepository.findById(id).map(existing -> {
            existing.setNombreUsuario(dto.getNombreUsuario());
            existing.setCorreoUsuario(dto.getCorreoUsuario());
            existing.setPasswordUsuario(dto.getPasswordUsuario());
            return toDto(userRepository.save(existing));
        });
    }

    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) return false;
        userRepository.deleteById(id);
        return true;
    }
}