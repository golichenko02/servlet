package org.hillel.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hillel.persistence.entity.util.YesNoConverter;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Accessors(chain = true)
@MappedSuperclass
public abstract class AbstractModifyEntity<ID extends Serializable>  implements Persistable<ID> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

    @Column(name = "create_date")
    @CreationTimestamp
    private Instant createDate;

    @Column(name = "active")
    @Convert(converter = YesNoConverter.class)
    private boolean active = true;

    @Override
    public boolean isNew() {
        return id == null;
    }
}
