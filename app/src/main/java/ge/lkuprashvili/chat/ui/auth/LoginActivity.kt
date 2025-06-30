package ge.lkuprashvili.chat.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ge.lkuprashvili.chat.R
import ge.lkuprashvili.chat.databinding.ActivityLoginBinding
import ge.lkuprashvili.chat.ui.main.MainActivity
import ge.lkuprashvili.chat.utils.Const.LOGIN_FAILED
import ge.lkuprashvili.chat.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            val email = binding.nicknameEt.text.toString()
            val password = binding.passwordEt.text.toString()
            viewModel.login(email, password)
        }

        binding.signupRedirect.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        viewModel.authResult.observe(this) { (success, error) ->
            if (success) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, error ?: LOGIN_FAILED, Toast.LENGTH_SHORT).show()
            }
        }
    }
}