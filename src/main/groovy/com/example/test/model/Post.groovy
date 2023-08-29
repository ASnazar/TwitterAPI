package com.example.test.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DBRef
import java.time.LocalDateTime
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import lombok.ToString

@Document(collection = "posts")
@Getter
@Setter
@ToString
@NoArgsConstructor
class Post {
    @Id
    String id
    String content
    @DBRef
    User user
    @DBRef
    Set<User> likes = new HashSet<>()
}
