package com.testosterolapp.unrd.main

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.testosterolapp.unrd.R
import java.util.*

/**
 * The idea - I have combined some Kotlin with Java classes to showcase my experience in both.
 * For data handling I chose room persistence library and mapped data using gson and foreign_keys in the
 * infrastructure. The rendering of the UI is being done in Java classes instead.
 * I would have normally used MVVM architecture due to the fact that LiveData and ViewModel
 * fit this case and Room architecture perfectly. However,I have limited experience using said MVVN in Kotlin (besides Room and coroutines),
 * therefore I decided to save the time and pain and went with simple recyclerView which
 * caused slow rendering of the screen with Chats (sadly, I didn't have a lot of time to optimize
 * it and play around as I would like).
 *
 * I have used coroutines for some background work, mainly for inserting the data. Few unit and
 * database tests are present as well
 *
 * The app is very basic due to limited time horizon. It contains 3 main screens:
 *
 * 1. First screen as seen on Image_1 - is welcoming/greeting screen for a user with small
 * generic animation and some sample text with two buttons. Enter button will trigger the intro video
 * and skip button will just skip it.
 *
 * 2. Second screen is a simple recycler view holding all of the Chats available from the data with some
 * small generic UI design and data operations such as showing count of messages, images of characters etc.
 *
 * P.S. I didn't have time to optimize data operations for this screen, therefore the loading time might take
 * couple seconds since majority of the operations are done on the Main thread. (I could have done
 * Multithreading, but due to limited time horizon, I just went with the most basic MVP) - Image_3
 *
 * 3. Third screen is After clicking on a specific chat, it will redirect you to another simple recycler view
 * which holds teh information regarding the specific chat: characters, messages, time of received/sent message
 * as seen on Image_4
 *
 */
class MainActivity : AppCompatActivity(), MainFragment.OnFragmentInteractionListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationComponent()
    }

    private fun setupNavigationComponent() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        if (navHostFragment != null) {
            val navController = navHostFragment.navController
            val navInflater = navController.navInflater
            val graph = navInflater.inflate(R.navigation.navigation)
            navHostFragment.navController.setGraph(graph)
        }
    }

    override fun onFragmentInteraction(uri: Uri?) {
        TODO("Not yet implemented")
    }
}