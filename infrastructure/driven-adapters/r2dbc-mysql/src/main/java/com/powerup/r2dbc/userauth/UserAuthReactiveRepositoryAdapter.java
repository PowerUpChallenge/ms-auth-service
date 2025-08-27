package com.powerup.r2dbc;

import com.powerup.model.userauth.UserAuth;
import com.powerup.model.userauth.gateways.UserAuthRepository;
import com.powerup.r2dbc.entity.UserAuthEntity;
import com.powerup.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserAuthReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        UserAuth,
        UserAuthEntity,
        Long,
        UserAuthReactiveRepository
> implements UserAuthRepository/* change for domain repository */ {
    public UserAuthReactiveRepositoryAdapter(UserAuthReactiveRepository repository, ObjectMapper mapper) {

        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, UserAuth.class/* change for domain model */));
    }

    @Override
    public Mono<Void> saveUser(UserAuth user) {
        return null;
    }

    @Override
    public Mono<UserAuth> getByIdNumber(String idNumber) {
        return null;
    }

    @Override
    public Mono<UserAuth> getByEmail(String email) {
        return repository.findByEmail(email)
                .map(userEntity -> mapper.map(userEntity, UserAuth.class));
    }

    @Override
    public Mono<UserAuth> updateUser(UserAuth user) {
        return null;
    }

    @Override
    public Flux<UserAuth> getAll() {
        return null;
    }

    @Override
    public Mono<Void> deleteByIdNumber(String idNumber) {
        return null;
    }
}
