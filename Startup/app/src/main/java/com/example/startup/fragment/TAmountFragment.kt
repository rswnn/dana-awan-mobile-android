package com.example.startup.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.startup.R
import kotlinx.android.synthetic.main.fragment_tamount.*

/**
 * A simple [Fragment] subclass.
 */
class TAmountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tamount, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val number = arguments?.getString("number")
        val name = arguments?.getString("name")
        tvAmountNumber.text = number
        tvAmountName.text = name
        buConfirm.setOnClickListener {
            var number = tvAmountNumber.text.toString()
            var name = tvAmountName.text.toString()
            var amount = etAmount.text.toString()
            var bundle = Bundle()
            if(amount.isEmpty()) {
                Toast.makeText(context, "Ko gak diisi? 😅", Toast.LENGTH_SHORT).show()
            } else {
                bundle.putString("number", number)
                bundle.putString("name", name)
                bundle.putString("amount", amount)
                view.findNavController().navigate(R.id.action_TAmountFragment_to_confirmFragment, bundle)
            }
        }
    }
}
