package com.dimas.order.data.model;

import com.dimas.order.saga.SagaStage;
import com.dimas.order.saga.SagaStatus;
import com.dimas.order.saga.SagaType;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Saga implements Serializable {

    private static final long serialVersionUID = 5377324598470724356L;

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private UUID id;
    private UUID transactionId;//we cannot use order id as trace id since when we create saga order is not created yet
    @Enumerated(EnumType.STRING)
    private SagaStage stage;
    @Enumerated(EnumType.STRING)
    private SagaType type;
    @Enumerated(EnumType.STRING)
    private SagaStatus status;
    private String error;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Saga order = (Saga) o;
        return id != null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
