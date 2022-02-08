package com.hafidmust.academy.ui.reader.list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hafidmust.academy.R
import com.hafidmust.academy.data.ModuleEntity
import com.hafidmust.academy.databinding.FragmentModuleListBinding
import com.hafidmust.academy.ui.reader.CourseReaderActivity
import com.hafidmust.academy.ui.reader.CourseReaderCallback
import com.hafidmust.academy.utils.DataDummy


class ModuleListFragment : Fragment(), ModuleListAdapter.MyAdapterClickListener {

    companion object{
        val TAG: String =  ModuleListFragment::class.java.simpleName

        fun newInstance() : ModuleListFragment = ModuleListFragment()
    }

    private lateinit var binding: FragmentModuleListBinding
    private lateinit var adapter: ModuleListAdapter
    private lateinit var courseReaderCallback: CourseReaderCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentModuleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ModuleListAdapter(this)
        populateRecyclerView(DataDummy.generateDummyModules("a14"))
    }

    private fun populateRecyclerView(modules: List<ModuleEntity>) {
        with(binding){
            progressBar.visibility = View.GONE
            adapter.setModules(modules)
            rvModule.layoutManager = LinearLayoutManager(context)
            rvModule.setHasFixedSize(true)
            rvModule.adapter = adapter
            val dividerItemDecoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            rvModule.addItemDecoration(dividerItemDecoration)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        courseReaderCallback = context as CourseReaderActivity
    }

    override fun onItemClicked(position: Int, moduleId: String) {
        courseReaderCallback.moveTo(position, moduleId)
    }

}