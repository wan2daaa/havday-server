package com.wane.memberservice.adapter.out.persistence;

import com.wane.exception.CustomException;
import com.wane.exception.ErrorCode;
import com.wane.memberservice.adapter.out.persistence.jpa.MemberJpaEntity;
import com.wane.memberservice.adapter.out.persistence.jpa.MemberJpaEntityRepository;
import com.wane.memberservice.application.port.out.GetMemberPort;
import com.wane.memberservice.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class GetMemberPersistenceAdapter implements GetMemberPort {

    private final MemberJpaEntityRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public Member getUser(Long memberId) {
        MemberJpaEntity memberJpaEntity = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_MATCH));

        return memberMapper.toDomainEntity(memberJpaEntity);
    }
}
