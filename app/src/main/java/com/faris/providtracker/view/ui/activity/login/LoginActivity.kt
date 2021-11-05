package com.faris.providtracker.view.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.faris.providtracker.R
import com.faris.providtracker.databinding.ActivityLoginBinding
import com.faris.providtracker.view.ui.ViewModelFactory
import com.faris.providtracker.view.ui.activity.dashboard.DashboardActivity
import com.faris.providtracker.view.ui.activity.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference = getSharedPreferences(getString(R.string.auth), MODE_PRIVATE)
        val viewModelFactory = ViewModelFactory(application, sharedPreference)
        val viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                Log.d("TAG", "$email $password")
                viewModel.getUserByEmailAndPassword(email, password)?.observe(this@LoginActivity) {
                    Log.d("TAG", "$it")
                    if (it.isNotEmpty()) {
                        viewModel.saveToPref(getString(R.string.logged_in), email)
                        val i = Intent(this@LoginActivity, DashboardActivity::class.java)
                        startActivity(i)
                        finish()

                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            getString(R.string.incorrect_email_pass),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            btnRegister.setOnClickListener {
                val i = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }
}