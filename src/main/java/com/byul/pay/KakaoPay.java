package com.byul.pay;

import com.byul.web.dto.request.pay.KakaoPayRequestDto;
import com.byul.web.dto.request.pay.KakaoPrepareRequestDto;
import com.byul.web.dto.response.pay.kakao.KakaoPayResponseDto;
import com.byul.web.dto.response.pay.kakao.KakaoPrePayResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class KakaoPay {

    @Value("${spring.pay.kakao.cid}")
    private String KAKAO_CID;

    @Value("${spring.pay.kakao.admin-key}")
    private String KAKAO_ADMIN_KEY;

    @Value(("${spring.pay.kakao.url}"))
    private String KAKAO_PREPAY_URL;

    @Value(("${spring.pay.kakao.approval-url}"))
    private String APPROVAL_URL;

    @Value(("${spring.pay.kakao.fail-url}"))
    private String FAIL_URL;

    @Value(("${spring.pay.kakao.cancel-url}"))
    private String CANCEL_URL;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public KakaoPrePayResponseDto prepare(KakaoPrepareRequestDto requestDto) {
        /**
         * 1. 결제 준비를 위한 URL 생성하기
         * 필수항목
         * cid 가맹점코드 (테스트는 TC0ONETIME)
         * partner_order_id 가맹점 주문번호
         * partner_user_id 가맹점 회원 id
         * item_name 상품명
         * quantity 상품 수량
         * total_amount 상품 총액
         * tax_free_amount 상품 비과세 금액
         * approval_url 결제 성공 시 redirect url
         * cancel_url 결제 취소 시 redirect url
         * fail_url 결제 실패 시 redirect url
         */
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("cid", KAKAO_CID);
        params.add("partner_order_id", requestDto.getOrderId());
        params.add("partner_user_id", requestDto.getMemberId());
        params.add("item_name", requestDto.getItemName());
        params.add("quantity", requestDto.getQuantity());
        params.add("total_amount", requestDto.getTotalAmount());
        params.add("tax_free_amount", 0);
        params.add("approval_url", APPROVAL_URL); //결제 대기 화면
        params.add("fail_url", FAIL_URL); //결제 실패 화면
        params.add("cancel_url", CANCEL_URL); //결제 취소 화면

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " +  KAKAO_ADMIN_KEY);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //2. 결제 요청할 URL 받기
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        KakaoPrePayResponseDto response = restTemplate.postForObject(KAKAO_PREPAY_URL, request, KakaoPrePayResponseDto.class);

        log.info("response.tid = " + response.getTid() + " , response.next_redirect_pc_url = " + response.getNext_redirect_pc_url());
        return response;
    }

    /**
     * 결제 요청하기
     * @param requestDto
     */
    public KakaoPayResponseDto pay(KakaoPayRequestDto requestDto) {
        String url = "https://kapi.kakao.com/v1/payment/approve";

        //조건에 맞는 요청 생성
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("cid", KAKAO_CID);
        params.add("tid", requestDto.getTid());
        params.add("partner_order_id", requestDto.getOrderId()); //결제 준비 API 요청과 일치해야 함
        params.add("partner_user_id", requestDto.getMemberId()); //결제 준비 API 요청과 일치해야 함
        params.add("pg_token", requestDto.getPgToken());
        //params.add("payload", "");
        //params.add("total_amount", requestDto.getTotalAmount()); //결제 준비 API 요청과 일치해야 함

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " +  KAKAO_ADMIN_KEY);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);

        //결제 요청
        KakaoPayResponseDto response = restTemplate.postForObject(url, request, KakaoPayResponseDto.class);

        log.info("KakaoPayResponseDto >>> " + response.toString());
        return response;
    }
}
