package cl.duoc.ms_users_db.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAlias;

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
    @JsonAlias({"username","nombreUsuario"})
    private String nombreUsuario;

    @JsonProperty("correoUsuario")
    @JsonAlias({"email","correoUsuario"})
    private String correoUsuario;

    @JsonProperty("passwordUsuario")
    @JsonAlias({"password","passwordUsuario"})
    private String passwordUsuario;
}
