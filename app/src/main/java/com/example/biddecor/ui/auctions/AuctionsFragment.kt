package com.example.biddecor.ui.auctions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biddecor.DbHelper
import com.example.biddecor.R
import com.example.biddecor.databinding.FragmentAuctionsBinding
import com.example.biddecor.model.Lot
import com.example.biddecor.LotsAdapter

class AuctionsFragment : Fragment() {

    private var _binding: FragmentAuctionsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val auctionsViewModel =
            ViewModelProvider(this).get(AuctionsViewModel::class.java)
        _binding = FragmentAuctionsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lotsList: RecyclerView? = view?.findViewById(R.id.lotsList)
        val db = DbHelper(requireContext(), null)
        val lots: ArrayList<Lot> = db.getAllLots()
        lotsList?.layoutManager = LinearLayoutManager(requireContext())
        lotsList?.adapter = LotsAdapter(lots, requireContext())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}