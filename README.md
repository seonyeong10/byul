<!-- import pandas as pd -->
## Byul
개인 프로젝트 Back-End Repository
<br/><br/><br/>

> ### 프로젝트 특징
    ● React, SpringBoot 기반한 카페 웹페이지
    ● 프론트 엔드와 백엔드를 분리하여 프로젝트 개발
    ● 회원가입과 로그인, 주문 및 결제에 대한 CRUD 중점으로 구현
      -로그인은 JWT방식으로 구현
    ● RestAPI 방식의 CRUD
      -상품의 기본순/인기순/최신순 조회
      -상품 옵션 선택 후 장바구니 담기
      -상품 주문하기 및 주문 내역 조회
    
<br/>
<br/>

> ### 개요
    ● 명    칭 : byul
    
    ● 개발인원 : 1명
    
    ● 개발기간 : 2023.06 ~ 2023.08
    
    ● 주요기능
      -카테고리별 상품 조회
      -회원가입과 로그인
      -장바구니 담기, 장바구니 조회, 수정
      -주문 및 결제하기
      -주문 히스토리 조회
    
    ● 개발환경
      - Java17 / SpringBoot3.1.1 / H2
      - JPA, Querydsl
      - Redis
      - lombok, spring security
      - jwt
    
    ● 형상관리 : git
    
<br/>
<br/>

> ### 테이블 설계

![JLseMztm8AkSFDvHB](https://github.com/seonyeong10/byul/assets/78454631/681f1f54-8091-443d-bfc5-0c993b7fd625)

<br/>
<br/>

> ### API 설계
![회원](https://github.com/seonyeong10/byul/assets/78454631/4862d635-563c-426e-9d5e-83853f28a671)

![상품](https://github.com/seonyeong10/byul/assets/78454631/2b2e52ee-107b-4315-a9d1-b44313c7f968)

![장바구니](https://github.com/seonyeong10/byul/assets/78454631/d4d854c7-0af8-4dc4-b17d-dff6efed037a)

![주문](https://github.com/seonyeong10/byul/assets/78454631/6f32c911-a3ee-4d03-89a7-8519a96a58c7)
