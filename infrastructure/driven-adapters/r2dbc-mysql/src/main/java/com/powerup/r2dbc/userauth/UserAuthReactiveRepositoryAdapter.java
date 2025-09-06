package com.powerup.r2dbc.userauth;

import com.powerup.model.userauth.UserAuth;
import com.powerup.model.userauth.gateways.UserAuthRepository;
import com.powerup.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
public class UserAuthReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        UserAuth,
        UserAuthEntity,
        Long,
        UserAuthReactiveRepository
        > implements UserAuthRepository  {

    private final TransactionalOperator transactionalOperator;

    public UserAuthReactiveRepositoryAdapter(UserAuthReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {

        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, UserAuth.class));
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Mono<Void> saveUser(UserAuth user) {
        return repository.save(mapper.map(user, UserAuthEntity.class))
                .then()
                .as(transactionalOperator::transactional);
    }

    @Override
    public Mono<UserAuth> getByEmail(String email) {
        return repository.findByEmail(email)
                .map(userEntity -> mapper.map(userEntity, UserAuth.class));
    }

}
