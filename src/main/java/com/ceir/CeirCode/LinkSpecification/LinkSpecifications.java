package com.ceir.CeirCode.LinkSpecification;

import com.ceir.CeirCode.model.app.LinkDetails;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class LinkSpecifications {

    public static Specification<LinkDetails> hasStatus(String status) {
        return (root, query, builder) -> builder.equal(root.get("status"), status);
    }

    public static Specification<LinkDetails> hasLinkId(String linkId) {
        return (root, query, builder) -> builder.equal(root.get("linkId"), linkId);
    }

    public static Specification<LinkDetails> createdAfter(LocalDateTime date) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("createdOn"), date);
    }

    public static Specification<LinkDetails> createdBefore(LocalDateTime date) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("createdOn"), date);
    }
}
