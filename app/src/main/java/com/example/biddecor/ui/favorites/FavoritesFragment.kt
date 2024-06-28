package com.example.biddecor.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biddecor.DbHelper
import com.example.biddecor.LotsAdapter
import com.example.biddecor.R
import com.example.biddecor.databinding.FragmentFavoritesBinding
import com.example.biddecor.model.Lot

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null

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
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lotsList: RecyclerView? = view?.findViewById(R.id.lotsList)
        val db = DbHelper(requireContext(), null)
        val lots: ArrayList<Lot> = db.getFavorLots()
        lotsList?.layoutManager = LinearLayoutManager(requireContext())
        lotsList?.adapter = LotsAdapter(lots, requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}