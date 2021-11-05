package com.faris.providtracker.view.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.faris.providtracker.R
import com.faris.providtracker.databinding.FragmentHomeBinding
import com.faris.providtracker.view.ui.ViewModelFactory
import com.faris.providtracker.view.ui.activity.add_habit.AddHabitActivity
import com.faris.providtracker.view.ui.activity.login.LoginActivity

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireContext().getSharedPreferences(
            getString(R.string.auth),
            AppCompatActivity.MODE_PRIVATE
        )
        val viewModelFactory = ViewModelFactory(requireActivity().application, sharedPreferences)
        val viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        val adapter = HomeAdapter {
            viewModel.updateHabit(it)
        }

        val loggedInEmail = viewModel.getFromPref(getString(R.string.logged_in))
        Log.d("TAG", "loggedin: $loggedInEmail")
        viewModel.getHabitByEmail(loggedInEmail!!)?.observe(viewLifecycleOwner) {
            adapter.setData(it)
            val doneHabits = it.filter { habit -> habit.isDone }
            binding!!.tvData.text = getString(R.string.x_6, doneHabits.size, it.size)

            if (it.isNotEmpty()) {
                binding!!.rvReminders.visibility = View.VISIBLE
                binding!!.tvEmpty.visibility = View.GONE
            }
        }

        binding!!.apply {
            rvReminders.adapter = adapter
            rvReminders.layoutManager = GridLayoutManager(requireContext(), 2)

            tvLogout.setOnClickListener {
                viewModel.removeFromPref(getString(R.string.logged_in))

                val i = Intent(requireContext(), LoginActivity::class.java)
                startActivity(i)
                requireActivity().finish()
            }

            fabHome.setOnClickListener {
                val i = Intent(requireContext(), AddHabitActivity::class.java)
                startActivity(i)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}