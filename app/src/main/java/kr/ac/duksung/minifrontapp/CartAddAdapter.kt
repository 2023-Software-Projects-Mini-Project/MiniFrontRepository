package kr.ac.duksung.minifrontapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_menu.view.*

class CartAddAdapter (var context: Context, var itemList : MutableList<MenuClass>) : RecyclerView.Adapter<CartAddAdapter.ViewHolder>()  {

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
        holder.bind(item, context)

    }

    override fun getItemCount(): Int {          // 생성자로 부터 받은 데이터의 개수를 측정
        return itemList.size
    }



    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        var view : View = v

        fun bind(item: MenuClass, context: Context){

            //val menuImageID = context.resources.getIdentifier(item.menuimage, "drawble", context.packageName)
            //view.MenuImage.setImageResource(menuImageID)
            Glide.with(view).load(item.menuimage).into(view.MenuImage)


            view.MenuName.text = item.menuname
            view.MenuPrice.text = item.menuprice
            view.MenuCount.text = item.menucount
        }
    }


}