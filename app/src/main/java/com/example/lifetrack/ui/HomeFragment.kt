package com.example.lifetrack.ui

import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import androidx.fragment.app.Fragment
import com.example.lifetrack.ItemListener
import com.example.lifetrack.adapters.TaskAdapter
import com.example.lifetrack.databinding.FragmentHomeBinding
import com.example.lifetrack.room.App
import com.example.lifetrack.room.TaskModel

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
     private  fun doBounceAnimation(targetView: View) {
         val interpolator: Interpolator = object : Interpolator{
             override fun getInterpolation(v: Float): Float {
                 return getPowOut(v, 2.0)
             }
         }
         val animator = ObjectAnimator.ofFloat(targetView,"translationY", 0f,25f,0f)
         animator.interpolator = interpolator
         animator.startDelay =200
         animator.duration = 800
         animator.repeatCount =5
         animator.start()
     }
    private  fun getPowOut(elapsedTimeRate: Float, pow: Double): Float {
        return (1.toFloat() - Math.pow((1 - elapsedTimeRate).toDouble(), pow)).toFloat()
    }

    private fun initClicker() {
        binding.btnAddTask.setOnClickListener {
            doBounceAnimation(it)
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

