package com.openclassrooms.realestatemanager.ui.dialog_box

import android.Manifest.permission
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
    companion object{
        const val CAMERA = permission.CAMERA
        const val READ_STORAGE = permission.READ_EXTERNAL_STORAGE
        const val WRITE_STORAGE = permission.WRITE_EXTERNAL_STORAGE
        const val GALLERY_REQUEST_CODE = 1201
        const val CAMERA_REQUEST_CODE = 807
        const val IMAGE_PICK_CODE = 2108
        const val TAKE_PHOTO_CODE = 3003
    }
    //------------------- Listener -----------------------------------------------------------------
    private lateinit var galleryListener: GalleryListener

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

    //----------------------------------------------------------------------------------------------
    //----------------------------- Camera permission ----------------------------------------------
    //----------------------------------------------------------------------------------------------

    @AfterPermissionGranted(CAMERA_REQUEST_CODE)
    private fun checkCameraPermission(){
        val perms = arrayOf(CAMERA, WRITE_STORAGE)
        if (EasyPermissions.hasPermissions(requireContext(), *perms)){
            activity?.showSuccessToast(getString(R.string.camera_permission), Toast.LENGTH_SHORT, true)
            takePhoto()
        }
        else{
            EasyPermissions.requestPermissions(this, getString(R.string.camera_permission_message), CAMERA_REQUEST_CODE, *perms)
        }
    }

    //----------------------------------------------------------------------------------------------
    //----------------------------- Gallery permission ---------------------------------------------
    //----------------------------------------------------------------------------------------------

    @AfterPermissionGranted(GALLERY_REQUEST_CODE)
    private fun checkGalleryPermission(){
        val perms = arrayOf(READ_STORAGE)
        if (EasyPermissions.hasPermissions(requireContext(), *perms)){
            activity?.showSuccessToast(getString(R.string.gallery_permission), Toast.LENGTH_SHORT, true)
            pickPhotoFromGallery()
        }
        else{
            EasyPermissions.requestPermissions(this, getString(R.string.gallery_permission_message), GALLERY_REQUEST_CODE, *perms)
        }
    }

    //------------------- Intent to access gallery -------------------------------------------------

    private fun pickPhotoFromGallery() {
        val accessGallery = Intent(Intent.ACTION_PICK)
        accessGallery.type ="image/*"
        startActivityForResult(accessGallery, IMAGE_PICK_CODE)
    }

    //------------------- Intent to access camera --------------------------------------------------

    private fun takePhoto(){
        val accessCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(accessCamera, TAKE_PHOTO_CODE)
    }

    //------------------- Handle image pick result -------------------------------------------------

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            val uriPhoto = data?.data
            galleryListener.applyGalleryPhoto(uriPhoto)
            dismiss()
        }
        if (resultCode == Activity.RESULT_OK && requestCode == TAKE_PHOTO_CODE){
            var bitmapPhoto = data?.extras?.get("data") as Bitmap
            dismiss()
        }
    }

    //------------------- Gallery interface --------------------------------------------------------

    interface GalleryListener {
        fun applyGalleryPhoto(uriPhoto: Uri?)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            if (context == activity) {
                galleryListener = context as GalleryListener
            } else {
                galleryListener = targetFragment as GalleryListener
            }
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "must implement GalleryListener")
        }
        //try {
        //    //galleryListener = context as GalleryListener
        //    //galleryListener = targetFragment as GalleryListener
        //
        //    //when (activity){ //no crash but image selected not shown into image view in AddAgentFragment
        //    //    context as GalleryListener -> galleryListener = context
        //    //    targetFragment as GalleryListener -> galleryListener = context
        //    //}
//
        //    //when (context){
        //    //    context as GalleryListener -> galleryListener = context
        //    //    targetFragment as GalleryListener -> galleryListener = context
        //    //}
        //} catch (e: ClassCastException) {
        //    throw ClassCastException(context.toString() + "must implement GalleryListener")
        //}
    }
}