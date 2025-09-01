package com.powerup.r2dbc.roleauth;

import com.powerup.model.roleauth.RoleAuth;
import com.powerup.model.roleauth.gateways.RoleAuthRepository;
import com.powerup.model.userauth.UserAuth;
import com.powerup.model.userauth.gateways.UserAuthRepository;
import com.powerup.r2dbc.helper.ReactiveAdapterOperations;
import com.powerup.r2dbc.userauth.UserAuthEntity;
import com.powerup.r2dbc.userauth.UserAuthReactiveRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class RoleAuthReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        RoleAuth,
        RoleAuthEntity,
        Long,
        RoleAuthReactiveRepository
> implements RoleAuthRepository/* change for domain repository */ {
    public RoleAuthReactiveRepositoryAdapter(RoleAuthReactiveRepository repository, ObjectMapper mapper) {

        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, RoleAuth.class/* change for domain model */));
    }

    @Override
    public Mono<Void> saveRole(RoleAuth role) {
        return super.save(role).then();
    }

    @Override
    public Mono<RoleAuth> findByIdRole(Long idRole) {
        return super.findById(idRole);
    }
}
