package com.example.lifetrack.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lifetrack.R
import com.example.lifetrack.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    private var imageUri: Uri? = null
    private var image: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root

    }


    companion object {
        val IMAGE_REQUEST_CODE = 1000
    }

    @SuppressLint("IntentReset")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.avatarIv.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivity(intent)
            imageSelect()
        }
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(
            "test",
            Context.MODE_PRIVATE
        )
        val imageProfile = sharedPreferences.getString("image", R.color.black.toString())

        image = Uri.parse(imageProfile)

        binding.avatarIv.setImageURI(image)
    }

    private fun imageSelect() {
        val intent: Intent
        if (Build.VERSION.SDK_INT < 19) {
            intent = Intent(Intent.ACTION_GET_CONTENT)
        } else {
            intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
        }
        intent.type = "image/*"
        startActivityForResult(
            Intent.createChooser(intent, "Select Picture"),
            IMAGE_REQUEST_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    imageUri = data.data
                    requireActivity().grantUriPermission(
                        requireContext().packageName,
                        imageUri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                    val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    imageUri?.let {
                        requireActivity().contentResolver.takePersistableUriPermission(
                            it, takeFlags
                        )
                    }

                    val sharedPreferences: SharedPreferences =
                        requireContext().getSharedPreferences(
                            "test",
                            Context.MODE_PRIVATE
                        )
                    sharedPreferences.edit().putString("image", imageUri.toString()).apply()

                    binding.avatarIv.setImageURI(imageUri)
                    binding.avatarIv.invalidate()
                }
            }
        }
    }
}
