package com.byul.web;

import com.byul.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/api/v1/image/{attachFileId}")
    public ResponseEntity<?> view(
            @PathVariable(name = "attachFileId") Long attachFileId
    ) {
        return fileService.view(attachFileId);
    }
}
