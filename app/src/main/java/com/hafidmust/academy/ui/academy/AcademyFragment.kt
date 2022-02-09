package com.hafidmust.academy.ui.academy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hafidmust.academy.R
import com.hafidmust.academy.databinding.FragmentAcademyBinding
import com.hafidmust.academy.utils.DataDummy
import com.hafidmust.academy.viewmodel.ViewModelFactory


class AcademyFragment : Fragment() {

    private lateinit var binding : FragmentAcademyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAcademyBinding.inflate(layoutInflater,container
        ,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[AcademyViewModel::class.java]
            val academyAdapter = AcademyAdapter()
            binding.progressBar.visibility = View.VISIBLE
            viewModel.getCourses().observe(viewLifecycleOwner){
                binding.progressBar.visibility = View.GONE
                academyAdapter.setCourses(it)
                academyAdapter.notifyDataSetChanged()
            }

            with(binding.rvAcademy){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = academyAdapter
            }

        }
    }

}