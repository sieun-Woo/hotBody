# OAuth2 카카오 로그인

- 1. kakao developers 애플리케이션 등록
    1. 동의 항목 설정
    2. 서비스 url, 리다이렉트 url등록

### 카카오 로그인 과정

![Untitled](OAuth2%20%E1%84%8F%E1%85%A1%E1%84%8F%E1%85%A1%E1%84%8B%E1%85%A9%20%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB%200c8d76fa1e5b4d65acbdd21ef3ca96c9/Untitled.png)

출처 : [https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code](https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#request-code)

1. 인가 코드 받기
    1. API키와 리다이렉트 URI을 포함한 GET요청을 카카오 Auth 서버로 보낸다.
    2. 카카오 로그인 후 1.에서 설정한 동의 항목을 동의한다.
        1. 클라이언트에 유효한 카카오계정 세션이 있거나, 카카오톡 인앱 브라우저에서의 요청인 경우 4단계로 넘어간다.
    3. 리다이렉트 URI로 인가 코드를 발급한다.
    
    ex) https://www.hotbody.store/index.html?**code=IkBW7ePWOWSJ3HcHWqY8CjyQEU1zbfVEH0Wsyl82a3p-1CuL9ldve2D1J17Li57ItStUnAoqJV**
    
2. 인가 코드로  카카오 액세스 토큰을 발급한다.
3. 액세스 토큰으로 사용자 정보를 가져온다.
4. 사용자 정보(이메일)가 기존 유저에 데이터에 존재하면 유저 데이터에 카카오 정보를 추가하고,
존재하지 않으면 카카오 정보를 통해 가입한다.
5. 로그인 완료