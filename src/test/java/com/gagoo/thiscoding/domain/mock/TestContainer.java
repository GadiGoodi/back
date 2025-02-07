package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.domain.maria.manager.controller.port.ManagerService;
import com.gagoo.thiscoding.domain.maria.manager.service.ManagerServiceImpl;
import com.gagoo.thiscoding.domain.maria.manager.service.port.ManagerRepository;
import com.gagoo.thiscoding.domain.maria.reply.controller.port.ReplyService;
import com.gagoo.thiscoding.domain.maria.reply.service.ReplyServiceImpl;
import com.gagoo.thiscoding.domain.maria.reply.service.port.ReplyRepository;
import com.gagoo.thiscoding.domain.maria.user.controller.port.CertificationService;
import com.gagoo.thiscoding.domain.maria.user.service.CertificationServiceImpl;
import com.gagoo.thiscoding.domain.maria.user.service.port.JoinCodeStore;
import com.gagoo.thiscoding.domain.maria.user.service.port.MailSender;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.service.BoardServiceImpl;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import com.gagoo.thiscoding.global.security.service.port.PasswordEncoderHolder;
import com.gagoo.thiscoding.global.security.service.port.SecurityUtils;
import lombok.Builder;

public class TestContainer {

    public final MailSender mailSender;
    public final JoinCodeStore joinCodeStore;
    public final PasswordEncoderHolder passwordEncoderHolder;
    public final UserRepository userRepository;
    public final ReplyRepository replyRepository;
    public final BoardRepository boardRepository;
    public final CertificationService certificationService;
    public final ReplyService replyService;
    public final BoardService boardService;
    public final ManagerRepository managerRepository;
    public final ManagerService managerService;



    @Builder
    public TestContainer(SecurityUtils securityUtils) {
        this.mailSender = new FakeMailSender();
        this.passwordEncoderHolder = new FakePasswordEncoder();

        this.joinCodeStore = new FakeJoinCodeStore();
        this.managerRepository = new FakeManagerRepository();
        this.userRepository = new FakeUserRepository();
        this.replyRepository = new FakeReplyRepository();
        this.boardRepository = new FakeBoardRepository();

        this.certificationService = CertificationServiceImpl.builder()
                .mailSender(this.mailSender)
                .joinCodeStore(this.joinCodeStore)
                .build();
        this.replyService = ReplyServiceImpl.builder()
                .replyRepository(this.replyRepository)
                .userRepository(this.userRepository)
                .boardRepository(this.boardRepository)
                .securityUtils(securityUtils)
                .build();
        this.boardService = BoardServiceImpl.builder()
                .userRepository(this.userRepository)
                .boardRepository(this.boardRepository)
                .replyRepository(this.replyRepository)
                .securityUtils(securityUtils)
                .build();

        this.managerService = ManagerServiceImpl.builder()
                .managerRepository(this.managerRepository)
                .build();


    }
}