package com.example.swipe_assignment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import org.w3c.dom.Text

class RecyclerViewAdapter(val context: Context,val list:ArrayList<TileModel>):RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.data_tile_layout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tileModel=list.get(position)
        Log.d("GetIamge",tileModel.image)
//        Glide.with(context)
//            .load(tileModel.image)
//            .error(context.resources.getDrawable(R.drawable.ic_launcher_background))
//            .into(holder.imageData)
        if(!tileModel.image.replace("\"","").isEmpty())
            Picasso.get().load(tileModel.image.replace("\"","")+"").error(context.resources.getDrawable(R.drawable.ic_launcher_background)).into(holder.imageData)
        else
            holder.imageData.setImageResource(R.drawable.ic_launcher_background)
        holder.product_name.text=tileModel.product_name
        holder.product_type.text=tileModel.product_type
        holder.price.text=tileModel.price
        holder.tax.text=tileModel.tax
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val imageData=itemView.findViewById<ImageView>(R.id.ImageData)
        val product_name=itemView.findViewById<TextView>(R.id.ProductNameTextData)
        val product_type=itemView.findViewById<TextView>(R.id.ProductTypeTextData)
        val price=itemView.findViewById<TextView>(R.id.PriceTextData)
        val tax=itemView.findViewById<TextView>(R.id.TaxTextData)
    }
}