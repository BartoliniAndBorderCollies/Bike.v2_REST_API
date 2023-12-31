package com.klodnicki.Bike.v2.DTO.authority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * This class is used to wrap {@link com.klodnicki.Bike.v2.model.entity.Authority Authority class} as DTO, and it is used only
 * to prepare a DTO for normal user. It is created to provide authorities (roles) to build a user DTO instance with those roles
 * during a rent procedure.
 * <br>
 * This class has annotations from the Lombok library to automatically generate boilerplate code like getters, setters,
 * equals, hashcode, and toString methods,
 * a builder pattern, and constructors with no arguments and all arguments.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityDTO {
    private Long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorityDTO that = (AuthorityDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
