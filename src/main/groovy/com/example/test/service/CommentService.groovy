package com.example.test.service

import com.example.test.dto.CommentDto
import com.example.test.model.Comment
import com.example.test.model.Post

interface CommentService {
    void createComment(Comment comment)

    void updateComment(Comment comment)

    void deleteComment(String commentId)

    def findCommentById(String commentId)

    List<CommentDto> getCommentsForPost(String postId)
}