package com.openclassrooms.realestatemanager.ui.dialog_box

import android.Manifest.permission
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.droidman.ktoasty.showSuccessToast
import com.openclassrooms.realestatemanager.R
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class PhotoChoiceDialog : DialogFragment() {

    private lateinit var photoCamera: ImageView
    private lateinit var photoGallery: ImageView
    //------------------- Camera & gallery ---------------------------------------------------------
    private val CAMERA = permission.CAMERA
    private val READ_STORAGE = permission.READ_EXTERNAL_STORAGE
    private val WRITE_STORAGE = permission.WRITE_EXTERNAL_STORAGE
    private val GALLERY_REQUEST_CODE = 1201
    private val CAMERA_REQUEST_CODE = 807

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view: View = inflater.inflate(R.layout.dialog_photo_choice, null)
        builder.setView(view)

        photoCamera = view.findViewById(R.id.dialog_photo_camera)
        photoGallery = view.findViewById(R.id.dialog_photo_gallery)

        photoCamera.setOnClickListener { checkCameraPermission() }
        photoGallery.setOnClickListener { checkGalleryPermission() }

        builder.setTitle("Choose")
                .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, which ->
                    dismiss()
                })
        return builder.create()
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------- Check permissions ----------------------------------------------
    //----------------------------------------------------------------------------------------------

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    //----------------------------- Camera permission ----------------------------------------------

    //TODO("@annotate")
    //@AfterPermissionGranted(CAMERA_REQUEST_CODE)
    private fun checkCameraPermission(){
        val perms = arrayOf(CAMERA, WRITE_STORAGE)
        if (EasyPermissions.hasPermissions(requireContext(), *perms)){
            activity?.showSuccessToast(getString(R.string.camera_permission), Toast.LENGTH_SHORT, true)
            TODO("Add camera access method")
        }
        else{
            EasyPermissions.requestPermissions(this, getString(R.string.camera_permission_message), CAMERA_REQUEST_CODE, *perms)
        }
    }

    //----------------------------- gallery permission ---------------------------------------------

    //TODO("@annotate")
    //@AfterPermissionGranted(GALLERY_REQUEST_CODE)
    private fun checkGalleryPermission(){
        val perms = arrayOf(READ_STORAGE)
        if (EasyPermissions.hasPermissions(requireContext(), *perms)){
            activity?.showSuccessToast(getString(R.string.gallery_permission), Toast.LENGTH_SHORT, true)
            TODO("Add gallery access method")
        }
        else{
            EasyPermissions.requestPermissions(this, getString(R.string.gallery_permission_message), GALLERY_REQUEST_CODE, *perms)
        }
    }
}