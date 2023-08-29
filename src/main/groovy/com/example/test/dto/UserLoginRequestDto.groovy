package com.example.test.dto

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import lombok.Data

@Data
@ToString
@EqualsAndHashCode
class UserLoginRequestDto {
    String userName
    String email
    String password
}
