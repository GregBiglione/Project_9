package com.openclassrooms.realestatemanager.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Agent
import kotlinx.android.synthetic.main.spinner_agent_item.view.*

class UpdateAgentSpinnerAdapter(context: Context): ArrayAdapter<Agent>(context, 0) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val agent = getItem(position)
        val view = LayoutInflater.from(context).inflate(R.layout.spinner_agent_item, parent, false)

        if (agent != null) {
            view.spinner_agent_item_image.setImageURI(Uri.parse(agent.agentPhoto))
        }
        Glide.with(parent.context)
                .load(agent?.agentPhoto)
                .into(view.spinner_agent_item_image)
        view.spinner_agent_item_first_name.text = agent?.firstName
        view.spinner_agent_item_name.text = agent?.name
        view.spinner_agent_item_email.text = agent?.email
        view.spinner_agent_item_phone.text = agent?.phoneNumber
        return view
    }
}

