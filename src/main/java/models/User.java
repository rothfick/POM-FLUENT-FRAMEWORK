package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username;
    private String password;

    public static User getTestUser() {
        return User.builder()
                .username("Admin")
                .password("admin123")
                .build();
    }
}
