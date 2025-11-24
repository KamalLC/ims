package kamal.ims.post.dto.comment;

import lombok.Data;
import lombok.NonNull;

@Data
public class CommentCreateRequest {
    @NonNull
    private Integer postId;

    @NonNull
    private Integer userId;

    @NonNull
    private String content;
}
