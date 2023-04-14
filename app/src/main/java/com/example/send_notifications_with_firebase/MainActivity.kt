package com.example.send_notifications_with_firebase

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.send_notifications_with_firebase.MyAppConstants.TARGET_TOPIC
import com.example.send_notifications_with_firebase.MyAppConstants.TOPIC_FOR_SUBSCRIPTION
import com.example.send_notifications_with_firebase.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.mo_chatting.chatapp.data.models.NotificationData
import com.mo_chatting.chatapp.data.models.PushNotification
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val pushPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            //  Toast.makeText(this, "granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeToTopic()
        getPermissions()
        setOnClicks()
    }

    private fun subscribeToTopic() {
        Firebase.messaging.subscribeToTopic(TOPIC_FOR_SUBSCRIPTION)
            .addOnCompleteListener {
                showToast("subscribed to topic successfully")
            }
            .addOnFailureListener {
                showToast("failed to subscribe to topic")
            }
    }

    private fun getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            pushPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun setOnClicks() {
        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString()
            val name = binding.etName.text.toString()
            if (message.isNotEmpty() && name.isNotEmpty()) {
                   sendFireBaseNotification(PushNotification(NotificationData(name,message),TARGET_TOPIC))
            } else {
                showToast("field is empty")
            }
        }
    }

    private fun showToast(message: String){
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }

}