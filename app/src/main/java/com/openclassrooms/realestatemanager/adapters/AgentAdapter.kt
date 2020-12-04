package com.openclassrooms.realestatemanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Agent
import de.hdodenhof.circleimageview.CircleImageView

class AgentAdapter(private val agentList: List<Agent>): RecyclerView.Adapter<AgentAdapter.AgentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.agent_item, parent, false)
        return AgentViewHolder(itemView)
    }

    override fun getItemCount() = agentList.size

    override fun onBindViewHolder(holder: AgentViewHolder, position: Int) {
       val currentAgent = agentList[position]

        Glide.with(holder.agentPhoto.context)
                .load(currentAgent.agentPhoto)
                .into(holder.agentPhoto)
        holder.agentFirstName.text = currentAgent.firstName
        holder.agentName.text = currentAgent.name
        holder.agentPhone.text = currentAgent.phoneNumber
        holder.agentEmail.text = currentAgent.email
    }

    class AgentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val agentPhoto: CircleImageView = itemView.findViewById(R.id.agent_item_image)
        val agentFirstName: TextView = itemView.findViewById(R.id.agent_item_first_name)
        val agentName: TextView = itemView.findViewById(R.id.agent_item_name)
        val agentPhone: TextView = itemView.findViewById(R.id.agent_item_phone)
        val agentEmail: TextView = itemView.findViewById(R.id.agent_item_email)
    }
}