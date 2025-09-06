package com.powerup.r2dbc.roleauth;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "role_auth")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleAuthEntity {

    @Id
    private Long idRole;
    private String name;
    private String description;

}
