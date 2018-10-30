# HelloBabyBackend

Restful CRUD API using Kotlin, Spring Boot, Mysql, JPA and Hibernate.


FORMAT: 1A
HOST: http://106.10.46.76:8080/
 # Hello Baby
 ## Hello Baby [/]
<br>

 ### 유저 정보 등록 [POST /put_user]
 
 + Parameters
 
    + user (string) - 유저 선택 (Mom : M, Dad : D)
    + babyName (string) - 태명
    + babyWeek (string) - 임신 주차
    + phoneNum (String) - 핸드폰 번호 
    + mappingPhone (String ) - 아빠(D)의 경우만 엄마의 폰 번호 입력
    
 + Request (application/json)
 
         {
          "user": "M",
          "babyname": "딸기",
          "babyweek": "4",
          "phoneNum": "01012345678"
        }
        
 + Response 200 (application/json)
 
     + Body
             {
                "result": "Y"
            }
 ### 유저 정보 확인 [POST /get_user]
 
 + Parameters
 
      + phoneNum (String) - 핸드폰 번호 (01012345678 데이터있음)
      
 + Request (application/json)
 
         {
          "phoneNum": "01012345678"
        }
        
 + Response 200 (application/json)
 
     + Body
         {
          "user": "M",
          "babyname": "딸기",
          "babyweek": "4"
        }
    
 ### 심장박동수, 온습도 입력 [POST /put_heartbeat]
 
 + Parameters
 
      + phoneNum (String) - 핸드폰 번호 
      + heartbeat (String) - 박동수
      + temperature (String) - 온도
      + humidity (String) - 습도
      
 + Request (application/json)
 
         {
          "phoneNum": "01012345678",
          "heartbeat": "97",
          "temperature": "27",
          "humidity": "50"
        }
        
 + Response 200 (application/json)
 
     + Body
         {
          "result":"Y"
        }
        
 ### 심장박동수, 온습도 일별 평균 확인 [POST /get_heartbeat/avg]
 
 + Parameters
 
      + datetime (String) - format: YYYY-MM-dd (10/23 ~10/27 데이터 존재)
      + phoneNum (String) - 핸드폰 번호 (01012345678 데이터 존재)
      
 + Request (application/json)
 
         {
          "phoneNum": "01012345678"
          "datetime": "2018-10-27"
        }
        
 + Response 200 (application/json)
 
     + Body
         {
          "heartbeat":"97",
          "temperature": "27",
          "humidity": "50"
        }
    
 ### 심장박동수 주차별 전체평균 데이터 확인 [POST /get_heartbeat/avg/{week}]
 
 + Parameters
 
      + week (String) - 임신주차
      
 + Response 200 (application/json)
 
     + Body
         {
          "heartbeat_max":"180",
          "heartbeat_min":"120"
        }
    
 ### FCM registration key 저장 [POST /register_fcm_key]
 
+ Parameters

    + phoneNum (String) - 핸드폰 번호
    + registrationToken (String) - FCM client token
        
+ Request (application/json)
        
        {
            "phoneNum": "01012345678",
            "registrationToken": "registrationToken"
        }
        
+ Response 200 (application/json)

     + Body
    
        {
            "result": "Y"
        }
 ### FCM send tester [POST /send_fcm_message]
 
+ Parameters

        + message (String) - message
        + registrationToken (String) - FCM client token
        
+ Request (application/json)
        
        {
            "message": "MESSAGE",
            "registrationToken": "registrationToken"
        }
        
+ Response 200 (application/json)

     + Body
    
        {
            "result": "Y"
        }
        
 ### 미션수행 요청 by Mom [POST /request_mission]
 
 + Parameters
 
    + phoneNum (String) - 핸드폰 번호
    + mission (String) -mission
       
+ Request (application/json)
        
        {
            "phoneNum": "01012345678",
            "mission":"콩알이 아빠~ 좀전부터 딸기가 아른아른ㅜ 오늘 꼭 먹고시포요 ㅜㅜ"
        }
        
+ Response 200 (application/json)

     + Body
    
        {
            "result": "Y"
        }
        
 ### 미션수행 목록보기(전체) [GET /get_mission_list/{phoneNum}]
 
 + Parameters
 
    + phoneNum (String) - 핸드폰 번호(01012345678 데이터있음)
        
+ Response 200 (application/json)

     + Body
    
        {
            "result": "Y",
            "data":[
            {   
                "mission":"콩알이 아빠~ 좀전부터 딸기가 아른아른ㅜ 오늘 꼭 먹고시포요 ㅜㅜ",
                "missionId":"12"
            },
                {   
                "mission":"산책가고싶다~~ ㅎㅎ 날씨좋치 아빵??",
                "missionId":"13"
            },
             {   
                "mission":"아빠,, 나 좀 추운데? ㅎㅎ 문좀 닫아줘용",
                "missionId":"14"
            }
            ]
        }
 ### 미션수행 목록보기(단일) [GET /get_mission/{missionId}]
 
 + Parameters
 
    + missionId(String) - 미션 아이디
       
        
+ Response 200 (application/json)

     + Body
    
        {
                "mission":"산책가고싶다~~ ㅎㅎ 날씨좋치 아빵??",
                "missionId":"13"
        }
        
 ### 미션수행 완료 by Dad [GET /complete_mission/{missionId}]
 
 + Parameters
 
    + missionId(String) - 미션 아이디
       
+ Response 200 (application/json)

     + Body
    
        {
            "result": "Y"
        }
        
 ### 알림 목록 리스트 보기 [GET /get_notification_list/{phoneNum}]
 
 + Parameters
 
    + phoneNum (String) - 핸드폰 번호(01012345678 데이터 있음)
       
+ Response 200 (application/json)

     + Body
    
        {
            "result": "Y",
            "data":[
            {   
                "type":"B"
                "text":"아빠 굿밤~:D",
                "datetime":"2018-10-24 21:30"
            },
                { 
                "type":"M"
                "text":"아빠가 미션을 수행했어요!WOW",
                "datetime":"2018-10-25 19:45"
            },
             {   
                "type":"H"
                "text":"엄마 오늘 병원가는날~ 알지용?",
                "datetime":"2018-10-27 11:00"
                "place":"서울삼성병원"
            },
            {
                "type":"B"
                "text":"아빠 나 열나ㅠㅠ",
                "datetime":"2018-10-27 14:10"
            },
             { 
                "type":"M"
                "text":"아빠가 미션을 수행했어요!WOW",
                "datetime":"2018-10-28 20:05"
            },
            ]
        }
        
 ### FCM custom [POST /send_fcm_custom]
 
 + Parameters
 
    + missionId(String) - 미션 아이디
      
+ Request (application/json)
        
        {
            "phoneNum": "lg",
            "mission":"콩알이 아빠~ 좀전부터 딸기가 아른아른ㅜ 오늘 꼭 먹고시포요 ㅜㅜ",
            "type": "exciting, smile, sleeping
        }
        
 + Response 200 (application/json)
 
     + Body
    
        {
                "result":"Y"
        } 
