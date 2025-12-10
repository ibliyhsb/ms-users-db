package cl.duoc.ms_users_db.model;

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
    
    private Long id;
    private String username;
    private String email;
    private String password;
    
}
