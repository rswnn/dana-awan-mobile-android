package com.example.startup.fragment


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.provider.ContactsContract
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.startup.Contact
import com.example.startup.R
import com.example.startup.api.RetrofitClient
import com.example.startup.models.ContactResponse
import kotlinx.android.synthetic.main.contact_list.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        read()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val search = menu.findItem(R.id.search).actionView as SearchView
        val sm = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        search.setSearchableInfo(sm.getSearchableInfo((activity!!.componentName)))
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                read()
                Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }


    fun read() {
        var adapter: ContactAdapter? = null
        var context: Context? = null
        var contactList = ArrayList<Contact>()
        var cursor = this.context!!.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null,
            null
        )
        while (cursor!!.moveToNext()) {
            val name =
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number =
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

            contactList.add(Contact(name, number))

            adapter = ContactAdapter(
                this.context!!,
                contactList
            )
            list.adapter = adapter


            list.setOnItemClickListener { parent, view, position, id ->
                var contactListArr = contactList[position]
//                var name = contactListArr.name
                var number = contactListArr.number.toString()
                RetrofitClient.instance.searchContact(number)
                    .enqueue(object : Callback<ContactResponse> {
                        override fun onFailure(call: Call<ContactResponse>, t: Throwable) {
                            Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(
                            call: Call<ContactResponse>,
                            response: Response<ContactResponse>
                        ) {
                            try {
                                if (response.body()?.status!!) {
                                    Toast.makeText(
                                        getContext(),
                                        response.body()?.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                    var bundle = Bundle()
                                    bundle.putString("name", response.body()?.contact?.name)
                                    bundle.putString("number", response.body()?.contact?.phone_number)
                                    view.findNavController().navigate(
                                        R.id.action_searchFragment_to_TAmountFragment,
                                        bundle
                                    )
                                }
                                if (!response.body()?.status!!) {
                                    Toast.makeText(
                                        getContext(),
                                        response.body()?.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            } catch (ex: Exception) {
                                Toast.makeText(
                                    getContext(),
                                    "Kontak tidak ditemukan",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            println(response.body()?.message)
                        }

                    })
//                var bundle = Bundle()
//                bundle.putString("name", name)
//                bundle.putString("number", number)
//                view.findNavController().navigate(R.id.action_searchFragment_to_TAmountFragment, bundle)
            }
        }
    }

    class ContactAdapter : BaseAdapter {
        var context: Context? = null
        var contactList = ArrayList<Contact>()

        constructor(context: Context, contactList: ArrayList<Contact>) : super() {
            this.context = context
            this.contactList = contactList
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val contact = contactList[position]
            var inflator =
                context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var contactView = inflator.inflate(R.layout.contact_list, null)
            contactView.tvName.text = contact.name!!
            contactView.tvNumber.text = contact.number

            return contactView
        }

        override fun getItem(position: Int): Any {
            return contactList[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return contactList.size
        }

    }
}
