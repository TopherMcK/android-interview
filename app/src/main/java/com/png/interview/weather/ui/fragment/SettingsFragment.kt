package com.png.interview.weather.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.png.interview.databinding.FragmentSettingsBinding
import com.png.interview.ui.InjectedFragment
import com.png.interview.weather.ui.binder.SettingsFragmentBinder

class SettingsFragment : InjectedFragment() {

    lateinit var settingsFragmentBinder: SettingsFragmentBinder

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentSettingsBinding.inflate(inflater, container, false).apply {
            settingsFragmentBinder = SettingsFragmentBinder(
                getViewModel()
            )
            viewBinder = settingsFragmentBinder

            this.lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsFragmentBinder.updateViewModelMeasurementSystemPref()
    }
}