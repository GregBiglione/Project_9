package com.openclassrooms.realestatemanager.ui.fragments

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidman.ktoasty.showSuccessToast
import com.google.android.material.textfield.TextInputEditText
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.adapters.AgentAdapter
import com.openclassrooms.realestatemanager.database.dao.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.events.DeleteAgentEvent
import com.openclassrooms.realestatemanager.injections.ViewModelFactory
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.repositories.AgentRepository
import com.openclassrooms.realestatemanager.repositories.HousePhotoRepository
import com.openclassrooms.realestatemanager.repositories.HouseRepository
import com.openclassrooms.realestatemanager.ui.dialog_box.PhotoChoiceDialog
import com.openclassrooms.realestatemanager.utils.ImageConverters
import com.openclassrooms.realestatemanager.utils.SavePhoto
import com.openclassrooms.realestatemanager.viewmodel.MainViewModel
import de.hdodenhof.circleimageview.CircleImageView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class AddAgentFragment : Fragment(), PhotoChoiceDialog.GalleryListener, PhotoChoiceDialog.CameraListener {

    private lateinit var agentRecyclerView: RecyclerView
    private lateinit var agentPhoto: CircleImageView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var agentAdapter: AgentAdapter
    //------------------- Agent input --------------------------------------------------------------
    private lateinit var agentFirstName: TextInputEditText
    private lateinit var agentName: TextInputEditText
    private lateinit var agentPhone: TextInputEditText
    private lateinit var agentEmail: TextInputEditText
    //------------------- Button -------------------------------------------------------------------
    private lateinit var addAgentButton: Button
    private lateinit var clearButton: Button
    //------------------- Uri to bitmap Conversion -------------------------------------------------
    private lateinit var savePhoto: SavePhoto
    private lateinit var imageConverters: ImageConverters
    private var photoFromStorage: Uri? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_add_agent, container, false)
        agentRecyclerView = root.findViewById(R.id.add_agent_recycler_view)
        agentPhoto = root.findViewById(R.id.agent_photo)
        agentFirstName = root.findViewById(R.id.agent_first_name)
        agentName = root.findViewById(R.id.agent_name)
        agentPhone = root.findViewById(R.id.agent_phone)
        agentEmail = root.findViewById(R.id.agent_email)
        addAgentButton = root.findViewById(R.id.agent_add_button)
        clearButton = root.findViewById(R.id.agent_delete_button)
        configureViewModel()
        configureAgentRecyclerView()
        imageConverters = ImageConverters()
        savePhoto = SavePhoto()
        clickToAddAgentPhoto()
        formatPhoneNumber()
        clickOnAddAgent()
        clickOnClear()
        return root
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure agent recyclerview ---------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureAgentRecyclerView(){
        agentAdapter = AgentAdapter()
        agentRecyclerView.adapter = agentAdapter
        agentRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Configure ViewModel ------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun configureViewModel(){
        val agentDao = RealEstateManagerDatabase.getInstance(requireContext()).agentDao
        val propertyDao = RealEstateManagerDatabase.getInstance(requireContext()).houseDao
        val housePhotoDao = RealEstateManagerDatabase.getInstance(requireContext()).housePhotoDao
        val agentRepository = AgentRepository(agentDao)
        val propertyRepository = HouseRepository(propertyDao)
        val housePhotoRepository = HousePhotoRepository(housePhotoDao)
        val factory = ViewModelFactory(agentRepository, propertyRepository, housePhotoRepository)
        mainViewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)
        //------------------- Get agents from room db ----------------------------------------------
        mainViewModel.allAgents.observe(viewLifecycleOwner, { agent ->
            agentAdapter.setData(agent)
        })
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Add agent photo from gallery ---------------------------------------------
    //----------------------------------------------------------------------------------------------

    //------------------- Click on image view ------------------------------------------------------

    private fun clickToAddAgentPhoto(){
        agentPhoto.setOnClickListener{ showPhotoChoiceDialogBox() }
    }

    //------------------- Show dialog box ----------------------------------------------------------

    private fun showPhotoChoiceDialogBox(){
        val photoChoiceDialog = PhotoChoiceDialog(this, this)
        //fragmentManager?.let { photoChoiceDialog.show(it, "Photo choice dialog box") }
        photoChoiceDialog.show(requireFragmentManager(), "Photo choice dialog box")
        photoChoiceDialog.setTargetFragment(this, 1)
    }

    //------------------- Get Uri from dialog box --------------------------------------------------

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun applyGalleryPhoto(uriPhoto: Uri?) {
        agentPhoto.setImageURI(uriPhoto)
        val bitmap = imageConverters.uriToBitmap(uriPhoto, requireContext())
        val tempUri: Uri? = savePhoto.getImageUri(requireContext(), bitmap)
        photoFromStorage = tempUri
    }

    //------------------- Get Bitmap from dialog box -----------------------------------------------

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun applyCameraPhoto(bitmapPhoto: Bitmap) {
        agentPhoto.setImageBitmap(bitmapPhoto)
        val tempUri: Uri? = savePhoto.getImageUri(requireContext(), bitmapPhoto)
        photoFromStorage = tempUri
    }

    //------------------- Handle image pick result -------------------------------------------------

    private fun clickOnAddAgent(){
        addAgentButton.setOnClickListener { saveAgent() }
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Convert phone number 2345678989 to (234) 567-8989 ------------------------
    //----------------------------------------------------------------------------------------------

    private fun formatPhoneNumber(){
        agentPhone.addTextChangedListener(phoneWatcher)
    }

    //------------------- Text watcher -------------------------------------------------------------

    private val phoneWatcher = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {

            //------------------- StringBuilder to hold all digits of the edit text ----------------
            val digits = StringBuilder()
            //------------------- StringBuilder to hold phone number -------------------------------
            val phoneDigits = StringBuilder()
            //------------------- Get characters from teh edit text --------------------------------
            val chars: CharArray = agentPhone.text.toString().toCharArray()
            //------------------- Get each digit with a for loop -----------------------------------
            for (x in chars.indices) {
                if (Character.isDigit(chars[x])) {
                    //------------------- Add digit into digits StringBuilder ----------------------
                    digits.append(chars[x])
                }
            }

            //------------------- Add space between digit's phone number ---------------------------
            if (digits.toString().length >= 3){
                //------------------- Formatting after 3rd character -------------------------------
                var countryCode = String()
                //------------------- Create country code ------------------------------------------
                countryCode += "(" + digits.toString().substring(0, 3) + ") "
                //------------------- Add country code into phoneDigits StringBuilder --------------
                phoneDigits.append(countryCode)
                //------------------- if digits are more than or just 6, that means we already have the country code
                if (digits.toString().length >= 6){
                    var regionCode = String()
                    //------------------- Create state code ----------------------------------------
                    regionCode += digits.toString().substring(3, 6) + "-"
                    //------------------- Add region code into phoneDigits StringBuilder -----------
                    phoneDigits.append(regionCode)

                    //------------------- Limit digits to 10 max ---------------------------------------
                    if (digits.toString().length >= 10){
                        phoneDigits.append(digits.toString().substring(6, 10))
                    }
                    else{
                        phoneDigits.append(digits.toString().substring(6))
                    }
                }
                else{
                    phoneDigits.append(digits.toString().substring(3))
                }
                //------------------- Remove watcher if not we'll have an ∞ loop -----------------------
                agentPhone.removeTextChangedListener(this)
                //------------------- Set the new text into the EditText -------------------------------
                agentPhone.setText(phoneDigits.toString())
                //------------------- Bring the cursor to the end of input -----------------------------
                agentPhone.setSelection(agentPhone.text.toString().length)
                //------------------- Bring back the watcher and go on listening to change events ------
                agentPhone.addTextChangedListener(this)
            }
            else{
                return
            }
        }
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Add agent in room db -----------------------------------------------------
    //----------------------------------------------------------------------------------------------

    //------------------- Click on add button ------------------------------------------------------

    private fun saveAgent(){

        val firstName: String = agentFirstName.text.toString().trim()
        val name: String = agentName.text.toString().trim()
        val phone: String = "+1 " + agentPhone.text.toString().trim()
        val email: String = agentEmail.text.toString().trim()

        if (inputCheck(firstName, name, phone, email)){
            if(firstName.isEmpty()){
                agentFirstName.error = getString(R.string.enter_first_name)
            }

            if(name.isEmpty()){
                agentName.error = getString(R.string.enter_name)
            }

            if(phone.isEmpty()){
                agentPhone.error = getString(R.string.enter_phone)
            }

            if(email.isEmpty()){
                agentEmail.error = getString(R.string.enter_email)
            }
            else{
                addAgent(agent = Agent(null, /*photoFromStorage*/ photoFromStorage.toString(), firstName, name, phone, email))
                clearChamps()
            }
        }
    }

    private fun addAgent(agent: Agent) {
        mainViewModel.createAgent(agent)
        activity?.showSuccessToast("Welcome " + agent.firstName, Toast.LENGTH_SHORT, true)
        closeKeyboard(agentEmail)

    }

    //----------------------------------------------------------------------------------------------
    //------------------- Remove agent from room db ------------------------------------------------
    //----------------------------------------------------------------------------------------------

    @Subscribe
    fun onDeleteAgent(event: DeleteAgentEvent) {
        mainViewModel.deleteAgent(event.agent)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Clear champs -------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun clearChamps(){ //Maybe use TextUtils.isEmpty(firstName) & add parameter to method
        if (agentFirstName.text != null){
            agentFirstName.setText("")
            agentPhoto.setImageResource(R.drawable.ic_baseline_person_24)
        }
        if (agentName.text != null){
            agentName.setText("")
        }
        if (agentPhone.text != null){
            agentPhone.setText("")
        }
        if (agentEmail.text != null){
            agentEmail.setText("")
        }
    }

    //------------------- Click on clear button -------------------------------------------------------

    private fun clickOnClear(){
        clearButton.setOnClickListener {
            clearChamps()
        }
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Check empty champs -------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun inputCheck(firstName: String, name: String, phone: String, email: String): Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(name) && TextUtils.isEmpty(phone) && TextUtils.isEmpty(email))
    }

    //----------------------------------------------------------------------------------------------
    //------------------- Hide keyboard ------------------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private fun closeKeyboard(view: View){
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}