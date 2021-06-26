package org.hillel.persistence.jpa.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

public interface ISpecification<T> {

    default Specification<T> getSpecification() {
        return null;
    }

    default Specification<T> getSpecification(@Nullable String  value) {
        return null;
    }

    default Specification<T> getSpecification(@Nullable String value, @Nullable T entity){
        return null;
    }

}
