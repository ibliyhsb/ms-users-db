package cl.duoc.ms_users_db.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString    

public class UserDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nombreUsuario")
    private String nombreUsuario;

    @JsonProperty("correoUsuario")
    private String correoUsuario;

    @JsonProperty("passwordUsuario")
    private String passwordUsuario;
}
