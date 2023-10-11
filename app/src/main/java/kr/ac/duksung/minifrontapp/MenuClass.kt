package kr.ac.duksung.minifrontapp

import com.google.firebase.database.Exclude

data class MenuClass(
    var menuName: String,
    var menuPrice: String,
    var menuCount: Int,
    //var menuimage: String
    ){


    @Exclude
    fun toMap() : HashMap<String, Any> {
        // 해시맵 만들기
        val result : HashMap<String, Any> = HashMap()

        // 첫번째 인자의 스트링 부분인  objectID는 objectId와 매칭된다.
        result["menuName"] = menuName
        result["menuPrice"] = menuPrice
        result["menuCount"] = menuCount
        //result["menuimage"] = menuimage

        return result   // 파이어베이스의 DB에 저장 가능한 자료형으로 변환 완료
    }
}