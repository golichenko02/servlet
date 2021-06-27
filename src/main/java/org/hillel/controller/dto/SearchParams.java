package org.hillel.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hillel.persistence.jpa.repository.specification.ISpecification;
import org.hillel.persistence.jpa.repository.specification.VehicleSpecification;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchParams {
    private int pageNumber;
    private int pageSize;
    private String orderField;
    private String isAsc;
    private String specificationFilter;
    private String value;

    public SearchParams(int pageNumber, int pageSize, String orderField, String isAsc) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.orderField = orderField;
        this.isAsc = isAsc;
    }
}
