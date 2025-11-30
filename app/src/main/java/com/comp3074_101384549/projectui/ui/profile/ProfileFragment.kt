package com.comp3074_101384549.projectui.ui.profile

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.comp3074_101384549.projectui.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    // SharedPreferences keys
    private val PREFS_NAME = "ParkSpotPrefs"
    private val KEY_USERNAME = "username"
    private val KEY_PROFILE_IMAGE_URI = "profile_image_uri"

    private lateinit var prefs: SharedPreferences

    // Store selected image URI
    private var selectedImageUri: Uri? = null

    // Gallery picker
    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                selectedImageUri = uri
                binding.profileImage.setImageURI(uri)
                saveProfileImageUri(uri)
            }
        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Initialize SharedPreferences once
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }


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

}
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load text profile + saved profile picture
        loadProfile()
        loadProfileImage()

        // By default, hide the Change Photo button (only show in edit mode)
        binding.btnChangePhoto.visibility = View.GONE

        // Change photo button: open gallery
        binding.btnChangePhoto.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.editProfileButton.setOnClickListener {
            binding.readOnlyContainer.visibility = View.GONE
            binding.editContainer.visibility = View.VISIBLE
            binding.editProfileButton.visibility = View.GONE

            // Now allow changing photo too
            binding.btnChangePhoto.visibility = View.VISIBLE
        }

        binding.saveButton.setOnClickListener {
            val newName = binding.nameEditText.text.toString().trim()
            val newAgeStr = binding.ageEditText.text.toString().trim()
            val newDesc = binding.descriptionEditText.text.toString().trim()

            if (newName.isEmpty() || newAgeStr.isEmpty() || newDesc.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please fill all fields",
                    Toast.LENGTH_SHORT
                ).show()
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
            binding.editContainer.visibility = View.GONE
            binding.editProfileButton.visibility = View.VISIBLE

            // Hide photo change button when leaving edit mode
            binding.btnChangePhoto.visibility = View.GONE

            loadProfile()

            Toast.makeText(
                requireContext(),
                "Profile saved (Prototype)",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun loadProfile() {
        val username = prefs.getString(KEY_USERNAME, "John Doe") ?: "John Doe"
        val age = prefs.getInt("age", 0)
        val desc = prefs.getString("description", "") ?: ""

        binding.usernameTextView.text = username

        binding.nameReadOnly.text = "Name: $username"
        binding.ageReadOnly.text = "Age: ${if (age == 0) "–" else age}"
        binding.descriptionReadOnly.text =
            "Description: ${if (desc.isEmpty()) "–" else desc}"

        binding.nameEditText.setText(username)
        binding.ageEditText.setText(if (age > 0) age.toString() else "")
        binding.descriptionEditText.setText(desc)
    }

    private fun saveProfileImageUri(uri: Uri) {
        prefs.edit()
            .putString(KEY_PROFILE_IMAGE_URI, uri.toString())
            .apply()
    }

    private fun loadProfileImage() {
        val uriString = prefs.getString(KEY_PROFILE_IMAGE_URI, null)
        if (!uriString.isNullOrEmpty()) {
            val uri = Uri.parse(uriString)
            selectedImageUri = uri
            binding.profileImage.setImageURI(uri)
        }
        // else it will just use the default src from XML
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

