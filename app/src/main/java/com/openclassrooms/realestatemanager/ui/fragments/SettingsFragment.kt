package com.openclassrooms.realestatemanager.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.openclassrooms.realestatemanager.R

class SettingsFragment : PreferenceFragmentCompat() {

    private val NOTIFICATIONS_PREF = "Notifications preferences"
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        loadSettings()
    }

    private fun loadSettings() {
        val sharePreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val notifications = sharePreferences.getBoolean("switchNotification", true)

        if (notifications){
            enableNotifications()
        }
        else{
            disableNotifications()
        }
    }

    private fun enableNotifications() {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(NOTIFICATIONS_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("silentMode", false)
        editor.commit()
    }

    private fun disableNotifications() {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences(NOTIFICATIONS_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("silentMode", true)
        editor.commit()
    }
}