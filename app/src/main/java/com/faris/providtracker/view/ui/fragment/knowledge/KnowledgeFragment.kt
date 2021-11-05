package com.faris.providtracker.view.ui.fragment.knowledge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.faris.providtracker.databinding.FragmentKnowledgeBinding
import com.faris.providtracker.view.model.Knowledge

class KnowledgeFragment : Fragment() {
    private var binding: FragmentKnowledgeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKnowledgeBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = KnowledgeAdapter(Knowledge.getKnowledges())
        binding?.apply {
            rvKnowledges.adapter = adapter
            rvKnowledges.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = KnowledgeFragment()
    }
}