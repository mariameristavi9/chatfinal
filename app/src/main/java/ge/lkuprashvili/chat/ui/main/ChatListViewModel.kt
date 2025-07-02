package ge.lkuprashvili.chat.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ge.lkuprashvili.chat.data.repository.ChatRepository
import ge.lkuprashvili.chat.model.Conversation

class ChatListViewModel : ViewModel() {
    private val chatRepository = ChatRepository()

    private val _conversations =
        MutableLiveData<List<Conversation>>()
    val conversations: LiveData<List<Conversation>> = _conversations

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadChats() {
        chatRepository.getUserConversations(
            onResult = { list ->
                _conversations.postValue(list)
            },
            onError = { errorMsg ->
                _error.postValue(errorMsg)
            }
        )
    }
}



