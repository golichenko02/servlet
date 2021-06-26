package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.StringJoiner;

@Embeddable
@Getter
@Setter
@Accessors(chain = true)
public class CommonInfo {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false, length = 1000)
    private String description;

    @Override
    public String toString() {
        return new StringJoiner(", ", CommonInfo.class.getSimpleName() + "[", "]")
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .toString();
    }
}
