package com.example.demo.util

import java.io.UnsupportedEncodingException
import java.net.URLEncoder

class GCMVo {
    // android 로 보낼 정보
    var title = "Hello Baby"
        @Throws(UnsupportedEncodingException::class)
        set(title) {
            field = URLEncoder.encode(title, "UTF-8")
        }
    var msg = ""
        @Throws(UnsupportedEncodingException::class)
        set(msg) {
            field = URLEncoder.encode(msg, "UTF-8")
        }
    var typeCode = ""

    // push 결과 정보
    var regId: String? = null // regId
    var pushSuccessOrFailure: Boolean = false // 성공 여부
    var msgId = "" // 메시지 ID
    var errorMsg = "" // 에러메시지

}
 

