package com.gagoo.thiscoding.domain.maria.user.controller;

import com.gagoo.thiscoding.domain.auth.domain.Token;
import com.gagoo.thiscoding.domain.auth.dto.TokenDto;
import com.gagoo.thiscoding.domain.maria.user.controller.port.TokenReissueService;
import com.gagoo.thiscoding.global.common.util.HttpServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.gagoo.thiscoding.domain.auth.common.AuthConstants.AUTHORIZATION;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class TokenReissueController {

    private final TokenReissueService tokenReissueService;
    private final HttpServletUtils servletUtils;

    @GetMapping("/reissue")
    private ResponseEntity<Void> reissue(HttpServletRequest request, HttpServletResponse response) {
        TokenDto tokenDto = TokenDto.from(tokenReissueService.create(request));

        servletUtils.setHeader(response, AUTHORIZATION, tokenDto.getAtk());
        servletUtils.addCookie(response, AUTHORIZATION, tokenDto.getRtk(), tokenDto.getRtkExpTime());

        return ResponseEntity.ok().build();
    }
}
