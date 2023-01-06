## (정리중입니다)
1. 프로젝트 설명
    1. 리부트 팀 게시판 (추후 변경)
    2. 스파르타코딩클럽 내일배움캠프 4기 Spring 심화 팀프로젝드입니다.
    3. 팀원
        1. 김정훈
            1. Dto, Repository 작성
        2. 이지섭
            1. Entity, Exception 작성
        3. 김현중
            1. 게시글 기능 구현
        4. 이신희
            1. 유저 기능 구현
        5. 유성재
            1. 댓글 기능 구현
        6. 공동 작업
            1. Spring Security, JWT 적용
2. 정보
    1. 개발환경
        - Spring Boot (2.7.6)
        - Spring
        - Spring Security (JWT)
        - JPA
        - JAVA(JDK11)
        - H2
    2. 개발 기간
        - 22/12/30 ~ 22/01/05 (7일)
    3. api
        
        
        | 기능 | API URL | Method | Request Header | Request | Response | Response Header |
        | --- | --- | --- | --- | --- | --- | --- |
        | 회원 가입 | /api/users/signup | POST |  | { 
        ”username” : “bin1234 “,
         “password” : “ 비밀번호“
        } | {
        "msg": "회원가입 성공",
        "statusCode": 200
        } |  |
        | 로그인 | /api/users/login | POST |  | { 
        ”username” : “bin1234 “,
         “password” : “ 비밀번호“
        } | {
        "msg": "로그인 성공",
        "statusCode": 200
        } | Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiaW4xMjM0IiwiZXhwIjoxNjY5ODcwNDUyLCJpYXQiOjE2Njk4NjY4NTJ9.mm8wgaV8M70hidhPX4Ut6UONZGaxjA1KnOJT1mO59Xc |
        | 전체 게시글 조회 | /api/posts | GET |  |  | { "postList": 
        [{
        "id": 1,
        "title": "게시글1",
        "content": "내용1",
        "username": "bin1234",
        "createdAt": "2022-12-01T12:52:06.729608",
        "modifiedAt": "2022-12-01T12:52:06.729608"
        },
        {
        "id": 2,
        "title": "게시글2",
        "content": "내용2",
        "username": "bin1234",
        "createdAt": "2022-12-01T12:52:10.566505",
        "modifiedAt": "2022-12-01T12:52:10.566505"
        }]
        } |  |
        | 게시글 작성 | /api/posts | POST | Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiaW4xMjM0IiwiZXhwIjoxNjY5ODcwNDUyLCJpYXQiOjE2Njk4NjY4NTJ9.mm8wgaV8M70hidhPX4Ut6UONZGaxjA1KnOJT1mO59Xc | {
        ”title” : “게시글5”,
        ”content” : 내용5”,
        ”username” : “bin1234”
        } | {
        "id": 5,
        "title": "게시글5",
        "content": "내용5",
        "username": "bin1234",
        "createdAt": "2022-12-01T12:56:36.821474",
        "modifiedAt": "2022-12-01T12:56:36.821474"
        } |  |
        | 단건 게시글 조회 | /api/posts/{id} | GET |  | {
        ”id” : 1
        } | {
        "id": 1,
        "title": "게시글1",
        "content": "내용1",
        "username": "bin1234",
        "createdAt": "2022-12-01T12:52:06.729608",
        "modifiedAt": "2022-12-01T12:52:06.729608"
        } |  |
        | 게시글 수정 | /api/posts/{id} | PUT | Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiaW4xMjM0IiwiZXhwIjoxNjY5ODcwNDUyLCJpYXQiOjE2Njk4NjY4NTJ9.mm8wgaV8M70hidhPX4Ut6UONZGaxjA1KnOJT1mO59Xc | {
        ”id” : 5,
        ”title” : “게시글4 수정”,
        ”content” : “내용4 수정”
        }
         | {
        "id": 5,
        "title": "게시글4 수정",
        "content": "내용4 수정",
        "username": "bin1234",
        "createdAt": "2022-12-01T12:56:36.821474",
        "modifiedAt": "2022-12-01T12:56:36.821474" |  |
        | 게시글 삭제 | /api/posts/{id} | DELETE | Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiaW4xMjM0IiwiZXhwIjoxNjY5ODcwNDUyLCJpYXQiOjE2Njk4NjY4NTJ9.mm8wgaV8M70hidhPX4Ut6UONZGaxjA1KnOJT1mO59Xc | {
        ”id” : 1
        } | {
        "msg": "게시글 삭제 성공",
        "statusCode": 200
        } |  |
        | 댓글 작성 | /api/comments/{id} | POST | Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiaW4xMjM0IiwiZXhwIjoxNjY5ODcwNDUyLCJpYXQiOjE2Njk4NjY4NTJ9.mm8wgaV8M70hidhPX4Ut6UONZGaxjA1KnOJT1mO59Xc | {
        ”comment” : “댓글내용”
        } | {
        ”id” : 1,
        ”content” : “댓글내용”
        ”username” : "bin1234", 
        ”like” : 0,
        "createdAt": "2022-12-01T12:56:36.821474",
        "modifiedAt": "2022-12-01T12:56:36.821474"
        } |  |
        | 댓글 수정 | /api/comments/{id}/{commentsId} | PUT |  |  | {
        ”id” : 1,
        ”content” : “내용1 삭제 내용2”
        ”username” : "bin1234", 
        ”like” : 0,
        "createdAt": "2022-12-01T12:56:36.821474",
        "modifiedAt": "2022-12-01T12:56:36.821474"
        } |  |
        | 댓글 삭제 | /api/comments/{id}/{commentsId} | DELETE |  |  | {
        "msg": "댓글 삭제 성공",
        "statusCode": 200
        } |  |
        | 게시글 좋아요 | /api/posts/like/{id} | POST |  |  | {
        "msg": "좋아요를 눌렀습니다.",
        "statusCode": 200
        } |  |
        | 댓글 좋아요 | /api/comments/like/{commentsId} | POST |  |  | {
        "msg": "좋아요를 눌렀습니다.",
        "statusCode": 200
        } |  |
    4. erd
    
    ![9조 (3).png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/8d6e6253-6fd0-4fc3-a196-726801f4c7b4/9%EC%A1%B0_(3).png)
