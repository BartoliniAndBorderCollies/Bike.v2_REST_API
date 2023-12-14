package com.klodnicki.Bike.v2.DTO.authority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) for Authority.
 * This class is annotated with annotations from Lombok library to
 * automatically generate boilerplate code like getters, setters, equals, hashcode and toString methods, and constructors with no arguments and all arguments.
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
