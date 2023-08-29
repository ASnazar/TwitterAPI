package com.example.test.service.impl

import com.example.test.model.Subscription
import com.example.test.model.User
import com.example.test.repository.SubscriptionRepository
import com.example.test.service.SubscriptionService
import com.example.test.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SubscriptionServiceImpl implements SubscriptionService{
    @Autowired
    private SubscriptionRepository subscriptionRepository

    @Autowired
    private UserService userService

    def createSubscription(User subscriber, User targetUser) {
        if (subscriber.id == targetUser.id) {
            throw new IllegalArgumentException("Cannot subscribe to yourself")
        }

        def existingSubscription = subscriptionRepository.findBySubscriberAndTargetUser(subscriber, targetUser)
        if (existingSubscription) {
            throw new IllegalStateException("Already subscribed to user with id ${targetUser.id}")
        }

        def subscription = new Subscription(subscriber: subscriber, targetUser: targetUser)
        subscriptionRepository.save(subscription)
    }

    def unsubscribe(User subscriber, User targetUser) {
        def existingSubscription = subscriptionRepository.findBySubscriberAndTargetUser(subscriber, targetUser)
        if (existingSubscription) {
            subscriptionRepository.delete(existingSubscription)
        } else {
            throw new NoSuchElementException("No subscription found to user with id ${targetUser.id}")
        }
    }

    def getSubscribedUsers(User user) {
        def subscriptions = subscriptionRepository.findBySubscriber(user)
        return subscriptions.collect { it.targetUser }
    }
}
