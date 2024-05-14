package com.example.musicstream

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicstream.adapter.CategoryAdapter
import com.example.musicstream.databinding.ActivityMainBinding
import com.example.musicstream.model.CategoryModel
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCategories()


    }

    
    fun getCategories(){
        FirebaseFirestore.getInstance().collection("category")
            .get().addOnSuccessListener { 
                val categoryList = it.toObjects(CategoryModel::class.java)
                setupCategoryRecyclerView(categoryList)
            }
    }

    private fun setupCategoryRecyclerView(categoryList: List<CategoryModel>) {
        categoryAdapter = CategoryAdapter(categoryList)
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.categoriesRecyclerView.adapter=categoryAdapter
    }

//    fun setupSection(id:String, mainLayout: RelativeLayout,titleView: TextView,recyclerView: RecyclerView){
//        FirebaseFirestore.getInstance().collection("section")
//            .document(id)
//            .get().addOnSuccessListener {
//                val section = it.toObject(CategoryModel::class.java)
//                section?.apply {
//                    mainLayout.visibility= View.VISIBLE
//                    titleView.text =name
//                    recyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager
//                        .HORIZONTAL,true )
//             //       recyclerView.adapter=SectionSo
//                }
//            }
//    }
}