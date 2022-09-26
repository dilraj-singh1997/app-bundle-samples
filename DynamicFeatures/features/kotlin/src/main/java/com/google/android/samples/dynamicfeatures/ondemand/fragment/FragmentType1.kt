package com.google.android.samples.dynamicfeatures.ondemand.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.google.android.samples.dynamicfeatures.ondemand.MainViewModel
import com.google.android.samples.dynamicfeatures.ondemand.kotlin.R
import com.google.android.samples.dynamicfeatures.ondemand.kotlin.databinding.FragmentType1Binding
import com.google.android.samples.dynamicfeatures.ondemand.utility.Constants


class FragmentType1 : Fragment() {

    private var _binding: FragmentType1Binding? = null
    private val binding get() = _binding
    private var id: String = "1"

    companion object {
        fun getFragment(id: String) = FragmentType1()
            .apply {
                arguments = bundleOf(
                    Constants.ID_TYPE_1 to id
                )
            }

        private const val TAG = "FragmentType1"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(TAG, "onCreateView: is called ${this.hashCode()}")
        return FragmentType1Binding.inflate(layoutInflater, container, false).let {
            _binding = it
            it.rootView.setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
            )
            it.rootView.setContent {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Cyan)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.DarkGray,
                        text = stringResource(id = R.string.type_1),
                        fontSize = 24.sp,
                    )
                }
            }
            it.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated: is called ${this.hashCode()}")
        parseArguments()
    }


    private fun parseArguments() {
        id = arguments?.getString(Constants.ID_TYPE_1) ?: ""
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: is called ${this.hashCode()}")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: is called ${this.hashCode()}")
    }

    override fun onStop() {
        Log.i(TAG, "onStop: is called ${this.hashCode()}")
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "onDestroyView: is called ${this.hashCode()}")
        _binding = null
    }

    override fun onAttach(context: Context) {
        Log.i(TAG, "onAttach: is called ${this.hashCode()}")
        super.onAttach(context)
    }

    override fun onPause() {
        Log.i(TAG, "onPause: is called ${this.hashCode()}")
        super.onPause()
    }

    override fun onDetach() {
        super.onDetach()
        Log.i(TAG, "onDetach: is called ${this.hashCode()}")
    }
}