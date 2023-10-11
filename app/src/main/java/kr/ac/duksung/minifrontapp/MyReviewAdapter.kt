package kr.ac.duksung.minifrontapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.before_wrote_review.view.*
import kotlinx.android.synthetic.main.review_list.view.*
import kotlinx.android.synthetic.main.review_list.view.message

class MyReviewAdapter: RecyclerView.Adapter<MyReviewAdapter.ViewHolder>() {

    var itemList = ArrayList<MyReviewClass>()

    // Adapter에서 사용할 ViewHolder를 설정
    // LayoutInflater를 이용해서 before_wrote_review.xml 정보를 가져옴 (inflate는 xml를 객체화 함)
    // inflatedView를 사용한 FriendsRowViewHolder를 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.before_wrote_review, parent, false)
        return ViewHolder(inflatedView);
    }

    // viewHolder의 apply 함수를 이용해서 bind 함수를 호출
    // -> 각각의 데이터를 before_wrote_review.xml를 사용하는 ViewHolder에 적용
    override fun onBindViewHolder(holder: MyReviewAdapter.ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {          // 생성자로 부터 받은 데이터의 개수를 측정
        return itemList.size
    }


    inner class ViewHolder(inflatedView: View) : RecyclerView.ViewHolder(inflatedView){
        var view : View = inflatedView

        fun bind(item: MyReviewClass){
            view.menu.text = item.menu
            view.rate.rating = item.rating
            view.date.text = item.date
            view.message.text = item.contents
        }
    }

}