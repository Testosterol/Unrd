package com.testosterolapp.unrd.conversations

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.testosterolapp.unrd.R
import com.testosterolapp.unrd.data.Chats
import com.testosterolapp.unrd.db.DaoRepository
import com.testosterolapp.unrd.main.MainActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ConversationsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConversationsFragment : Fragment(), ConversationAdapter.ConversationAdapterChatListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val model: ConversationViewModel by viewModels()
    private var conversationFragmentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_conversations, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        conversationFragmentView = view

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        recyclerView.setHasFixedSize(true)

        val adapter = ConversationAdapter(requireContext(), this)
        recyclerView.adapter = adapter


        val resultDao = DaoRepository(requireContext()).resultDao
        model.init(resultDao!!)

        model.allConversations!!.observe(requireActivity()) { chats ->
            if (chats.isNotEmpty()) {
                adapter.submitList(chats)
            }
        }
       // setupActionBar()

        model.filterTextAll.setValue("")

    }
    private fun setupActionBar() {
        val mToolbar: Toolbar = conversationFragmentView!!.findViewById(R.id.toolbar)
        (requireActivity() as MainActivity).setSupportActionBar(mToolbar)



     /*   mToolbar.setNavigationOnClickListener { v: View? ->
            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(messagesFragmentView.getWindowToken(), 0)
            Navigation.findNavController(Objects.requireNonNull(view)).popBackStack()
        }*/
    /*    Objects.requireNonNull((requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true)
        Objects.requireNonNull((activity as MainActivity?).getSupportActionBar()).setDisplayShowHomeEnabled(true)
        Objects.requireNonNull((activity as MainActivity?).getSupportActionBar()).setDisplayShowTitleEnabled(true)
        val drawable = resources.getDrawable(R.mipmap.ic_launcher)
        val bitmap = (drawable as BitmapDrawable).bitmap
        val newdrawable: Drawable = BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, 80, 80, false))
        Objects.requireNonNull((activity as MainActivity?).getSupportActionBar()).setLogo(newdrawable)
        var name: String? = null
        if (arguments != null) {
            name = MessageUtil.getContactName(ConversationDatabase.getInstance(context).getConversationDao().getConversationAddress(arguments!!.getLong("conversationThreadId")), Objects.requireNonNull(context))
        }
        if (name != null) {
            if (name != "") {
                Objects.requireNonNull((activity as MainActivity?).getSupportActionBar())
                        .setSubtitle(ConversationDatabase.getInstance(context).getConversationDao().getConversationAddress(conversationThreadId))
                Objects.requireNonNull((activity as MainActivity?).getSupportActionBar())
                        .setTitle(name)
            } else {
                Objects.requireNonNull((activity as MainActivity?).getSupportActionBar())
                        .setTitle(ConversationDatabase.getInstance(context).getConversationDao().getConversationAddress(conversationThreadId))
            }
        }
*/

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.navigation, menu)

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        }
        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                model.filterTextAll.value = "%$query%"
                return false
            }
        })


    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ConversationsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ConversationsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }



    override fun onChatSelected(chats: Chats?, v: View?, position: Int) {

        Log.d("asd", "position: " + position)
        Log.d("asd", "chats: " + chats!!.display_name)

        /*val intent = Intent(this, ChatActivity::class.java)
        val chatId = chats!!.chat_id
        intent.putExtra("chat_id", chatId)
        startActivity(intent)*/
    }
}