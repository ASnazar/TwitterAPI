package com.example.test.dto

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class UserFeedDto {
    String userId
    String username
    List<PostWithLikesAndCommentsDto> posts = []
}
