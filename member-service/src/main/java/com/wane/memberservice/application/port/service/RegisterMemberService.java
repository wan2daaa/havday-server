package com.wane.memberservice.application.port.service;

import com.wane.exception.CustomException;
import com.wane.exception.ErrorCode;
import com.wane.memberservice.application.port.in.RegisterUserCommand;
import com.wane.memberservice.application.port.in.RegisterUserUseCase;
import com.wane.memberservice.application.port.out.ExistMemberPort;
import com.wane.memberservice.application.port.out.RegisterMemberPort;
import com.wane.memberservice.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RegisterMemberService implements RegisterUserUseCase {

	private final RegisterMemberPort registerMemberPort;
	private final ExistMemberPort existMemberPort;

	@Transactional
	@Override
	public void registerUser(RegisterUserCommand command) {

		boolean isUserExists = existMemberPort.existMemberByEmail(command.getEmail());

		if (isUserExists) {
			throw new CustomException(ErrorCode.MEMBER_ALREADY_REGISTERED);
		}

		Member member = Member.createUser(
				command.getName(),
				command.getEmail(),
				command.getPassword(),
				command.getPhoneNumber(),
				command.getAuthServiceType(),
				command.getAuthId()
		);
		registerMemberPort.registerUser(member);
	}
}
