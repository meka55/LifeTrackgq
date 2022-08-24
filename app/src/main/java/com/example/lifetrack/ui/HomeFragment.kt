package com.example.lifetrack.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lifetrack.ItemListener
import com.example.lifetrack.room.App
import com.example.lifetrack.adapters.TaskAdapter
import com.example.lifetrack.room.TaskModel
import com.example.lifetrack.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), ItemListener {
     lateinit var binding: FragmentHomeBinding
     lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicker()
        App.appDataBase.taskDao().getAll() .observe(viewLifecycleOwner) { data ->
            Log.e("data", data.toString())
            adapter = TaskAdapter(data, this)
            binding.recyclerTask.adapter = adapter
        }
    }

    private fun initClicker() {
        binding.btnAddTask.setOnClickListener {
            val dialog = CreateTaskData()
            dialog.show(requireActivity().supportFragmentManager, "")
        }
    }
    private fun initLongClicker() {}

        override fun itemUpDate(taskModel: TaskModel) {
            val dialog = CreateTaskData()
            val bundle = Bundle()
            bundle.putSerializable("model", taskModel)
            dialog.arguments = bundle
            dialog.show(requireActivity().supportFragmentManager, "")
        }
     override fun delete(taskModel: TaskModel) {
        val dialog = AlertDialog.Builder(requireActivity())
        dialog.setTitle("Удалить?")
        dialog.setPositiveButton("Да", DialogInterface.OnClickListener{
                dialogInterface, i ->
            App.appDataBase.taskDao().deleteData(taskModel)
            dialogInterface.dismiss()
        })
        dialog.setNegativeButton("Нет", DialogInterface.OnClickListener{
                dialogInterface, i ->
            dialogInterface.dismiss()
        })
        dialog.show()
    }
    }

