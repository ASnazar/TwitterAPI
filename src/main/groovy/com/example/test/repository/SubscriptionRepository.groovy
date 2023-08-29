package com.example.test.repository

import com.example.test.model.Subscription
import com.example.test.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    Subscription findBySubscriberAndTargetUser(User subscriber, User targetUser)
    List<Subscription> findBySubscriber(User subscriber)
}