package kamal.ims.post.dto.comment;

import lombok.Data;
import lombok.NonNull;

@Data
public class CommentReplyRequest {

    @NonNull
    private Integer userId;

    @NonNull
    private String content;
}
