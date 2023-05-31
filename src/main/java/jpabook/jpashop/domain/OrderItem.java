package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 당시 가격
    private int count; // 주문 수량
    
    /** createOrderItem을 사용하지 않고, 
     * 생성자를 이용해 직접 객체를 생성해서 set하는 경우를 막기 위해선 protected 생성자를 만들어주면 됨
     * 롬복의 @NoArgsConstructor(access = AccessLevel.PROTECTED) 로 대체가능
    protected OrderItem() {

    }*/

    // 생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);

        return orderItem;
    }

    /**
     * 비즈니스 로직
     * */
    public void cancel() {
        getItem().addStock(count);
    }


    /**
     * 조회 로직
     * */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
