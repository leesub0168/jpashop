package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;
    
    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = creatMemeber();

        Book book = createBook("JPA", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order findOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, findOrder.getStatus());
        assertEquals(1, findOrder.getOrderItems().size());
        assertEquals(10000 * orderCount, findOrder.getTotalPrice());
        assertEquals(8, book.getStockQuantity());
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = creatMemeber();
        Book jpa = createBook("jpa", 10000, 10);

        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), jpa.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order findOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, findOrder.getStatus());
        assertEquals(10, jpa.getStockQuantity());
    }
    
    @Test
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = creatMemeber();
        Book jpa = createBook("jpa", 10000, 10);

        int orderCount = 11;
        
        //when
        //then
        assertThrows(NotEnoughStockException.class
                , () -> orderService.order(member.getId(), jpa.getId(), orderCount)
                ,"재고 부족 예외가 발생해야한다."
        );

    }
    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member creatMemeber() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","관악구","03466"));
        em.persist(member);
        return member;
    }
}