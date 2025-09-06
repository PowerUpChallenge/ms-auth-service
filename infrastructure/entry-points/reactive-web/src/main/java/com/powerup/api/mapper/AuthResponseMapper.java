package com.powerup.api.mapper;

import com.powerup.api.dto.response.AuthResponseDTO;
import com.powerup.model.userauth.AuthResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthResponseMapper {

    AuthResponseDTO toResponseDTO(AuthResponse authResponse);

}
