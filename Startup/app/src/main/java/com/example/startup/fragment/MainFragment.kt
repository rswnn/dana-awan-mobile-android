package com.example.startup.fragment


import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.startup.R
import com.example.startup.api.RetrofitClient
import com.example.startup.models.LoginResponse
import com.example.startup.storage.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        var user = SharedPrefManager.getInstance(this.context!!)?.user
//        tvUser.text = user?.name
//        tvCash.text = "Rp. " + user?.cash.toString()
//        tvPhoneNumber.text = user?.phone_number
        val user = SharedPrefManager.getInstance(context!!)?.user!!
        var userId = user?.id
        if (SharedPrefManager.getInstance(context!!)!!.isLoggedIn) {
            RetrofitClient.instance.getUSer(
                userId
            )
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(context!!, t.message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (response.body()?.status!!){
                            val user = response.body()?.user
                            tvUser.text = user?.name
                            tvCash.text = user?.cash.toString().trim()
                            tvPhoneNumber.text = user?.phone_number
                        }
                    }
                })
        }
        ivTransfer.setOnClickListener {
            view.findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
        }

        ivWithdraw.setOnClickListener {
            view.findNavController().navigate(R.id.action_mainFragment_to_clarificationFragment)
        }

        itemswipetorefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(context!!,
            R.color.gojekgreen
        ))
        itemswipetorefresh.setColorSchemeColors(Color.WHITE)

        itemswipetorefresh.setOnRefreshListener {
            AlertDialog.Builder(context)
                .setTitle("hallo")
                .show()
            val user = SharedPrefManager.getInstance(context!!)?.user!!
            var userId = user?.id
            if (SharedPrefManager.getInstance(context!!)!!.isLoggedIn) {
                RetrofitClient.instance.getUSer(
                    userId
                )
                    .enqueue(object : Callback<LoginResponse> {
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Toast.makeText(context!!, t.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: Response<LoginResponse>
                        ) {
                            if (response.body()?.status!!){
                                val user = response.body()?.user
                                tvUser.text = user?.name
                                tvCash.text = user?.cash.toString().trim()
                                tvPhoneNumber.text = user?.phone_number
                            }
                        }
                    })
            }
        }
    }
}
