package com.openclassrooms.realestatemanager.model

import android.net.Uri

class AgentDataSource {
    companion object{
        fun createAgentDataSet(): ArrayList<Agent>{
            val listOfAgent = ArrayList<Agent>()
            listOfAgent.add(
                    Agent(
                            1L,
                            Uri.parse("https://wallpapertag.com/wallpaper/full/a/2/0/569247-cool-terminator-2-wallpaper-1920x1200.jpg"),
                            "Arnold",
                            "Schwarzenegger",
                            "+1 (260) 222-8751",
                            "aschwarzenegger79@gmail.com"
                    )
            )
            listOfAgent.add(
                    Agent(
                            2L,
                            Uri.parse("https://www.everythingaction.com/wp-content/uploads/2011/08/Cobra.1986.BDRip_.720p.01.png"),
                            "Sylvester",
                            "Stallone",
                            "+1 (530) 444-7842",
                            "sylvesterstallonecobra@gmail.com"
                    )
            )
            listOfAgent.add(
                    Agent(
                            3L,
                            Uri.parse("https://bamfstyle.files.wordpress.com/2016/06/gf2mc2b-main.jpg"),
                            "Al",
                            "Pacino",
                            "+1 (330) 694-6681",
                            "alpacinothegodfather@gmail.com"
                    )
            )
            listOfAgent.add(
                    Agent(
                            4L,
                            Uri.parse("https://i.pinimg.com/originals/49/b7/ac/49b7ace6519bbce532ab890c6fcb382f.jpg"),
                            "Robert",
                            "De Niro",
                            "+1 (330) 421-3723",
                            "robertdeniroheat@gmail.com"
                    )
            )
            listOfAgent.add(
                    Agent(
                            5L,
                            Uri.parse("https://i.pinimg.com/originals/0d/13/82/0d1382f7cb1a5ca4a369cfac740b0653.jpg"),
                            "Bruce",
                            "Willis",
                            "+1 (530) 911-2182",
                            "brucewillisdiehard@gmail.com"
                    )
            )
            return listOfAgent
        }
    }
}