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
            val viewModel = ViewModelProvider(requireActivity(), factory)[CourseReaderViewModel::class.java]
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
                }
            }
        }
    }

    private fun populateWebView(content: ModuleEntity) {
        binding.webView.loadData(content.contentEntity?.content ?: "","text/html", "UTF-8")
    }

}