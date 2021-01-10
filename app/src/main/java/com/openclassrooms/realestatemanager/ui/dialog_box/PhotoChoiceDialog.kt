package com.openclassrooms.realestatemanager.ui.dialog_box

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.openclassrooms.realestatemanager.R
import java.lang.IllegalStateException

class PhotoChoiceDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_photo_choice, null)
        builder.setView(view)
        builder.setTitle("Choose")
                .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, which ->
                    dismiss()
                })
        return builder.create()
    }
}