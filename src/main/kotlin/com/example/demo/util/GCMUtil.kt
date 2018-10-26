package com.example.demo.util

import com.google.android.gcm.server.Message
import com.google.android.gcm.server.Sender
import java.io.IOException
import java.util.*

class GCMUtil(reslist: List<String>, gcmVo: GCMVo) {

    companion object {
        internal val API_KEY = "서버 API KEY"
        private val MAX_SEND_CNT = 999 // 1회 최대 전송 가능 수

        // android 에서 받을 extra key (android app 과 동일)
        internal val TITLE_EXTRA_KEY = "TITLE"
        internal val MSG_EXTRA_KEY = "MSG"
        internal val TYPE_EXTRA_CODE = "TYPE_CODE"
    }

    internal var resList: List<String>? = null
    private val sender: Sender
    private var message: Message? = null

    var rtnList: ArrayList<GCMVo>

    init {
        sender = Sender(API_KEY)
        this.resList = reslist
        setMessage(gcmVo)
        rtnList = ArrayList()
        sendGCM()
    }

    private fun setMessage(gcmVo: GCMVo) {
        val builder = Message.Builder()
        builder.addData(TITLE_EXTRA_KEY, gcmVo.title)
        builder.addData(MSG_EXTRA_KEY, gcmVo.msg)
        builder.addData(TYPE_EXTRA_CODE, gcmVo.typeCode)
        message = builder.build()
    }

    private fun sendGCM() {
        if (resList!!.size > 0) {
            if (resList!!.size <= MAX_SEND_CNT) {
                sendMultivastResult(resList!!)
            } else {
                val resListTemp = ArrayList<String>()
                for (i in resList!!.indices) {
                    if ((i + 1) % MAX_SEND_CNT == 0) {
                        sendMultivastResult(resListTemp)
                        resListTemp.clear()
                    }
                    resListTemp.add(resList!![i])
                }
                // 최대건수 보내고 남은 것 보내기
                if (resListTemp.size != 0) {
                    sendMultivastResult(resListTemp)
                }
            }
        }

    }

    private fun sendMultivastResult(list: List<String>) {
        try {

            val multiResult = sender.send(message, list, 3)
            val resultList = multiResult.results


            for (i in resultList.indices) {
                val result = resultList[i]
                // 결과 셋팅
                val rtnGcmVo = GCMVo()
                rtnGcmVo.regId = list[i]
                rtnGcmVo.msgId = result.messageId
                rtnGcmVo.errorMsg = result.errorCodeName

                if (result.messageId != null) { // 전송 성공
                    rtnGcmVo.pushSuccessOrFailure = true
                } else { // 전송 실패
                    rtnGcmVo.pushSuccessOrFailure = false
                }
                rtnList.add(rtnGcmVo)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}
 
