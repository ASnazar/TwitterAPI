package com.example.test.service

import com.example.test.model.User

interface SubscriptionService {
    def createSubscription(User subscriber, User targetUser)

    def unsubscribe(User subscriber, User targetUser)

    def getSubscribedUsers(User user)
}