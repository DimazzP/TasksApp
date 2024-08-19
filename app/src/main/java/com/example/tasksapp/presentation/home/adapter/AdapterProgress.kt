package com.example.tasksapp.presentation.home.adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.tasksapp.R
import com.example.tasksapp.databinding.AdpProgressBinding
import com.example.tasksapp.domain.model.ProgressModel

class AdapterProgress(private val context: Context, private val items: List<ProgressModel>, private val viewPager: ViewPager2, private val itemSelected: (item: ProgressModel)-> Unit) : RecyclerView.Adapter<AdapterProgress.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdpProgressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        val isSelected = position == viewPager.currentItem
        val scaleTitle: Float = if (isSelected) 24f else 20f
        val scaleProgress: Float = if (isSelected) 11f else 8f
        val scaleTextview: Float = if (isSelected) 9f else 6f

        val currentTitleSize = holder.binding.adpProgressTitle.textSize / context.resources.displayMetrics.scaledDensity
        animateTextSize(holder.binding.adpProgressTitle, currentTitleSize, scaleTitle, 300)

        val currentProgressSize = holder.binding.adpProgressTxpro.textSize / context.resources.displayMetrics.scaledDensity
        animateTextSize(holder.binding.adpProgressTxpro, currentProgressSize, scaleProgress, 300)

        val currentTextviewSize = holder.binding.adpProgressSizemember.textSize / context.resources.displayMetrics.scaledDensity
        animateTextSize(holder.binding.adpProgressSizemember, currentTextviewSize, scaleTextview, 300)

        if (position % 2 == 0) {
            holder.binding.adpProgressUser1.setCardBackgroundColor(ContextCompat.getColor(holder.binding.root.context, R.color.home_item_purple_progress))
            holder.binding.adpProgressUser2.setCardBackgroundColor(ContextCompat.getColor(holder.binding.root.context, R.color.home_item_purple_progress))
            holder.binding.adpPrgressOther.setCardBackgroundColor(ContextCompat.getColor(holder.binding.root.context, R.color.home_item_purple_progress))
            holder.binding.adpTaskRoot.setCardBackgroundColor(ContextCompat.getColor(holder.binding.root.context, R.color.home_item_purple_progress))
            holder.binding.adpTaskImgBg.setColorFilter(ContextCompat.getColor(holder.binding.root.context, R.color.home_item_purple_bg_progress))
        } else {
            holder.binding.adpProgressUser1.setCardBackgroundColor(ContextCompat.getColor(holder.binding.root.context, R.color.home_item_yellow_progress))
            holder.binding.adpProgressUser2.setCardBackgroundColor(ContextCompat.getColor(holder.binding.root.context, R.color.home_item_yellow_progress))
            holder.binding.adpPrgressOther.setCardBackgroundColor(ContextCompat.getColor(holder.binding.root.context, R.color.home_item_yellow_progress))
            holder.binding.adpTaskRoot.setCardBackgroundColor(ContextCompat.getColor(holder.binding.root.context, R.color.home_item_yellow_progress))
            holder.binding.adpTaskImgBg.setColorFilter(ContextCompat.getColor(holder.binding.root.context, R.color.home_item_yellow_bg_progress))
        }
    }

    fun animateTextSize(view: TextView, startSize: Float, endSize: Float, duration: Long) {
        val animator = ObjectAnimator.ofFloat(view, "textSize", startSize, endSize)
        animator.duration = duration
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.start()
    }

    fun pxToSp(px: Float, context: Context): Float {
        return px / context.resources.displayMetrics.density
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(val binding: AdpProgressBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProgressModel) {
            binding.adpProgressTitle.text = item.title
            binding.adpProgressBar.progress = item.progress
            binding.adpProgressTxpro.text = "Progress"
            binding.root.setOnClickListener {
                itemSelected(item)
            }
            binding.adpPrgressOther.findViewById<TextView>(R.id.adp_progress_sizemember).text = "${item.member.size - 2} Lainnya"
        }
    }
}


//            // Load avatar using Glide for the first member
//            if (item.member.isNotEmpty()) {
//                Glide.with(binding.root.context)
//                    .load(item.member[0].name) // Replace with actual image URL or resource
//                    .circleCrop()
//                    .placeholder(R.drawable.example_user)
//                    .error(R.drawable.ic_error)
//                    .into(binding.adpProgressUser1)
//            }
//
//            // Load avatar using Glide for the second member
//            if (item.member.size > 1) {
//                Glide.with(binding.root.context)
//                    .load(item.member[1].name) // Replace with actual image URL or resource
//                    .circleCrop()
//                    .placeholder(R.drawable.example_user)
//                    .error(R.drawable.ic_error)
//                    .into(binding.adpProgressUser2)
//            }