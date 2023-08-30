package com.byul.web;

import com.byul.domain.PlatformType;
import com.byul.service.PayService;
import com.byul.web.dto.request.pay.KakaoPayRequestDto;
import com.byul.web.dto.request.pay.KakaoPrepareRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PayController {

    private final PayService payService;

    @GetMapping("/api/v1/order/{memberId}/pay/{platform}/prepare")
    public Object prepare(
            @PathVariable(name = "memberId") Long memberId,
            @PathVariable(name = "platform") String platform,
            KakaoPrepareRequestDto requestDto
    ) throws IOException {
        PlatformType platformType = PlatformType.valueOf(platform.toUpperCase());
        requestDto.addMember(memberId);
        
        //프론트에서 redirect_url 호출(팝업창)
        return payService.prepare(platformType, requestDto);
    }

    @PostMapping("/api/v1/order/{memberId}/pay/{platform}")
    public String pay(
            @PathVariable(name = "memberId") Long memberId,
            @PathVariable(name = "platform") String platform,
            KakaoPayRequestDto requestDto
    ) {
        PlatformType platformType = PlatformType.valueOf(platform.toUpperCase());
        requestDto.addMember(memberId);
        return payService.pay(platformType, requestDto);
    }

}
