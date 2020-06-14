package com.example.startup.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.startup.R
import com.example.startup.api.RetrofitClient
import com.example.startup.models.TransactionResponse
import com.example.startup.storage.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_pin.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class PinFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var name = arguments?.getString("name")
        var number = arguments?.getString("number")
        val amount = arguments?.getString("amount")!!.toInt()

        buPinCon.setOnClickListener {
            var name = name
            var pin = etPin.text.toString()
            var user = SharedPrefManager.getInstance(context!!)?.user
            var userPin = user!!.pin
            var phone_number = user.phone_number.trim()
            var phone_numberr = number.toString().trim()
            var cash = amount.toLong()
            if (pin == userPin) {
                RetrofitClient.instance.transaction(phone_number, phone_numberr, cash)
                    .enqueue(object : Callback<TransactionResponse> {
                        override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                            Toast.makeText(context!!, "Terjadi kesalahan", Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onResponse(
                            call: Call<TransactionResponse>,
                            response: Response<TransactionResponse>
                        ) {
                            if (response.body()?.status!!) {
                                var total = response.body()?.cash.toString().trim()
                                var bundle = Bundle()
                                bundle.putString("name", name)
                                bundle.putString("number", number)
                                bundle.putString("amount", total)
                                view.findNavController()
                                    .navigate(R.id.action_pinFragment_to_thanksFragment, bundle)
                            }
                        }

                    })
            } else {
                tvWrongPin.text = "Pinnya salah"
            }
        }
    }

}
