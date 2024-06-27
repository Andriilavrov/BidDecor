package com.example.biddecor.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.biddecor.DbHelper
import com.example.biddecor.R
import com.example.biddecor.databinding.FragmentProfileBinding
import com.example.biddecor.model.User
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.io.File

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val jsonFilePath = File(context?.filesDir, "user.json")
        val jsonString = jsonFilePath.readText()
        val jsonObject = JSONObject(jsonString)
        val email = jsonObject.getString("email")

        val db = DbHelper(requireContext(), null)
        val user: User? = db.getUserByEmail(email)

        val textViewEmail: TextView = binding.userEmailText
        profileViewModel.text.observe(viewLifecycleOwner) {
            textViewEmail.text = user?.email
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val jsonFilePath = File(context?.filesDir, "user.json")
        val jsonString = jsonFilePath.readText()
        val jsonObject = JSONObject(jsonString)
        val email = jsonObject.getString("email")

        val db = DbHelper(requireContext(), null)
        val user: User? = db.getUserByEmail(email)

        val imageView = view.findViewById<ImageView>(R.id.imageView)
        if (user?.ImageProfileRef != null && user?.ImageProfileRef != "") {
            Picasso.get().load(user?.ImageProfileRef).into(imageView)
        }

        val userNameTextView = view.findViewById<TextView>(R.id.userName_text)
        userNameTextView.text = user?.userName + " id: " + user?.userId
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}