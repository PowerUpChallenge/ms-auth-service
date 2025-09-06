package com.powerup.usecase.roleauth;

import com.powerup.model.roleauth.RoleAuth;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class RoleAuthUseCase {

    private final com.powerup.model.roleauth.gateways.RoleAuthRepository roleAuthRepository;

    /**
     * Save a new role.
     * @param role
     * @return
     */
    public Mono<Void> saveRole(RoleAuth role) {
        return roleAuthRepository.saveRole(role);
    }

    /**
     * Retrieve a role by its ID.
     * @param idRole The ID of the role.
     * @return A Mono emitting the role if found, or empty if not found.
     */
    public Mono<RoleAuth> getByIdRole(Long idRole) {
        return roleAuthRepository.findByIdRole(idRole);
    }

}
