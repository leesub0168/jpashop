package jpabook.jpashop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit1() {
            Member member = createMember("userA", "seoul", "y", "1325");
            em.persist(member);

            Book book = createBook("JPA1 Book", 10000, 100);
            em.persist(book);

            Book book1 = createBook("JPA2 Book", 20000, 200);
            em.persist(book1);

            OrderItem orderItem = OrderItem.createOrderItem(book, 10000, 1);
            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem, orderItem1);
            em.persist(order);
        }

        public void dbInit2() {
            Member member = createMember("userB", "busan", "test", "121324");
            em.persist(member);

            Book book = createBook("Spring1 Book", 20000, 200);
            em.persist(book);

            Book book1 = createBook("Spring2 Book", 40000, 300);
            em.persist(book1);

            OrderItem orderItem = OrderItem.createOrderItem(book, 20000, 3);
            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 40000, 4);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem, orderItem1);
            em.persist(order);
        }

        private Book createBook(String name, int price, int qty) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(qty);
            return book;
        }

        private Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }
}
