package kamal.ims.post.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kamal.ims.post.dto.comment.CommentCreateRequest;
import kamal.ims.post.dto.comment.CommentReplyRequest;
import kamal.ims.post.dto.comment.CommentUpdateRequest;
import kamal.ims.post.model.Comment;
import kamal.ims.post.service.CommentService;
import kamal.ims.util.ApiResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/create")
public class CommentCreationController {

    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/comment")
    public ResponseEntity<Map<String, Object>> createComment(@RequestBody CommentCreateRequest request)
            throws JsonProcessingException {

        Comment saved = commentService.createComment(request.getPostId(), request.getUserId(), request.getContent());

        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseBuilder()
                .status(HttpStatus.CREATED)
                .message("Comment Created Successfully.")
                .data(saved)
                .build()
        );
    }


    @PatchMapping(value = "/comment/{commentId}")
    public ResponseEntity<Map<String, Object>> updateComment(
            @PathVariable int commentId,
            @RequestBody CommentUpdateRequest request,
            @RequestParam int userId
    ) throws JsonProcessingException {

        Comment updated = commentService.updateComment(commentId, userId, request.getContent());

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Comment Updated Successfully.")
                .data(updated)
                .build()
        );
    }

    @DeleteMapping(value = "/comment/{commentId}")
    public ResponseEntity<Map<String, Object>> deleteComment(
            @PathVariable int commentId,
            @RequestParam int userId
    ) throws JsonProcessingException {

        commentService.softDeleteComment(commentId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseBuilder()
                .status(HttpStatus.OK)
                .message("Comment Deleted Successfully.")
                .build()
        );
    }
}
