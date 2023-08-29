package com.example.test.service.impl

import com.example.test.dto.CommentDto
import com.example.test.model.Comment
import com.example.test.model.Post
import com.example.test.repository.CommentRepository
import com.example.test.service.CommentService
import lombok.AllArgsConstructor
import org.springframework.stereotype.Service

@Service
@AllArgsConstructor
class CommentServiceImpl implements CommentService{
    private CommentRepository commentRepository

    @Override
    void createComment(Comment comment) {
        commentRepository.save(comment)
    }

    @Override
    void updateComment(Comment comment) {
        commentRepository.save(comment)
    }

    @Override
    void deleteComment(String commentId) {
        commentRepository.deleteById(commentId)
    }

    @Override
    def findCommentById(String commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new NoSuchElementException("Can't find comment by id" + commentId))
    }

    List<CommentDto> getCommentsForPost(String postId) {
        def postComments = commentRepository.findByPost(postId)

        return postComments.collect { CommentDto(commentId: it.id, content: it.content) }
    }
}
