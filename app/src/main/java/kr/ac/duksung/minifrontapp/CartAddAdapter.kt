package kr.ac.duksung.minifrontapp

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.row_menu.view.*
import kotlinx.android.synthetic.main.activity_cart_main.*
import kotlinx.coroutines.flow.count


class CartAddAdapter: RecyclerView.Adapter<CartAddAdapter.ViewHolder>()  {

    var itemList = ArrayList<MenuClass>()

    private lateinit var auth: FirebaseAuth
    private val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private val categoriesRef = db.getReference("MenuName")
    private val cartRef = db.getReference("Cart")

    val cartActivity = CartActivity.getInstance()

    // Adapter에서 사용할 ViewHolder를 설정
    // LayoutInflater를 이용해서 row_menu.xml 정보를 가져옴 (inflate는 xml를 객체화 함)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAddAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.row_menu, parent, false)
        return ViewHolder(inflatedView)
    }

    // viewHolder의 apply 함수를 이용해서 bind 함수를 호출
    // -> 각각의 데이터를 row_friends를 사용하는 ViewHolder에 적용
    override fun onBindViewHolder(holder: CartAddAdapter.ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {          // 생성자로 부터 받은 데이터의 개수를 측정
        return itemList.size
    }



    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        var view : View = v
        var count : Int = view.MenuCount.text.toString().toInt()


        init {
            // 삭제 버튼이 클릭되었을 때
            view.bt_DeleteMenu.setOnClickListener{
                // bt_DeleteMenu를 클릭할 때 실행할 코드
                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {

                    itemList.removeAt(position)     // 아이템을 itemList에서 삭제(안드로이드 화면 상에만 반영)
                    notifyItemRemoved(position)     // RecyclerView에 변경사항을 알림

                    deleteMenuFromDB(view.MenuName.text as String)
                    Log.d("CardAddAdapter", "지울 메뉴 이름 ${view.MenuName.text as String}")
                }
            }

            // 수량 빼기 버튼 눌렸을때
            view.bt_sub.setOnClickListener{
                count = view.MenuCount.text.toString().toInt()
                Log.d("CardAddAdapter", "get $count")

                if(count == 1){                                     // 수량 0개 이하로는 안 내려가도록
                    Log.d("CardAddAdapter", "if $count")
                    Toast.makeText(this.view.context, "수량 0개는 안됩니다.", Toast.LENGTH_SHORT).show()
                }
                else{
                    Log.d("CardAddAdapter", "else $count")
                    count = count - 1
                    view.MenuCount.text = count.toString()
                    changeMenuCountFromDB(view.MenuName.text as String, count)
                }
            }

            // 수량 더하기 버튼 눌렸을 때
            view.bt_add.setOnClickListener{
                count = view.MenuCount.text.toString().toInt()
                count = count + 1
                view.MenuCount.text = count.toString()
                changeMenuCountFromDB(view.MenuName.text as String, count)
            }
        }

        fun bind(item: MenuClass){

            var name = item.menuName
            view.MenuName.text = item.menuName
            view.MenuPrice.text = item.menuPrice
            view.MenuCount.text = item.menuCount.toString()


            // 1. Firebase Storage 관리 객체 얻어오기
            val firebaseStorage = FirebaseStorage.getInstance()
            // 2. 최상위 노드 참조 객체 얻어오기
            val rootRef = firebaseStorage.reference
            // 메뉴 이미지 띄우기
            var image : String
            categoriesRef.child("$name").get().addOnSuccessListener {
                image = it.child("menuImage").getValue(String::class.java).toString()
                rootRef.child("$image").downloadUrl.addOnSuccessListener { uri ->
                    // 다운로드 URL이 파라미터로 전달되어 옴.
                    Glide.with(view).load(uri).into(view.MenuImage)
                }
            }
        }

    }

    fun deleteMenuFromDB(MENUNAME:String){
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userUid = currentUser!!.uid

        Log.e("test", "deleteMenuFromDB 함수 들어옴")

        //val count = cartRef.child(userUid).snapshots

        cartRef.child(userUid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val nodeCount = dataSnapshot.childrenCount.toInt()       // nodeCount 변수에 해당 경로의 노드 개수가 저장

                Log.d("NodeCount", "Node count: $nodeCount")

                if(nodeCount == 0){                           // 파이어베이스는 자식노드가 없으면 상위노드가 파괴됨.
                    val giveEmptyList: HashMap<String, Any> = HashMap()
                    giveEmptyList[userUid] = ""
                    cartRef.updateChildren(giveEmptyList)
                }
                else
                    cartRef.child(userUid).child("$MENUNAME").removeValue()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러 처리
                Log.e("NodeCount", "Error getting node count: ${databaseError.message}")
            }
        })

    }

    fun changeMenuCountFromDB(MENUNAME:String, MENUCOUNT:Int){
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val userUid = currentUser!!.uid

        val updateCount: HashMap<String, Any> = HashMap()       // updateChildren은 인스턴스로 해쉬맵만 받음
        updateCount["menuCount"] = MENUCOUNT                    // key: menuCount, value: oldcount로 updateCount에 저장

        cartRef.child(userUid).child("$MENUNAME").updateChildren(updateCount)
    }


}