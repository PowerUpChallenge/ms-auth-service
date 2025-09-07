package com.powerup.api.mapper;

import com.powerup.api.dto.request.UserAuthRequestDTO;
import com.powerup.api.dto.response.UserAuthResponseDTO;
import com.powerup.api.dto.response.UserValidateResponseDTO;
import com.powerup.model.userauth.UserAuth;
import org.mapstruct.Mapper;
import reactor.core.publisher.Flux;

@Mapper(componentModel = "spring")
public interface UserAuthMapper {

    UserAuthResponseDTO toResponseDTO(UserAuth userAuth);

    UserAuth toModel(UserAuthRequestDTO requestDTO);

    UserAuthResponseDTO toResponseDTOList(UserAuth usersAuth);

    UserValidateResponseDTO toValidateResponseDTO(UserAuth userAuth);

}
