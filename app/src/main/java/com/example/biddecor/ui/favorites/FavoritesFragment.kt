package com.example.biddecor.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.biddecor.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favoritesViewModel =
            ViewModelProvider(this).get(FavoritesViewModel::class.java)

        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        favoritesViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

//        val lotsList: RecyclerView? = view?.findViewById(R.id.lotsList)
//        val db = DbHelper(requireContext(), null)
//        val lots: ArrayList<Lot> = db.getAllLots()
//        lotsList?.layoutManager = LinearLayoutManager(requireContext())
//        lotsList?.adapter = LotsAdapter(lots, requireContext())

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}