package com.example.test.service.impl

import com.example.test.model.Subscription
import com.example.test.model.User
import com.example.test.repository.SubscriptionRepository
import com.example.test.service.UserService
import spock.lang.Specification
import org.springframework.boot.test.mock.mockito.MockBean

class SubscriptionServiceTest extends Specification {

    @MockBean
    SubscriptionRepository subscriptionRepository

    @MockBean
    UserService userService

    def "Test createSubscription method"() {
        given:
        def subscriber = new User(id: "user123", username: "subscriber", email: "subscriber@example.com")
        def targetUser = new User(id: "user456", username: "targetuser", email: "targetuser@example.com")
        def subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, userService)

        when:
        subscriptionService.createSubscription(subscriber, targetUser)

        then:
        1 * subscriptionRepository.findBySubscriberAndTargetUser(subscriber, targetUser) >> null
        1 * subscriptionRepository.save(_)

        and:
        noExceptionThrown()
    }

    def "Test createSubscription method with existing subscription"() {
        given:
        def subscriber = new User(id: "user123", username: "subscriber", email: "subscriber@example.com")
        def targetUser = new User(id: "user456", username: "targetuser", email: "targetuser@example.com")
        def existingSubscription = new Subscription(subscriber: subscriber, targetUser: targetUser)
        def subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, userService)

        when:
        subscriptionRepository.findBySubscriberAndTargetUser(subscriber, targetUser) >> existingSubscription

        then:
        shouldFail(IllegalStateException) {
            subscriptionService.createSubscription(subscriber, targetUser)
        }
    }

    def "Test unsubscribe method"() {
        given:
        def subscriber = new User(id: "user123", username: "subscriber", email: "subscriber@example.com")
        def targetUser = new User(id: "user456", username: "targetuser", email: "targetuser@example.com")
        def existingSubscription = new Subscription(subscriber: subscriber, targetUser: targetUser)
        def subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, userService)

        when:
        subscriptionRepository.findBySubscriberAndTargetUser(subscriber, targetUser) >> existingSubscription

        then:
        1 * subscriptionRepository.delete(existingSubscription)

        and:
        noExceptionThrown()
    }

    def "Test unsubscribe method with no existing subscription"() {
        given:
        def subscriber = new User(id: "user123", username: "subscriber", email: "subscriber@example.com")
        def targetUser = new User(id: "user456", username: "targetuser", email: "targetuser@example.com")
        def subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, userService)

        when:
        subscriptionRepository.findBySubscriberAndTargetUser(subscriber, targetUser) >> null

        then:
        shouldFail(NoSuchElementException) {
            subscriptionService.unsubscribe(subscriber, targetUser)
        }
    }

    def "Test getSubscribedUsers method"() {
        given:
        def subscriber = new User(id: "user123", username: "subscriber", email: "subscriber@example.com")
        def subscribedUser1 = new User(id: "user456", username: "subscribeduser1", email: "subscribeduser1@example.com")
        def subscribedUser2 = new User(id: "user789", username: "subscribeduser2", email: "subscribeduser2@example.com")
        def subscriptions = [new Subscription(subscriber: subscriber, targetUser: subscribedUser1),
                             new Subscription(subscriber: subscriber, targetUser: subscribedUser2)]
        def subscriptionService = new SubscriptionServiceImpl(subscriptionRepository, userService)

        when:
        subscriptionRepository.findBySubscriber(subscriber) >> subscriptions

        then:
        def result = subscriptionService.getSubscribedUsers(subscriber)
        result.size() == 2
        result.containsAll([subscribedUser1, subscribedUser2])
    }
}
