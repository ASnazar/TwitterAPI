package com.example.test.model

import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import lombok.Getter
import lombok.Setter
import lombok.ToString

@Document(collection = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
class User {
    @Id
    String id
    String username
    String email
    String password
}
