package com.example.test.controller

import com.example.test.service.SubscriptionService
import com.example.test.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/subscriptions")
class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService
    @Autowired
    private UserService userService

    @PostMapping("/{targetUserId}/subscribe")
    ResponseEntity<String> subscribeToUser(@PathVariable String targetUserId) {
        try {
            def currentUser = userService.getCurrentUser()
            def targetUser = userService.findUserById(targetUserId)

            if (currentUser.id == targetUser.id) {
                return ResponseEntity.badRequest().body("You cannot subscribe to yourself")
            }

            subscriptionService.createSubscription(currentUser, targetUser)
            return ResponseEntity.ok("Subscribed to user with id = ${targetUser.id}")
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for id $targetUserId")
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: ${e.message}")
        }
    }

    @PostMapping("/{targetUserId}/unsubscribe")
    ResponseEntity<String> unsubscribeFromUser(@PathVariable String targetUserId) {
        try {
            def currentUser = userService.getCurrentUser()
            def targetUser = userService.findUserById(targetUserId)
            subscriptionService.unsubscribe(currentUser, targetUser)
            return ResponseEntity.ok("Unsubscribed from user with id = ${targetUser.id}")
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found for id $targetUserId")
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: ${e.message}")
        }
    }
}
