package kamal.ims.post.dto.comment;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CommentResponse {
    private long id;
    private long postId;
    private long parentId;
    private long userId;
    private String content;
    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private LocalDateTime deletedDate;

    private long replyCount;
    private List<CommentResponse> replies;
}
