package com.gagoo.thiscoding.domain.mongo.code.service;

import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.mongo.code.controller.port.CodeService;
import com.gagoo.thiscoding.domain.mongo.code.domain.Code;
import com.gagoo.thiscoding.domain.mongo.code.domain.dto.CodeCreate;
import com.gagoo.thiscoding.domain.mongo.code.service.exception.CodeNotFoundException;
import com.gagoo.thiscoding.domain.mongo.code.service.exception.ExistCodeFileName;
import com.gagoo.thiscoding.domain.mongo.code.service.port.CodeRepository;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {
    private final CodeRepository codeRepository;
    private final UserRepository userRepository;
    private final SecurityUtils securityUtils;

    /**
     * 코드 생성(저장)
     * @param codeCreate
     * @return 생성(저장)한 Code
     */
    @Override
    public Code createCode(CodeCreate codeCreate) {
        Code code = Code.create(codeCreate, getCurrentUser().getId(), getCurrentUser().getNickname());

        return codeRepository.save(code);
    }

    /**
     * 코드 저장
     * @param codeCreate
     * @return 저장한 Code
     */
    @Override
    public Code saveCode(CodeCreate codeCreate) {
        Code code = codeRepository.findById(codeCreate.getId()).orElse(null);

        // 한 번도 저장되지 않은 코드일 경우
        if(code == null) {
            // 파일명 중복 검사
            validateRoomIdAndFileNameExists(codeCreate.getRoomId(), codeCreate.getFileName());
            return createCode(codeCreate);
        } else {
            code = Code.save(codeCreate, getCurrentUser().getId(), getCurrentUser().getNickname());
            return codeRepository.save(code);
        }
    }

    /**
     * 코드 조회
     * @param codeId
     * @return 조회한 Code
     */
    @Override
    public Code getById(String codeId) {
        return codeRepository.findById(codeId).orElseThrow(
                () -> new CodeNotFoundException(ErrorCode.CODE_NOT_FOUND)
        );
    }

    /**
     * 특정 코드방 내의 파일명 중복 검증
     * @param roomId
     * @param fileName
     */
    @Override
    public void validateRoomIdAndFileNameExists(Long roomId, String fileName) {
        if(codeRepository.existsByRoomIdAndFileName(roomId, fileName)) {
            throw new ExistCodeFileName(ErrorCode.EXIST_CODE_FILENAME);
        }
    }

    /**
     * 로그인 사용자 조회
     * @return user
     */
    private User getCurrentUser() {
        return userRepository.getByEmail(securityUtils.getUserEmail());
    }
}
