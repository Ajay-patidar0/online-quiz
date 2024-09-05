package com.example.quizonline

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizonline.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var quizModellist: MutableList<QuizModel>
    lateinit var adapter: QuizListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        quizModellist= mutableListOf()
        getDataFromFirebase()
        }
    private fun getDataFromFirebase(){
        FirebaseDatabase.getInstance().reference
            .get()
            .addOnSuccessListener {dataSnapshot->
                if(dataSnapshot.exists()){
                    for(data in dataSnapshot.children) {
                            val quizModel= data.getValue(QuizModel::class.java)
                        if (quizModel != null) {
                            quizModellist.add(quizModel)
                        }
                    }
                }
                setupRecyclerView()
            }


    }

    private fun setupRecyclerView() {
        adapter= QuizListAdapter(quizModellist)
        binding.recyclerView.layoutManager= LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

    }

}
