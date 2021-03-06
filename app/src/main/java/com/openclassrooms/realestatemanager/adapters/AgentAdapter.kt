package com.openclassrooms.realestatemanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.events.DeleteAgentEvent
import com.openclassrooms.realestatemanager.model.Agent
import com.openclassrooms.realestatemanager.ui.fragments.AddAgentFragmentDirections
import de.hdodenhof.circleimageview.CircleImageView
import org.greenrobot.eventbus.EventBus

class AgentAdapter: RecyclerView.Adapter<AgentAdapter.AgentViewHolder>() {

    private var agentList = emptyList<Agent>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.agent_item, parent, false)
        return AgentViewHolder(itemView)
    }

    override fun getItemCount() = agentList.size

    override fun onBindViewHolder(holder: AgentViewHolder, position: Int) {
       val currentAgent = agentList[position]

        Glide.with(holder.agentPhoto.context)
                .load(currentAgent.agentPhoto.toString())
                .into(holder.agentPhoto)
        holder.agentFirstName.text = currentAgent.firstName
        holder.agentName.text = currentAgent.name
        holder.agentPhone.text = currentAgent.phoneNumber
        holder.agentEmail.text = currentAgent.email

        holder.updateButton.setOnClickListener {
            val action = AddAgentFragmentDirections.actionNavAddAgentToNavUpdateAgent3(currentAgent)
            holder.updateButton.findNavController().navigate(action)
        }

        holder.deleteButton.setOnClickListener {
            EventBus.getDefault().post(DeleteAgentEvent(currentAgent))
        }
    }

    class AgentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val agentPhoto: CircleImageView = itemView.findViewById(R.id.agent_item_image)
        val agentFirstName: TextView = itemView.findViewById(R.id.agent_item_first_name)
        val agentName: TextView = itemView.findViewById(R.id.agent_item_name)
        val agentPhone: TextView = itemView.findViewById(R.id.agent_item_phone)
        val agentEmail: TextView = itemView.findViewById(R.id.agent_item_email)
        val updateButton: ImageButton = itemView.findViewById(R.id.agent_item_update_button)
        val deleteButton: ImageButton = itemView.findViewById(R.id.item_list_delete_button)
    }

    fun setData(agent: List<Agent>){
        this.agentList = agent
        notifyDataSetChanged()
    }
}