package kr.ac.duksung.minifrontapp

data class FcmNotificationResponse(
    val multicastId: Long, // 다중 대상 메시지 ID
    val success: Int,      // 성공한 메시지 수
    val failure: Int,      // 실패한 메시지 수
    val canonicalIds: Int, // 캐노니컬 ID 수
    val results: List<FcmMessageResult> // 메시지 결과 목록
)

data class FcmMessageResult(
    val messageId: String, // 메시지 ID
    val registrationId: String?, // 등록 ID (캐노니컬 메시지일 때만 포함)
    val error: String?    // 오류 메시지 (실패한 경우에만 포함)
)
