package com.example.test.dto

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class PostWithLikesAndCommentsDto {
    String postId
    String content
    List<String> likes = []
    List<CommentDto> comments = []
}
