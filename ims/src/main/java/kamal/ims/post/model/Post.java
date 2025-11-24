package kamal.ims.post.model;

import jakarta.persistence.*;
import kamal.ims.user.model.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "Post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String status;
    private String title;
    private String description;
    private String postResource;
    private LocalDateTime  createdDate;
    private LocalDateTime updatedDate;

//    private String category;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinTable(
            name = "post_category",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )

    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_post",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private User user;
}
