package com.example.marketplace.activityResult

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AppCompatActivity

class PickPhoto: ActivityResultContract<Int, PickPhoto.Result>() {
    companion object{
        const val TAG = "PICK PHOTO"
    }
    override fun createIntent(context: Context, ringtoneType: Int) : Intent {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        return intent
    }

    override fun parseResult(resultCode: Int, result: Intent?) : Result {
        if (resultCode != AppCompatActivity.RESULT_OK) {
            Log.i(TAG,"NULL")
            return Result(null,null)
        }else {
            return Result(result!!.clipData, result.data)
        }
    }

    data class Result(val clipData: ClipData?, val uri: Uri? )
}
