package com.powerup.model.roleauth;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RoleAuth {

    private Long idRole;
    private String name;
    private String description;

}
