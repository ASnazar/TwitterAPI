package com.example.test.controller

import com.example.test.dto.UserFeedDto
import com.example.test.dto.UserLoginRequestDto
import com.example.test.model.User
import com.example.test.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/user")
class UserController {
    private final UserService userService

    @Autowired
    UserController(UserService userService) {
        this.userService = userService
    }

    @PostMapping("/update")
    ResponseEntity<String> updateUser(@RequestBody UserLoginRequestDto userRequestDto) {
        try {
            User user = userService.findUserByEmail(userRequestDto.email)
            if (userRequestDto.getUserName() != null) {
                user.setUsername(userRequestDto.getUserName())
            }
            if (userRequestDto.getPassword() != null) {
                user.setPassword(userRequestDto.getPassword())
            }
            userService.updateUser(user)
            return ResponseEntity.ok("User updated with id = ${user.id}")
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found for email " + userRequestDto.email)
        }
    }

    @DeleteMapping("/delete/{userId}")
    ResponseEntity<String> deleteUser(@PathVariable String userId) {
        try {
            def user = userService.findUserById(userId)
            userService.deleteUser(user)
            return ResponseEntity.ok("User with id = ${user.id} has been deleted")
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found for id $userId")
        }
    }

    @GetMapping("/{userId}/feed")
    ResponseEntity<UserFeedDto> getUserFeed(@PathVariable String userId) {
        try {
            def userFeed = userService.getUserFeed(userId)
            return ResponseEntity.ok(userFeed)
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build()
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }
}
