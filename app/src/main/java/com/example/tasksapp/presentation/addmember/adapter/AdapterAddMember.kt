package com.example.tasksapp.presentation.addmember.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tasksapp.databinding.AdpAddMemberBinding
import com.example.tasksapp.domain.model.MemberModel

class AdapterAddMember(private val members: List<MemberModel>) :
    RecyclerView.Adapter<AdapterAddMember.MemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = AdpAddMemberBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(members[position])
    }

    override fun getItemCount(): Int = members.size

    inner class MemberViewHolder(private val binding: AdpAddMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(member: MemberModel) {
            binding.apply {
                adpmemUser.text = member.name
                Glide.with(binding.root.context)
                    .load(member.photo)
                    .into(binding.adpmemPhotoImage.getImageView())
            }
        }
    }
}
