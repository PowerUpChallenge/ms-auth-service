package com.powerup.model.roleauth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleAuthTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        RoleAuth role = new RoleAuth();

        role.setIdRole(1L);
        role.setName("ADMIN");
        role.setDescription("Administrator role");

        assertEquals(1L, role.getIdRole());
        assertEquals("ADMIN", role.getName());
        assertEquals("Administrator role", role.getDescription());
    }

    @Test
    void testAllArgsConstructor() {
        RoleAuth role = new RoleAuth(2L, "USER", "User role");

        assertEquals(2L, role.getIdRole());
        assertEquals("USER", role.getName());
        assertEquals("User role", role.getDescription());
    }

    @Test
    void testBuilder() {
        RoleAuth role = RoleAuth.builder()
                .idRole(3L)
                .name("MANAGER")
                .description("Manager role")
                .build();

        assertEquals(3L, role.getIdRole());
        assertEquals("MANAGER", role.getName());
        assertEquals("Manager role", role.getDescription());
    }

    @Test
    void testBuilderToBuilder() {
        RoleAuth original = RoleAuth.builder()
                .idRole(4L)
                .name("AUDITOR")
                .description("Auditor role")
                .build();

        RoleAuth modified = original.toBuilder()
                .name("SUPERVISOR")
                .build();

        assertEquals(4L, modified.getIdRole()); // keeps same id
        assertEquals("SUPERVISOR", modified.getName()); // updated field
        assertEquals("Auditor role", modified.getDescription()); // unchanged
    }
}
