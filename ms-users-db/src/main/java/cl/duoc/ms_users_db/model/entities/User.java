package cl.duoc.ms_users_db.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    
    @Column(name = "correo_usuario")
    private String correoUsuario;
    
    @Column(name = "password_usuario")
    private String passwordUsuario;
}
