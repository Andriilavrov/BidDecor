package com.example.biddecor.ui.notifications

import android.content.Context
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
import com.example.biddecor.databinding.FragmentNotificationsBinding
import com.example.biddecor.model.User
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.io.File

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val jsonFilePath = File(context?.filesDir, "user.json")
        val jsonString = jsonFilePath.readText()
        val jsonObject = JSONObject(jsonString)
        val email = jsonObject.getString("email")

        val db = DbHelper(requireContext(), null)
        val user: User? = db.getUserByEmail(email)

        val textViewEmail: TextView = binding.userEmailText
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textViewEmail.text = user?.email
        }

//        val textViewName: TextView = binding.userNameText
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textViewName.text = user?.userName
//        }

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
        Picasso.get().load(user?.ImageProfileRef).into(imageView)

        val userNameTextView = view.findViewById<TextView>(R.id.userName_text)
        userNameTextView.text = user?.userName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}