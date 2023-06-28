package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryOldTest {

    @Autowired
    MemberRepositoryOld memberRepositoryOld;

    @Test
    @Transactional
    @Rollback(value = false)
    /**
     * 스프링에서 테스트에서 실행한 데이터는 테스트가 끝나면 롤백함.
     * 롤백 되는걸 원치않으면 @Rollback(value = false)를 주면 데이터가 롤백되지 않고 남는다.
     * */
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setName("memberA");

        //when
        memberRepositoryOld.save(member);
        Member findMember = memberRepositoryOld.findOne(member.getId());

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
        Assertions.assertThat(findMember).isEqualTo(member);
    }
}