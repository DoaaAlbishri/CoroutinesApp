package com.example.coroutinesapp


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var responseText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.button)

        btn.setOnClickListener {
            getResult()
        }
    }

    private fun getResult() {
        responseText = findViewById<View>(R.id.textView) as TextView
        CoroutineScope(IO).launch {
            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            var displayResponse = ""
            val call: Call<Slip>? = apiInterface!!.doGetListResources()
            call?.enqueue(object : Callback<Slip?> {
                override fun onResponse(
                        call: Call<Slip?>?,
                        response: Response<Slip?>
                ) {
                    Log.d("TAG", response.code().toString() + "")
                    val resource: Slip? = response.body()
                    val advice = resource?.slip
                    displayResponse += "${advice!!.advice}"
                    responseText.text = displayResponse
                }

                override fun onFailure(call: Call<Slip?>, t: Throwable?) {
                    call.cancel()
                }
            })
        }
    }
}