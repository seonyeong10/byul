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
    
    ● 개발기간 : 2023.07 ~ 
    
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
<br/>

#### 회원 관련 API

|기능|Method|URL|Parameter|Return|
|----|-----|----------|----------|----------|
|소셜 로그인 페이지 호출|GET|/api/v1/login/oauth2/code/{platform}||redirectURL|
|소셜 로그인|GET|/api/v1/login/oauth2/code/{platform}/callback|?code={code}|JwtToken|
|JWT 토큰 발급|POST|/api/v1/login/reissue||JwtToken|
|로그아웃|POST|/api/v1/logout|||

<br/>

#### 상품 관련 API

|기능|Method|URL|Parameter|Return|
|----|-----|----------|----------|----------|
|전체 조회|GET|/api/v1/menus|?page={page}&sort={sort}|MenuListResponseDto|
|카테고리별 조회|GET|/api/v1/menus/{parentCategoryName}|?page={page}&sort={sort}|MenuListResponseDto|
|상세 조회|GET|/api/v1/menus/{parentCategory}/{childCategory}/{menuId}||MenuResponseDto|

<br/>

#### 장바구니 관련 API

|기능|Method|URL|Parameter|Return|
|----|-----|----------|----------|----------|
|장바구니 추가|POST|/api/v1/my/{memberId}/cart|||
|장바구니 전체 조회|GET|/api/v1/my/{memberId}/cart||List<CartResponseDto>|
|장바구니 전체 삭제|DELETE|/api/v1/my/{memberId}/cart/delete/all|||
|장바구니 선택 삭제|DELETE|/api/v1/my/{memberId}/cart/delete|?cartIds={cartIds}||

<br/>

#### 주문 관련 API

|기능|Method|URL|Parameter|Return|
|----|-----|----------|----------|----------|
|주문하기|POST|/api/v1/order/{memberId}||ok|
|주문 전체 조회|GET|/api/v1/my/{memberId}/history||List<OrderHistroyResDto>|
|주문 상세 조회|GET|/api/v1/my/{memberId}/history/{orderId}||OrderHistroyResDto|
|주문 취소|PUT|/api/v1/my/{memberId}/history/{orderId}|||

<br/>

#### 결제 관련 API

|기능|Method|URL|Parameter|Return|
|----|-----|----------|----------|----------|
|결제 준비하기|GET|/api/v1/order/{memberId}/pay/{platform}|?orderId={orderId}&itemName={itemName}&quantity={quantity}&totalAmount={totalAmount}|ok|
|결제하기|POST|/api/v1/order/{memberId}/pay/{platform}|||


<br/>
<br/>

> ### 구동 화면
<br/>

- 홈
  <br/>
  ![main_animation](https://github.com/seonyeong10/banjjok/assets/78454631/3414ac56-77ad-4ff5-beaf-a43e1e2e2f10)
  
- 로그인
  <br/>
  ![login_animation3](https://github.com/seonyeong10/banjjok/assets/78454631/6fd9651d-1404-4031-947b-c27acef84b57)

- 주문·결제
  <br/>
  ![pay_animation3](https://github.com/seonyeong10/banjjok/assets/78454631/434b48f4-20ac-44e8-85fe-19f97ad5a7fe)
