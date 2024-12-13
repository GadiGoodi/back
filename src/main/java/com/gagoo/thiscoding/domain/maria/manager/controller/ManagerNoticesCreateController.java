package com.gagoo.thiscoding.domain.maria.manager.controller;


import com.gagoo.thiscoding.domain.maria.manager.controller.port.ManagerService;
import com.gagoo.thiscoding.domain.maria.manager.domain.ManagerNoticesCreate;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/notices")
@Builder
public class ManagerNoticesCreateController {
    private final ManagerService managerService;

    //공지사항 작성
    @PostMapping
    public ResponseEntity<ManagerNoticesCreate> createNotice(@RequestBody ManagerNoticesCreate request) {
        managerService.createAdminNotices(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
