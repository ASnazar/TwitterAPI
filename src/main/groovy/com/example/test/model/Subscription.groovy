package com.example.test.model

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import lombok.ToString
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "subscriptions")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
class Subscription {
    @Id
    String id

    @DBRef
    User subscriber

    @DBRef
    User targetUser
}
