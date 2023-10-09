package kr.ac.duksung.minifrontapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.review_list.view.*

class TotalReviewAdapter: RecyclerView.Adapter<TotalReviewAdapter.ViewHolder>() {

    var itemList = ArrayList<TotalReviewClass>()
    var totalRating : Float = 0.0f

    // Adapter에서 사용할 ViewHolder를 설정
    // LayoutInflater를 이용해서 review_list.xml 정보를 가져옴 (inflate는 xml를 객체화 함)
    // inflatedView를 사용한 FriendsRowViewHolder를 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TotalReviewAdapter.ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.review_list, parent, false)
        return ViewHolder(inflatedView);
    }

    // viewHolder의 apply 함수를 이용해서 bind 함수를 호출
    // -> 각각의 데이터를 review_list를 사용하는 ViewHolder에 적용
    override fun onBindViewHolder(holder: TotalReviewAdapter.ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {          // 생성자로 부터 받은 데이터의 개수를 측정
        return itemList.size
    }



    inner class ViewHolder(inflatedView: View) : RecyclerView.ViewHolder(inflatedView){
        var view : View = inflatedView

        fun bind(item: TotalReviewClass){

            totalRating += item.rating!!
            view.Rate.rating = item.rating
            view.message.text = item.contents
        }
    }
}