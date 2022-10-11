package com.dimas.order.data.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "ordering")
public class Order implements Serializable {

    private static final long serialVersionUID = 5575868736187611391L;

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;
    private UUID transactionId;
    private UUID userId;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    private String description;
    private LocalDateTime createdOn;
    private LocalDateTime modifiedOn;
    private LocalDateTime completedOn;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "id")
    @Builder.Default
    @ToString.Exclude
    private List<Item> items = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return id != null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

//    @Override
//    public boolean isNew() {
//        return true;
//    }
}
