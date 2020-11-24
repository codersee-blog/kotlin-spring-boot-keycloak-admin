package com.codersee.keycloakadmin.service

import org.keycloak.admin.client.Keycloak
import org.keycloak.representations.idm.GroupRepresentation
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GroupService(
    private val keycloak: Keycloak,
    @Value("\${keycloak.realm}")
    private val realm: String
) {
    fun create(name: String) {
        val group = GroupRepresentation()
        group.name = name

        keycloak
            .realm(realm)
            .groups()
            .add(group)
    }

    fun findAll(): List<GroupRepresentation> =
        keycloak
            .realm(realm)
            .groups()
            .groups()
}