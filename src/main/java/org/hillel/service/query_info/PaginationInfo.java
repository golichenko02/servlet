package org.hillel.service.query_info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hillel.persistence.jpa.repository.specification.ISpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaginationInfo {

    private int pageNumber;
    private int pageSize;
    private String orderField;
    private boolean isAsc;
    private ISpecification specificationFilter;
    private String value;


    public PaginationInfo(int pageNumber, int pageSize, String orderField) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.orderField = orderField;
    }

    public PaginationInfo(int pageNumber, int pageSize, String orderField, boolean isAsc) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.orderField = orderField;
        this.isAsc = isAsc;
    }

    public int getFirstResult() {
        return (pageNumber - 1) * pageSize;
    }

    public String getOrderBy() {
        if (!StringUtils.isEmpty(orderField)) {
            return " order by " + orderField + (isAsc ? " asc" : " desc");
        }
        return "";
    }

    public PageRequest createPageRequest() {
        if (orderField == null || orderField.isEmpty()) {
            return PageRequest.of(getPageNumber(), getPageSize());
        }
        return PageRequest.of(getPageNumber(),
                getPageSize(),
                Sort.by(isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC, orderField));

    }

}
