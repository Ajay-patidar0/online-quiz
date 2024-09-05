package com.example.quizonline

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quizonline.databinding.QuizItemRecyclerRowBinding

class QuizListAdapter(private val quizModellist: List<QuizModel>): RecyclerView.Adapter<QuizListAdapter.MyViewHolder>() {
    class MyViewHolder(private val binding: QuizItemRecyclerRowBinding): RecyclerView.ViewHolder(binding.root) {
            fun bind(quizModel: QuizModel){
                    binding.apply {
                        quizTitle.text=quizModel.title
                        quizSubtitle.text=quizModel.subtitle
                        quizTimerText.text=quizModel.time + " min"
                        root.setOnClickListener {
                            val intent= Intent(root.context,QuizActivity::class.java)
                            QuizActivity.qsmodelList= quizModel.qslist
                            QuizActivity.time= quizModel.time
                            root.context.startActivity(intent)
                        }
                    }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding=QuizItemRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return quizModellist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(quizModellist[position])
    }

}