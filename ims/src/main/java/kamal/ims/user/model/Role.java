package kamal.ims.user.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Entity(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String roleName;

    private String description;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Role(String roleName) {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = LocalDateTime.now();
        this.description = "System generate role "+ roleName;
        this.roleName = roleName;
    }
}

