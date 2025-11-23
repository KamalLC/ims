package kamal.ims.user.model;

import jakarta.persistence.*;
import kamal.ims.post.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String username;
    private String password;

    @NotNull
    private String firstName;
    private String lastName;


    private String email;
    private boolean isAccountExpired;
    private boolean isLocked;
    private boolean isEnabled;
    private boolean isCredentialsExpired;

    //    @Pattern(regexp="^\\+?[0-9]{10,15}$", message="Invalid phone number")
    private String contact;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime lastLoginDate;


    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private List<Role> roles;

}
