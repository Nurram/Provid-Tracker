package com.faris.providtracker.view.ui.fragment.home

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
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
import com.faris.providtracker.view.local.entities.Habit
import com.faris.providtracker.view.ui.ViewModelFactory
import com.faris.providtracker.view.ui.activity.add_habit.AddHabitActivity
import com.faris.providtracker.view.ui.activity.login.LoginActivity
import com.faris.providtracker.view.utils.AlarmReceiver
import java.util.*

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

        val currentCal = Calendar.getInstance()
        val currentDate =
            "${currentCal.get(Calendar.DAY_OF_MONTH)}/${currentCal.get(Calendar.MONTH)}/${currentCal.get(Calendar.YEAR)}"
        val savedDate = viewModel.getFromPref("date")
        val ids = listOf(1, 2, 3, 4, 5, 6)

        if (currentDate != savedDate) {
            viewModel.setDefaultHabitToFalse(ids)
            viewModel.putToPref("date", currentDate)
        }

        val adapter = HomeAdapter { viewModel.updateHabit(it) }

        adapter.setOnLongClickListener(object : HomeAdapter.OnLongClickListener {
            override fun onLongClick(habit: Habit) {
                val i = Intent(requireContext(), AddHabitActivity::class.java)
                i.putExtra(getString(R.string.habit), habit)
                startActivity(i)
            }
        })

        val loggedInEmail = viewModel.getFromPref(getString(R.string.logged_in))
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

    private fun cancelAlarm(habit: Habit) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getBroadcast(
                requireContext(),
                habit.id,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getBroadcast(
                requireContext(),
                habit.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        alarmManager.cancel(pendingIntent)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}