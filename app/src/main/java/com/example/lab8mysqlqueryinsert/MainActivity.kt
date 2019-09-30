package com.example.lab8mysqlqueryinsert

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {
    var studentList = arrayListOf<Student>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(applicationContext) as RecyclerView.LayoutManager?
        recycler_view.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        recycler_view.addItemDecoration(DividerItemDecoration(recycler_view.getContext(), DividerItemDecoration.VERTICAL))

        recycler_view.addOnItemTouchListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                Toast.makeText(applicationContext, "You click on : "+studentList[position].std_id,
                    Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onResume() {
        super.onResume()
        callStudentData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.item1 -> {
                val intent = Intent(this@MainActivity, InsertActivity::class.java)
                startActivity(intent)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

        fun callStudentData(){

            studentList.clear();

            val serv : StudentAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(StudentAPI ::class.java)

            serv.retrieveStudent()
                .enqueue(object : Callback<List<Student>> {

                    override fun onResponse(
                        call: Call<List<Student>>,
                        response: Response<List<Student>>
                    ) {
                        response.body()?.forEach{
                            studentList.add(Student(it.std_id, it.std_name, it.std_age))
                        }

                        recycler_view.adapter = StudentAdapter(studentList,applicationContext)
                        text1.text = "Student List : "+ studentList.size.toString()+ " Students"
                    }

                    override fun onFailure(call: Call<List<Student>>, t: Throwable) = t.printStackTrace()

                    })
                }
        }
interface  OnItemClickListener {
    fun onItemClicked(position: Int, view: View)
}
fun RecyclerView.addOnItemTouchListener(onClickListener: OnItemClickListener) {
    this.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {

        override fun onChildViewDetachedFromWindow(view: View) {
            view?.setOnClickListener(null)
        }

        override fun onChildViewAttachedToWindow(view: View) {
            view?.setOnClickListener {
                val holder = getChildViewHolder(view)
                onClickListener.onItemClicked(holder.adapterPosition, view)
            }
        }
    })
}


