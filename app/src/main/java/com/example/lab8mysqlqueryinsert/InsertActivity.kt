package com.example.lab8mysqlqueryinsert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_insert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InsertActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
    }
    fun addStudent(v: View){

        val api : StudentAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StudentAPI :: class.java)

        api.insertStd(
            edt_id.text.toString(),
            edt_name.text.toString(),
            edt_age.text.toString().toInt()).enqueue(object : Callback<Student> {

            override fun onResponse(call: Call<Student>, response: Response<Student>) {
                if (response.isSuccessful()){

                    Toast.makeText(applicationContext, "Successfully Inserted", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Student>, t: Throwable) {
                Toast.makeText(applicationContext, "Error onFailur " + t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun resetStudent(v:View){
        edt_id.getText().clear()
        edt_name.getText().clear()
        edt_age.getText().clear()
    }
}