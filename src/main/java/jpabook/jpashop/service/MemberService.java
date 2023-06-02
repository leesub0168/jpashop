package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 읽기 메서드에는 readOnly - true 를 주는게 좀더 효율면에서 좋음
//@AllArgsConstructor // 생성자 injection 을 알아서 생성해줌
@RequiredArgsConstructor // final 이 있는 필드만 가지고 생성자를 만들어줌
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 최신 버전 스프링에서는 생성자가 하나만 있는 경우 @Autowired 를 생략해도 알아서 주입해줌
     * 의존성 주입은 최근 가장 추천하는건 1순위 : 생성자 injection, 2순위 : setter injection
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
     */

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }
    /**
     * 중복 회원 검증
     */
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName()); // 실무에서는 추가적인 안정장치로 테이블에 unique 까지 걸어주는게 좋음
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 회원 단건 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) { // 커맨드와 쿼리는 분리
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
