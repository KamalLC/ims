package kamal.ims.post.service;

import jakarta.transaction.Transactional;
import kamal.ims.post.dto.comment.CommentResponse;
import kamal.ims.post.mapper.comment.CommentMapper;
import kamal.ims.post.model.Comment;
import kamal.ims.post.model.Post;
import kamal.ims.post.repo.CommentRepo;
import kamal.ims.post.repo.PostRepo;
import kamal.ims.user.model.User;
import kamal.ims.user.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Transactional
    public Comment createComment(long postId, long userId, String content) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found: " + postId));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content must not be empty");
        }
        if (content.length() > 2000) {
            throw new IllegalArgumentException("Content length must be <= 2000 characters");
        }

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setParent(null);
        comment.setContent(content);
        comment.setStatus("ACTIVE");
        comment.setCreatedDate(LocalDateTime.now());

        return commentRepo.save(comment);
    }

    @Transactional
    public Comment replyToComment(int parentCommentId, long userId, String content) {
        Comment parent = commentRepo.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found: " + parentCommentId));
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));

        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("Content must not be empty");
        }
        if (content.length() > 2000) {
            throw new IllegalArgumentException("Content length must be <= 2000 characters");
        }

        Comment reply = new Comment();
        reply.setPost(parent.getPost());  // replies belong to same post
        reply.setUser(user);
        reply.setParent(parent);
        reply.setContent(content);
        reply.setStatus("ACTIVE");
        reply.setCreatedDate(LocalDateTime.now());

        return commentRepo.save(reply);
    }

    public Page<CommentResponse> getTopLevelCommentsForPost(long postId, int page, int size, Sort sort, boolean includeReplies) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found: " + postId));

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Comment> commentsPage = commentRepo.findByPostAndParentIsNull(post, pageable);

        List<CommentResponse> responses = new ArrayList<>();
        for (Comment c : commentsPage.getContent()) {
            long replyCount = commentRepo.countByParent(c);
            if (includeReplies) {
                // Fetch all replies for each top-level comment (simple pagination disabled here)
                List<CommentResponse> replies = buildRepliesRecursively(c, Sort.by(Sort.Direction.ASC, "createdDate"));
                responses.add(CommentMapper.toResponse(c, replyCount, replies));
            } else {
                responses.add(CommentMapper.toResponseWithoutReplies(c, replyCount));
            }
        }

        return new PageImpl<>(responses, pageable, commentsPage.getTotalElements());
    }

    public CommentResponse getCommentThread(int commentId) {
        Comment root = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found: " + commentId));
        long replyCount = commentRepo.countByParent(root);
        List<CommentResponse> replies = buildRepliesRecursively(root, Sort.by(Sort.Direction.ASC, "createdDate"));
        return CommentMapper.toResponse(root, replyCount, replies);
    }

    @Transactional
    public Comment updateComment(int commentId, int userId, String newContent) {
        Comment c = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found: " + commentId));

        if (c.getUser() == null || c.getUser().getId() != userId) {
            throw new RuntimeException("You are not allowed to edit this comment");
        }

        if (newContent == null || newContent.isBlank()) {
            throw new IllegalArgumentException("Content must not be empty");
        }
        if (newContent.length() > 2000) {
            throw new IllegalArgumentException("Content length must be <= 2000 characters");
        }

        c.setContent(newContent);
        c.setUpdatedDate(LocalDateTime.now());
        return commentRepo.save(c);
    }

    @Transactional
    public void softDeleteComment(int commentId, int userId) {
        Comment c = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found: " + commentId));

        if (c.getUser() == null || c.getUser().getId() != userId) {
            throw new RuntimeException("You are not allowed to delete this comment");
        }

        c.setStatus("DELETED");
        c.setDeletedDate(LocalDateTime.now());
        c.setContent("[deleted]");
        commentRepo.save(c);
    }

    private List<CommentResponse> buildRepliesRecursively(Comment parent, Sort sort) {
        List<CommentResponse> list = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, sort);
        Page<Comment> repliesPage = commentRepo.findByParent(parent, pageable);

        for (Comment reply : repliesPage.getContent()) {
            long childReplyCount = commentRepo.countByParent(reply);
            List<CommentResponse> childReplies = buildRepliesRecursively(reply, sort);
            list.add(CommentMapper.toResponse(reply, childReplyCount, childReplies));
        }
        return list;
    }
}
