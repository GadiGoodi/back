package com.gagoo.thiscoding.domain.maria.manager.controller;

import com.gagoo.thiscoding.domain.maria.manager.controller.port.ManagerService;
import com.gagoo.thiscoding.domain.maria.manager.controller.response.ManagerNoticesDetail;
import com.gagoo.thiscoding.domain.maria.manager.controller.response.ManagerNoticesList;
import com.gagoo.thiscoding.domain.maria.manager.domain.ManagerNoticesUpdate;
import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import com.gagoo.thiscoding.global.common.response.ApiResponse;
import com.gagoo.thiscoding.global.paging.aop.ConvertToOneBase;
import com.gagoo.thiscoding.global.security.aop.AuthorizationRequired;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/notices")
@Builder
public class ManagerNoticesController {
    private final ManagerService managerService;

    //공지사항 목록 조회
    @GetMapping
    @ConvertToOneBase
    public ApiResponse<Page<ManagerNoticesList>> getNoticesAll(Pageable pageable) {

        return ApiResponse
                .ok(managerService.getAllManagerNotices(pageable)
                        .map(ManagerNoticesList::from), "공지사항 목록 조회 성공");
    }

    //공지사항 상세 조회
    @GetMapping("/{id}")
    public ApiResponse<ManagerNoticesDetail> getNoticesDetail(@PathVariable Long id) {
        return ApiResponse
                .ok(ManagerNoticesDetail.from(managerService.getNotices(id)), "공지사항 상세 조회 성공");
    }

    //공지사항 수정
    @PatchMapping("/{id}")
    @AuthorizationRequired(value = {Role.ADMIN})
    public ApiResponse<?> updateManagerNotices(@PathVariable Long id, @RequestBody ManagerNoticesUpdate request) {

        managerService.updateAdminNotices(id, request);
        return ApiResponse.ok(null, "공지사항 수정 완료");
    }

    //공지사항 삭제
    @DeleteMapping("/{id}")
    @AuthorizationRequired(value = {Role.ADMIN, Role.USER})
    public ApiResponse<?> deleteManagerNotices(@PathVariable Long id) {
        managerService.deleteManagerNotices(id);
        return ApiResponse.ok(null, "공지사항 삭제 완료");
    }

}
