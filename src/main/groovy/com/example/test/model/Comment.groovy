package com.example.test.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DBRef
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import lombok.ToString

@Document(collection = "comments")
@Getter
@Setter
@ToString
@NoArgsConstructor
class Comment {
    @Id
    String id
    String text
    @DBRef
    User user
    @DBRef
    Post post
    @DBRef
    Set<User> likes = new HashSet<>()
}
