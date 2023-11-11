package com.example.swipe_assignment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swipe_assignment.ApiService.ApiInterface
import com.example.swipe_assignment.ApiService.RetrofitInstance
import com.example.swipe_assignment.ViewModels.GetDataFragmentViewModel
import com.example.swipe_assignment.databinding.FragmentGetDataBinding
import com.google.gson.JsonArray
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class GetDataFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    lateinit var viewModal: GetDataFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding=FragmentGetDataBinding.inflate(inflater,container,false)
        val root=binding.root
        viewModal= ViewModelProvider(this).get()
        val recyclerView=binding.recyclerView
        val linearLayoutManager= LinearLayoutManager(root.context,LinearLayoutManager.VERTICAL,false)
        recyclerView.layoutManager=linearLayoutManager

        viewModal.getApiData(root.context)

        viewModal.arrayList.observe(viewLifecycleOwner, Observer {
            binding.ProgressGetData.visibility=View.GONE
            val adapter=RecyclerViewAdapter(root.context,it)
                recyclerView.adapter=adapter
        })

        binding.Reload.setOnClickListener {
            binding.ProgressGetData.visibility=View.VISIBLE
            viewModal.getApiData(root.context)
        }

        binding.addDataToFragment.setOnClickListener{
            startActivity(Intent(root.context,AddDataActivity::class.java))
        }

        return root
    }
}