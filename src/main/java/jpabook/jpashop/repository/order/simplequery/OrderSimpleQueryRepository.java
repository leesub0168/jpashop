package jpabook.jpashop.repository.order.simplequery;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.repository.OrderSimpleQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderSimpleQueryRepository {
    private final EntityManager em;

    /**
     * 해당 메소드는 OrderRepository에 두기에는, 리포지토리에 api 스펙이 들어와버리는 상황이 되버림
     * 리포지토리는 엔티티를 조회하는데 사용하는 용도이므로, 용도에 맞지않는 상황이 됨
     *
     * 그래서 따로 쿼리 리포지토리를 뽑아서 사용하도록 하는게 유지보수면에서 낫다.
     * */
    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                "select new jpabook.jpashop.repository.OrderSimpleQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                        "from Order o " +
                        "join o.member m " +
                        "join o.delivery d", OrderSimpleQueryDto.class
        ).getResultList();
    }
}
