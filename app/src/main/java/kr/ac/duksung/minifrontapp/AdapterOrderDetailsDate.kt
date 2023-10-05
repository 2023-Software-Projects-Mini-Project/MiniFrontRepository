package kr.ac.duksung.minifrontapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.orderdetails_orders2.view.*
import kotlinx.android.synthetic.main.row_menu.view.*

class AdapterOrderDetailsDate (var itemList : MutableList<OrderDetailsClass>) : RecyclerView.Adapter<AdapterOrderDetailsDate.ViewHolder>()  {

    // Adapter에서 사용할 ViewHolder를 설정
    // LayoutInflater를 이용해서 orderdetails_orders2.xml 정보를 가져옴 (inflate는 xml를 객체화 함)
    // inflatedView를 사용한 FriendsRowViewHolder를 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterOrderDetailsDate.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.orderdetails_orders2, parent, false)
        return ViewHolder(inflatedView);
    }

    // viewHolder의 apply 함수를 이용해서 bind 함수를 호출
    // -> 각각의 데이터를 orderdetails_orders2를 사용하는 ViewHolder에 적용
    override fun onBindViewHolder(holder: AdapterOrderDetailsDate.ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {          // 생성자로 부터 받은 데이터의 개수를 측정
        return itemList.size
    }



    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v){
        var view : View = v


        init {

            view.order1_orderdetailButton.setOnClickListener{
                val intent = Intent(view.context, OrderDetailsDetail::class.java)
                view.context.startActivity(intent)
            }

            view.review_writeButton.setOnClickListener{
                val intent = Intent(view.context, ReviewWrite::class.java)
                view.context.startActivity(intent)
            }
        }

        fun bind(item: OrderDetailsClass){
            view.order_detail_dates.text = item.date
            Glide.with(view).load(item.menuimage).into(view.imageView)
            view.order1_text.text = item.menuname
            view.order1_price.text = item.sumprice
        }
    }


}