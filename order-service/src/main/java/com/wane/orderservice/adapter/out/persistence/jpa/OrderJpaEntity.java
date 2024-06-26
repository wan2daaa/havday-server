package com.wane.orderservice.adapter.out.persistence.jpa;

import com.wane.orderservice.domain.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "orders")
public class OrderJpaEntity {

    @Setter
    @Id
    private String id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long addressId;

    @Column(nullable = false)
    private int totalPrice;

    @Column(nullable = false)
    private boolean isDeliveryFeeExists;

    @Column(nullable = false)
    private int usedPoint;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @CreatedDate
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_id")
    private PaymentJpaEntity payment;

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "orders_id")
    private List<ProductItemJpaEntity> productItems = new ArrayList<>();

    //builder 를 쓰면 추가 컬럼 변경에 유리
    //하지만 정확히 수정 범위를 파악하기 어렵다
    //그래서 써도 괜찮을까?
    @Builder
    private OrderJpaEntity(String id, Long memberId, Long addressId, int totalPrice, boolean isDeliveryFeeExists, int usedPoint, OrderStatus orderStatus, LocalDateTime createdAt, PaymentJpaEntity payment, List<ProductItemJpaEntity> productItems) {
        this.id = id;
        this.memberId = memberId;
        this.addressId = addressId;
        this.totalPrice = totalPrice;
        this.isDeliveryFeeExists = isDeliveryFeeExists;
        this.usedPoint = usedPoint;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.payment = payment;
        this.productItems = productItems;
    }

}
