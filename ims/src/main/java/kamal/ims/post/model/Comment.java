package kamal.ims.post.model;

import jakarta.persistence.*;
import kamal.ims.post.model.Post;
import kamal.ims.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "comment_post",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "comment_user",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private User user;

    // Parent comment for replies (nullable)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
            name = "comment_parent",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id")
    )
    private Comment parent;

    @Column(nullable = false, length = 2000)
    private String content;

    // "ACTIVE" | "DELETED" | "FLAGGED"
    @Column(nullable = false)
    private String status = "ACTIVE";

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime deletedDate;
}
