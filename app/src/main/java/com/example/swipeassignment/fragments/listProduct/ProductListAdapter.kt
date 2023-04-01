package com.example.swipeassignment.fragments.listProduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.swipeassignment.R
import com.example.swipeassignment.databinding.SingleProductLayoutBinding
import com.example.swipeassignment.model.Model

class ProductListAdapter(private var productList : List<Model>):RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>() {

    inner class ProductListViewHolder(private val binding: SingleProductLayoutBinding) :RecyclerView.ViewHolder(binding.root){
        fun setData(model: Model) {
            binding.productNameTv.text =  model.mProductName
            binding.productTypeTv.text = model.mProductType
            binding.productPriceTv.text = model.mPrice.toString()
            binding.productTaxTv.text = model.mTax.toString()

            Glide
                .with(binding.root.context)
                .load(model.mImage)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(binding.productIv)
        }

    }
    fun filteredList(productList: List<Model>){
        this.productList = productList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        val binding = SingleProductLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.setData(productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }


}