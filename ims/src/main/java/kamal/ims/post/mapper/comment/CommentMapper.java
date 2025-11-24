package kamal.ims.post.mapper.comment;

import kamal.ims.post.dto.comment.CommentResponse;
import kamal.ims.post.model.Comment;

import java.util.List;

public class CommentMapper {

    public static CommentResponse toResponse(Comment c, long replyCount, List<CommentResponse> replies) {
        return CommentResponse.builder()
                .id(c.getId())
                .postId(c.getPost().getId())
                .parentId(c.getParent() != null ? c.getParent().getId() : null)
                .userId(c.getUser().getId())
                .content(c.getContent())
                .status(c.getStatus())
                .createdDate(c.getCreatedDate())
                .updatedDate(c.getUpdatedDate())
                .deletedDate(c.getDeletedDate())
                .replyCount(replyCount)
                .replies(replies)
                .build();
    }

    public static CommentResponse toResponseWithoutReplies(Comment c, long replyCount) {
        return toResponse(c, replyCount, null);
    }
}
