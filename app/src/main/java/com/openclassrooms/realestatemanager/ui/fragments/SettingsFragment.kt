package com.openclassrooms.realestatemanager.ui.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.openclassrooms.realestatemanager.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    //override fun onCreateView(
    //        inflater: LayoutInflater,
    //        container: ViewGroup?,
    //        savedInstanceState: Bundle?
    //): View? {
    //    //val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
    //    return inflater.inflate(R.layout.fragment_slideshow, container, false)
    //}
}