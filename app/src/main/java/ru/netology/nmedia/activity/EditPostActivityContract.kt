package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class EditPostResultContract : ActivityResultContract<String, String?>() {
    override fun createIntent(context: Context, input: String): Intent {
        val intent = Intent(context, EditPostActivity::class.java)
        intent.putExtra("post text input", input)
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK) {
            intent?.getStringExtra("post text output")
        } else {
            null
        }
}
class NewPostResultContract : ActivityResultContract<Unit, String?>() {

    override fun createIntent(context: Context, input: Unit): Intent =
        Intent(context, NewPostActivity::class.java)

    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK) {
            intent?.getStringExtra(Intent.EXTRA_TEXT)
        } else {
            null
        }
}