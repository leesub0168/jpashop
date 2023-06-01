package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
            /**
             * merge 는 변경감지와 달리 모든 필드를 update 해버림.
             * 따라서 기존에 값이 있던 필드가 null 로 업데이트 쳐질 가능성이 있음.
             * merge 보다는 변경감지를 사용하는게 좋음.
             */
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
