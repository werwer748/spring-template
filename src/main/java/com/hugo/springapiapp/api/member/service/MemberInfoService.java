package com.hugo.springapiapp.api.member.service;

import com.hugo.springapiapp.api.member.dto.MemberInfoResponseDto;
import com.hugo.springapiapp.domain.member.entity.Member;
import com.hugo.springapiapp.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberInfoService {

    private  final MemberService memberService;

    public MemberInfoResponseDto getMemberInfo(Long memberId) {
        Member member = memberService.findMemberByMemberId(memberId);

        return MemberInfoResponseDto.of(member);
    }
}
