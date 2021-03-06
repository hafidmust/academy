package com.hafidmust.academy.ui.reader.content

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.hafidmust.academy.data.source.local.entity.ModuleEntity
import com.hafidmust.academy.databinding.FragmentModuleContentBinding
import com.hafidmust.academy.ui.reader.CourseReaderViewModel
import com.hafidmust.academy.viewmodel.ViewModelFactory
import com.hafidmust.academy.vo.Status


class ModuleContentFragment : Fragment() {

    private lateinit var viewModel: CourseReaderViewModel

    companion object{
        val TAG: String = ModuleContentFragment::class.java.simpleName
        fun newInstance() : ModuleContentFragment = ModuleContentFragment()
    }

    private lateinit var binding : FragmentModuleContentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentModuleContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null){
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]
            viewModel.selectedModule.observe(viewLifecycleOwner){ moduleEntity ->

                if (moduleEntity != null){
                    when(moduleEntity.status){
                        Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            if (moduleEntity.data != null){
                                binding.progressBar.visibility = View.GONE
                                if (moduleEntity.data.contentEntity != null){
                                    populateWebView(moduleEntity.data)
                                }
//                                setbutton next
                                setButtonNextPrevState(moduleEntity.data)
                                if (!moduleEntity.data.read){
                                    viewModel.readContent(moduleEntity.data)
                                }
                            }
                        }
                        Status.ERROR ->{
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }

                    }
                    binding.btnNext.setOnClickListener {
                        viewModel.setNextPage()
                    }
                    binding.btnPrev.setOnClickListener {
                        viewModel.setPrevPage()
                    }
                }
            }
        }
    }

    private fun setButtonNextPrevState(module: ModuleEntity) {
        if (activity != null){
            when(module.position){
                0 ->{
                    binding.btnPrev.isEnabled = false
                    binding.btnNext.isEnabled = true
                }
                viewModel.getModuleSize() -1 ->{
                    binding.btnNext.isEnabled = false
                    binding.btnPrev.isEnabled = true
                }
                else ->{
                    binding.btnNext.isEnabled = true
                    binding.btnPrev.isEnabled = true
                }
            }
        }
    }

    private fun populateWebView(content: ModuleEntity) {
        binding.webView.loadData(content.contentEntity?.content ?: "","text/html", "UTF-8")
    }

}