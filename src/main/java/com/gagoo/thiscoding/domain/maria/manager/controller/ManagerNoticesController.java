package com.gagoo.thiscoding.domain.maria.manager.controller;

import com.gagoo.thiscoding.domain.maria.manager.controller.port.ManagerService;
import com.gagoo.thiscoding.domain.maria.manager.controller.response.ManagerNoticesList;
import com.gagoo.thiscoding.domain.maria.manager.controller.response.ManagerNoticesPost;
import com.gagoo.thiscoding.domain.maria.manager.controller.response.ManagerNoticesUpdate;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/notices")
@Builder
public class ManagerNoticesController {
    private final ManagerService managerService;


    //공지사항 목록 조회
    @GetMapping
    public ResponseEntity<Page<ManagerNoticesList>> getNoticesAll(Pageable pageable) {
        return ResponseEntity
                .ok()
                .body(managerService.getAllManagerNotices(pageable).map(ManagerNoticesList::from));
    }

    //공지사항 작성
    @PostMapping
    public ResponseEntity<ManagerNoticesPost> createNotice(@RequestBody ManagerNoticesPost request) {
        managerService.createAdminNotices(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //공지사항 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteManagerNotices(@PathVariable Long id) {
        managerService.deleteManagerNotices(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //공지사항 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateManagerNotices(@PathVariable Long id,
                                                  @RequestBody ManagerNoticesUpdate request) {

        managerService.updateAdminNotices(id, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
