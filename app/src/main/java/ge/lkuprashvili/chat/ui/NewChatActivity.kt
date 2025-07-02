package ge.lkuprashvili.chat.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import ge.lkuprashvili.chat.databinding.ActivityNewChatBinding
import ge.lkuprashvili.chat.model.User
import ge.lkuprashvili.chat.utils.generateChatId

class NewChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewChatBinding
    private lateinit var adapter: UserAdapter
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.usersRv.layoutManager = LinearLayoutManager(this)

        adapter = UserAdapter { user ->
            val currentUserId = auth.currentUser?.uid ?: return@UserAdapter
            val chatId = generateChatId(currentUserId, user.userId)

            val chatMap = mapOf(
                "users" to mapOf(
                    "0" to currentUserId,
                    "1" to user.userId
                ),
                "lastMessage" to "სალამი", // optional
                "timestamp" to System.currentTimeMillis()
            )

            db.child("chats").child(chatId).setValue(chatMap).addOnSuccessListener {
                val intent = Intent(this, ChatActivity::class.java)
                intent.putExtra("chatId", chatId)
                intent.putExtra("otherUserId", user.userId)
                intent.putExtra("userName", user.nickname)
                startActivity(intent)
                finish()
            }
        }


        binding.usersRv.adapter = adapter

        loadUsers()
    }

    private fun loadUsers() {
        val currentUid = auth.currentUser?.uid ?: return

        db.child("users").get().addOnSuccessListener { snapshot ->
            val userList = snapshot.children.mapNotNull {
                val user = it.getValue(User::class.java)
                user?.copy(userId = it.key ?: "")
            }.filter { it.userId != currentUid }

            adapter.submitList(userList)
        }
    }

}
