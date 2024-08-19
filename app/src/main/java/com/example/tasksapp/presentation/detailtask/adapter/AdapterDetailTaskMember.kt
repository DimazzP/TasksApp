package com.example.tasksapp.presentation.detailtask.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tasksapp.databinding.AdpPhotoBinding
import com.example.tasksapp.domain.model.MemberModel
import com.example.tasksapp.R

class AdapterDetailTaskMember(private val members: List<MemberModel>, private val clickAddListener: () -> Unit) :
    RecyclerView.Adapter<AdapterDetailTaskMember.MemberViewHolder>() {

    override fun getItemCount(): Int = members.size + 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = AdpPhotoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        if (position == members.size) {
            holder.bindAddMember()
        } else {
            holder.bind(members[position])
        }
    }

    inner class MemberViewHolder(private val binding: AdpPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(member: MemberModel) {
            Glide.with(binding.root.context)
                .load(member.photo)
                .into(binding.adpPhotoImage.getImageView())
        }

        fun bindAddMember() {
            binding.adpPhotoImage.elevation = 0f
            binding.root.elevation = 0f
            binding.adpPhotoImage.setImageBackground(R.color.white)
            binding.adpPhotoImage.setImageResource(R.drawable.ic_add_circle)
            binding.root.setOnClickListener {
                clickAddListener()
            }
        }
    }
}
