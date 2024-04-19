package com.concurrency.jpa.customer.Product.entity;


import com.concurrency.jpa.customer.Product.enums.ActualStatus;
import com.concurrency.jpa.customer.order.Order;
import com.concurrency.jpa.customer.order.dto.ActualProductDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "actual_product")
public class ActualProduct {
    @Id
    @Column(name = "actual_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "actual_status", columnDefinition = "varchar(255)")
    private ActualStatus actualStatus;

    @ManyToOne
    @JoinColumn(name = "core_product_id", nullable = false)
    private CoreProduct coreProduct;

    @ManyToOne
    @Getter
    @JoinColumn(name = "order_id")
    private Order order;

    @Getter
    @Column(name = "actual_price")
    private Long actualPrice;

    @Column(name = "discount_rate")
    private float discountRate;

    public void updateActualProductStatus(ActualStatus actualStatus){
        this.actualStatus = actualStatus;
    }

    public ActualProductDto toDto(){
        return ActualProductDto.builder()
                .actualProductId(id)
                .actualStatus(actualStatus)
                .coreProductId(coreProduct.getId())
                .actualPrice(actualPrice)
                .discountRate(discountRate)
                .build();
    }
}
