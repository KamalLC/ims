package kamal.ims.post.dto.comment;

import lombok.Data;
import lombok.NonNull;

@Data
public class CommentUpdateRequest {
    @NonNull
    private String content;
}
