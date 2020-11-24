package com.codersee.keycloakadmin.controller

import com.codersee.keycloakadmin.model.UserRequest
import com.codersee.keycloakadmin.service.RoleService
import com.codersee.keycloakadmin.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/user")
class UserController(
    private val userService: UserService,
    private val roleService: RoleService
) {
    @GetMapping
    fun findAll() =
        userService.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String) =
        userService.findById(id)

    @GetMapping("/username/{username}")
    fun findByUsername(@PathVariable username: String) =
        userService.findByUsername(username)

    @PostMapping
    fun create(@RequestBody userRequest: UserRequest): ResponseEntity<URI> {
        val response = userService.create(userRequest)

        if (response.status != 201)
            throw RuntimeException("User was not created")

        return ResponseEntity.created(response.location).build()
    }

    @PostMapping("/{userId}/group/{groupId}")
    fun assignToGroup(
        @PathVariable userId: String,
        @PathVariable groupId: String
    ) {
        userService.assignToGroup(userId, groupId)
    }

    @PostMapping("/{userId}/role/{roleName}")
    fun assignRole(
        @PathVariable userId: String,
        @PathVariable roleName: String
    ) {
        val role = roleService.findByName(roleName)

        userService.assignRole(userId, role)
    }
}

