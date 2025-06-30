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
import ge.lkuprashvili.chat.databinding.ActivityRegisterBinding
import ge.lkuprashvili.chat.ui.main.MainActivity
import ge.lkuprashvili.chat.utils.Const.FILL_ALL_FIELDS
import ge.lkuprashvili.chat.utils.Const.REGISTRATION_FAILED
import ge.lkuprashvili.chat.viewmodel.AuthViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerBtn.setOnClickListener {
            val email = binding.nicknameEt.text.toString()
            val password = binding.passwordEt.text.toString()
            val nickname = binding.nicknameEt.text.toString()
            val profession = binding.professionEt.text.toString()

            if (email.isEmpty() || password.isEmpty() || nickname.isEmpty() || profession.isEmpty()) {
                Toast.makeText(this, FILL_ALL_FIELDS, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.register(email, password, nickname, profession)
        }
        viewModel.authResult.observe(this) { (success, error) ->
            if (success) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, error ?: REGISTRATION_FAILED , Toast.LENGTH_SHORT).show()
            }
        }
    }
}