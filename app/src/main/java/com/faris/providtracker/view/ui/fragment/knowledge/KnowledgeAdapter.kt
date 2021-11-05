package com.faris.providtracker.view.ui.fragment.knowledge

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faris.providtracker.databinding.KnowledgeItemListBinding
import com.faris.providtracker.view.model.Knowledge

class KnowledgeAdapter(
    private val knowledges: List<Knowledge>
) : RecyclerView.Adapter<KnowledgeAdapter.KnowledgeHolder>() {

    inner class KnowledgeHolder(private val binding: KnowledgeItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(knowledge: Knowledge) {
            binding.apply {
                ivKnowledgeImage.setImageResource(knowledge.image)
                tvKnowledgeName.text = knowledge.name
                tvKnowledgeDesc.text = knowledge.desc
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnowledgeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = KnowledgeItemListBinding.inflate(inflater, parent, false)

        return KnowledgeHolder(binding)
    }

    override fun onBindViewHolder(holder: KnowledgeHolder, position: Int) =
        holder.bind(knowledges[position])

    override fun getItemCount(): Int = knowledges.size
}