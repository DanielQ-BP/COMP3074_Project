package com.comp3074_101384549.projectui.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.comp3074_101384549.projectui.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    private val PREFS_NAME = "ParkSpotPrefs"
    private val KEY_USERNAME = "username"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


        fun loadProfile() {
            val username = prefs.getString(KEY_USERNAME, "John Doe") ?: "John Doe"
            val age      = prefs.getInt("age", 0)
            val desc     = prefs.getString("description", "") ?: ""

            binding.usernameTextView.text = username

            binding.nameReadOnly.text = "Name: $username"

            binding.ageReadOnly.text = "Age: ${if (age == 0) "–" else age}"
            binding.descriptionReadOnly.text = "Description: ${if (desc.isEmpty()) "–" else desc}"

            binding.nameEditText.setText(username)
            binding.ageEditText.setText(if (age > 0) age.toString() else "")
            binding.descriptionEditText.setText(desc)
        }

        loadProfile()

        binding.editProfileButton.setOnClickListener {
            binding.readOnlyContainer.visibility = View.GONE
            binding.editContainer.visibility     = View.VISIBLE
            binding.editProfileButton.visibility = View.GONE
        }


        binding.saveButton.setOnClickListener {
            val newName = binding.nameEditText.text.toString().trim()
            val newAgeStr = binding.ageEditText.text.toString().trim()
            val newDesc = binding.descriptionEditText.text.toString().trim()

            if (newName.isEmpty() || newAgeStr.isEmpty() || newDesc.isEmpty()) {
                Toast.makeText(requireContext(),
                    "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newAge = newAgeStr.toIntOrNull() ?: 0

            with(prefs.edit()) {
                putString(KEY_USERNAME, newName)
                putInt("age", newAge)
                putString("description", newDesc)
                apply()
            }

            binding.readOnlyContainer.visibility = View.VISIBLE
            binding.editContainer.visibility     = View.GONE
            binding.editProfileButton.visibility = View.VISIBLE

            loadProfile()

            Toast.makeText(requireContext(),
                "Profile saved (Prototype)", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}