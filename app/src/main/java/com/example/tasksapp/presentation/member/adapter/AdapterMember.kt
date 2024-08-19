package com.example.tasksapp.presentation.member.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tasksapp.databinding.AdpTeamMemberBinding
import com.example.tasksapp.domain.model.MemberModel

class AdapterMember(private val members: List<MemberModel>) :
    RecyclerView.Adapter<AdapterMember.MemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding = AdpTeamMemberBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(members[position])
    }

    override fun getItemCount(): Int = members.size

    inner class MemberViewHolder(private val binding: AdpTeamMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(member: MemberModel) {
            binding.adpTeamName.text = member.name
            binding.adpTeamRole.text = member.role

            // Load gambar anggota menggunakan Glide
            Glide.with(binding.root.context)
                .load(member.photo)
                .into(binding.adpTeamPhoto.getImageView())
        }
    }
}
