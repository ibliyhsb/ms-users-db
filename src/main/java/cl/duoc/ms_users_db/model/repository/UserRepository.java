package cl.duoc.ms_users_db.model.repository;

import cl.duoc.ms_users_db.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}