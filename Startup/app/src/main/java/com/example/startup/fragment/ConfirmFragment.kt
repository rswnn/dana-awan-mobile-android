package com.example.startup.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.startup.R
import kotlinx.android.synthetic.main.fragment_confirm.*

/**
 * A simple [Fragment] subclass.
 */
class ConfirmFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val number = arguments?.getString("number")
        val name = arguments?.getString("name")
        val amount = arguments?.getString("amount")

        tvConName.text = name
        tvConNumber.text = number
        tvTotal.text = "Rp." + amount
        buBayar.setOnClickListener {
            var amount = amount.toString()
            var phone_number = number.toString()
            println(phone_number)
            var bundle = Bundle()
            bundle.putString("number", phone_number)
            bundle.putString("amount", amount)
            view.findNavController().navigate(R.id.action_confirmFragment_to_pinFragment, bundle)
        }
    }


}
