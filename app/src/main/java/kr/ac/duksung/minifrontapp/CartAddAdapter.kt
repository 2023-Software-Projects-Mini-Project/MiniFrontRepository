package kr.ac.duksung.minifrontapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.menu_review.*
import kotlinx.android.synthetic.main.row_menu.view.*

class CartAddAdapter: RecyclerView.Adapter<CartAddAdapter.ViewHolder>()  {

    var itemList = ArrayList<MenuClass>()
    private lateinit var auth: FirebaseAuth
    private val db = Firebase.database("https://testlogin2-a82d6-default-rtdb.firebaseio.com/")
    private val categoriesRef = db.getReference("MenuName")
    private val cartRef = db.getReference("Cart")



    // Adapter에서 사용할 ViewHolder를 설정
    // LayoutInflater를 이용해서 row_friends.xml 정보를 가져옴 (inflate는 xml를 객체화 함)
    // inflatedView를 사용한 FriendsRowViewHolder를 반환
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
        
        private val cartActivity = CartActivity.getInstance()       // CartActivity에서 객체 가져오기

        init {
            view.bt_DeleteMenu.setOnClickListener{
                // bt_DeleteMenu를 클릭할 때 실행할 코드
                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {

                    itemList.removeAt(position)     // 아이템을 itemList에서 삭제


                    notifyItemRemoved(position)     // RecyclerView에 변경사항을 알림

                    //val menuToDelete = itemList[position]
                    //cartActivity?.deleteMenuFromFirebase(menuToDelete)        // 이거 쓰면 앱 꺼짐.

                }
            }

            view.bt_sub.setOnClickListener{             // 빼기 버튼 눌렸을때
                count = view.MenuCount.text.toString().toInt()
                Log.d("CardAddAdapter", "get $count")

                if(count == 1){                                     // 수량 0개 이하로는 안 내려가도록
                    Log.d("CardAddAdapter", "if $count")
                    Toast.makeText(this.view.context, "수량 0개는 안됩니다.", Toast.LENGTH_SHORT).show()
                }
                else{
                    Log.d("CardAddAdapter", "else start $count")
                    count = count - 1
                    view.MenuCount.text = count.toString()
                    Log.d("CardAddAdapter", "else end $count")
                }
            }

            view.bt_add.setOnClickListener{             // 더하기 버튼 눌렸을때
                count = view.MenuCount.text.toString().toInt()
                count = count + 1
                view.MenuCount.text = count.toString()
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


}