package com.gagoo.thiscoding.domain.mock;

import com.gagoo.thiscoding.domain.maria.alarm.service.port.AlarmRepository;
import com.gagoo.thiscoding.domain.maria.bookmark.service.port.BookmarkRepository;
import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.CodeRoomService;
import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.InvitationService;
import com.gagoo.thiscoding.domain.maria.coderoom.controller.port.ParticipationService;
import com.gagoo.thiscoding.domain.maria.coderoom.service.CodeRoomServiceImpl;
import com.gagoo.thiscoding.domain.maria.coderoom.service.InvitationServiceImpl;
import com.gagoo.thiscoding.domain.maria.coderoom.service.ParticipationServiceImpl;
import com.gagoo.thiscoding.domain.maria.coderoom.service.port.CodeRoomRepository;
import com.gagoo.thiscoding.domain.maria.like.service.port.LikeRepository;
import com.gagoo.thiscoding.domain.maria.manager.controller.port.ManagerService;
import com.gagoo.thiscoding.domain.maria.manager.service.ManagerServiceImpl;
import com.gagoo.thiscoding.domain.maria.manager.service.port.ManagerRepository;
import com.gagoo.thiscoding.domain.maria.reply.controller.port.ReplyService;
import com.gagoo.thiscoding.domain.maria.reply.service.ReplyServiceImpl;
import com.gagoo.thiscoding.domain.maria.reply.service.port.ReplyRepository;
import com.gagoo.thiscoding.domain.maria.user.controller.port.CertificationService;
import com.gagoo.thiscoding.domain.maria.user.service.CertificationServiceImpl;
import com.gagoo.thiscoding.domain.maria.user.service.port.AuthCodeStore;
import com.gagoo.thiscoding.domain.maria.user.service.port.MailSender;
import com.gagoo.thiscoding.domain.maria.user.service.port.UserRepository;
import com.gagoo.thiscoding.domain.maria.usercoderoom.controller.port.UserCodeRoomService;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.UserCodeRoomServiceImpl;
import com.gagoo.thiscoding.domain.maria.usercoderoom.service.port.UserCodeRoomRepository;
import com.gagoo.thiscoding.domain.mongo.board.controller.port.BoardService;
import com.gagoo.thiscoding.domain.mongo.board.service.BoardServiceImpl;
import com.gagoo.thiscoding.domain.mongo.board.service.BoardViewServiceImpl;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardRepository;
import com.gagoo.thiscoding.domain.auth.service.port.PasswordEncoderHolder;
import com.gagoo.thiscoding.domain.auth.service.port.SecurityUtils;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardViewRepository;
import com.gagoo.thiscoding.domain.mongo.board.service.port.BoardViewService;
import com.gagoo.thiscoding.domain.mongo.code.service.port.CodeRepository;
import com.gagoo.thiscoding.global.common.uuid.service.port.UuidHolder;
import lombok.Builder;

public class TestContainer {

    public final MailSender mailSender;
    public final AuthCodeStore authCodeStore;
    public final PasswordEncoderHolder passwordEncoderHolder;
    public final UuidHolder uuidHolder;
    public final UserRepository userRepository;
    public final ReplyRepository replyRepository;
    public final BoardRepository boardRepository;
    public final BoardViewRepository boardViewRepository;
    public final ManagerRepository managerRepository;
    public final AlarmRepository alarmRepository;
    public final CodeRepository codeRepository;
    public final CodeRoomRepository codeRoomRepository;
    public final UserCodeRoomRepository userCodeRoomRepository;
    public final BookmarkRepository bookmarkRepository;
    public final LikeRepository likeRepository;
    public final CertificationService certificationService;
    public final ReplyService replyService;
    public final BoardService boardService;
    public final BoardViewService boardViewService;
    public final ManagerService managerService;
    public final InvitationService invitationService;
    public final CodeRoomService codeRoomService;
    public final ParticipationService participationService;
    public final UserCodeRoomService userCodeRoomService;

    @Builder
    public TestContainer(SecurityUtils securityUtils) {
        this.mailSender = new FakeMailSender();
        this.authCodeStore = new FakeAuthCodeStore();
        this.passwordEncoderHolder = new FakePasswordEncoder();
        this.uuidHolder = new FakeUuidHolder("test-uuid");

        this.userRepository = new FakeUserRepository();
        this.replyRepository = new FakeReplyRepository();
        this.boardRepository = new FakeBoardRepository();
        this.boardViewRepository = new FakeBoardViewRepository();
        this.managerRepository = new FakeManagerRepository();
        this.alarmRepository = new FakeAlarmRepository();
        this.codeRepository = new FakeCodeRepository();
        this.codeRoomRepository = new FakeCodeRoomRepository();
        this.userCodeRoomRepository = new FakeUserCodeRoomRepository();
        this.bookmarkRepository = new FakeBookmarkRepository();
        this.likeRepository = new FakeLikeRepository();

        this.certificationService = CertificationServiceImpl.builder()
                .mailSender(this.mailSender)
                .authCodeStore(this.authCodeStore)
                .build();
        this.replyService = ReplyServiceImpl.builder()
                .replyRepository(this.replyRepository)
                .userRepository(this.userRepository)
                .boardRepository(this.boardRepository)
                .securityUtils(securityUtils)
                .build();
        this.boardViewService = BoardViewServiceImpl.builder()
                .boardRepository(this.boardRepository)
                .boardViewRepository(this.boardViewRepository)
                .build();
        this.boardService = BoardServiceImpl.builder()
                .userRepository(this.userRepository)
                .boardRepository(this.boardRepository)
                .replyRepository(this.replyRepository)
                .boardViewService(this.boardViewService)
                .bookmarkRepository(this.bookmarkRepository)
                .likeRepository(this.likeRepository)
                .securityUtils(securityUtils)
                .build();
        this.managerService = ManagerServiceImpl.builder()
                .managerRepository(this.managerRepository)
                .build();
        this.invitationService = InvitationServiceImpl.builder()
            .userRepository(this.userRepository)
            .codeRoomRepository(this.codeRoomRepository)
            .alarmRepository(this.alarmRepository)
            .userCodeRoomRepository(userCodeRoomRepository)
            .securityUtils(securityUtils)
            .build();
        this.codeRoomService = CodeRoomServiceImpl.builder()
                .userRepository(this.userRepository)
                .userCodeRoomRepository(this.userCodeRoomRepository)
                .codeRoomRepository(this.codeRoomRepository)
                .codeRepository(this.codeRepository)
                .securityUtils(securityUtils)
                .uuidHolder(this.uuidHolder)
                .build();
        this.participationService = ParticipationServiceImpl.builder()
                .codeRoomRepository(this.codeRoomRepository)
                .userCodeRoomRepository(this.userCodeRoomRepository)
                .userRepository(this.userRepository)
                .securityUtils(securityUtils)
                .build();
        this.userCodeRoomService = UserCodeRoomServiceImpl.builder()
                .userRepository(this.userRepository)
                .codeRoomRepository(this.codeRoomRepository)
                .userCodeRoomRepository(this.userCodeRoomRepository)
                .securityUtils(securityUtils)
                .build();
    }
}