package com.testosterolapp.unrd.intro

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.navigation.Navigation
import com.github.florent37.shapeofview.shapes.ArcView
import com.testosterolapp.unrd.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SplashScreenFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SplashScreenFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        val buttonEnter = view.findViewById<Button>(R.id.enter_splash_screen)
        val buttonSkip = view.findViewById<Button>(R.id.skip_intro_video)

        val arcLayout = view.findViewById<ArcView>(R.id.arcLayout)
        if (arcLayout != null) {
            ValueAnimator.ofFloat(0f, 0f, 200f).apply {
                addUpdateListener { animation -> arcLayout.arcHeight = ((animation.animatedValue as Float).toInt()) }
                duration = 5000
                repeatCount = ValueAnimator.INFINITE
                repeatMode = ValueAnimator.REVERSE
            }.start()
        }
        val clickListener = View.OnClickListener { view ->
            when (view.getId()) {
                R.id.enter_splash_screen -> startIntroVideo(view)
            }
        }
        buttonEnter.setOnClickListener(clickListener)

        val clickListenerSkip = View.OnClickListener { view ->
            when (view.getId()) {
                R.id.skip_intro_video -> skipIntroVideo(view)
            }
        }
        buttonSkip.setOnClickListener(clickListenerSkip)
    }


    private fun skipIntroVideo(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_conversationsFragment);
    }

    private fun startIntroVideo(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_splashScreenFragment_to_introVideoFragment);
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SplashScreenFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SplashScreenFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}