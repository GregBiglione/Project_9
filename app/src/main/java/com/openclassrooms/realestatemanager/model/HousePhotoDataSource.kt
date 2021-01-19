package com.openclassrooms.realestatemanager.model

import android.net.Uri

class HousePhotoDataSource {
    companion object{

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #1 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet1() :ArrayList<HousePhoto>{
            val listOfPhoto1 = ArrayList<HousePhoto>()
            listOfPhoto1.add(
                    HousePhoto(
                            1L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/1/properties/Property-b2660000000001e2000157b5fd0a-31614642.jpg",
                            "Living room"
                    )
            )
            listOfPhoto1.add(
                    HousePhoto(
                            11L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/2/properties/Property-b2660000000001e2000257b5fd0a-31614642.jpg",
                            "Terrace"
                    )
            )
            listOfPhoto1.add(
                    HousePhoto(
                            12L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/3/properties/Property-b2660000000001e2000357b5fd0a-31614642.jpg",
                            "Fitness room"
                    )
            )
            listOfPhoto1.add(
                    HousePhoto(
                            13L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/5/properties/Property-b2660000000001e2000557b5fd0a-31614642.jpg",
                            "Swimming pool"
                    )
            )
            listOfPhoto1.add(
                    HousePhoto(
                            14L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/8/properties/Property-b2660000000001e2000857b5fd0a-31614642.jpg",
                            "Bed room"
                    )
            )
            listOfPhoto1.add(
                    HousePhoto(
                            15L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/9/properties/Property-b2660000000001e2000957b5fd0a-31614642.jpg",
                            "Kitchen"
                    )
            )
            return listOfPhoto1
        }

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #2 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet2() :ArrayList<HousePhoto>{
            val listOfPhoto2 = ArrayList<HousePhoto>()
            listOfPhoto2.add(
                    HousePhoto(
                            2L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/4/properties/Property-431f0000000005d2000a5f9be394-97656643.jpg",
                            "Living room"
                    )
            )
            listOfPhoto2.add(
                    HousePhoto(
                            21L,
                            "https://fr.luxuryestate.com/p97656643-appartement-en-vente-new-york",
                            "Terrace"
                    )
            )
            listOfPhoto2.add(
                    HousePhoto(
                            22L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/15/properties/Property-431f0000000005d200115f9be395-97656643.jpg",
                            "Bathroom"
                    )
            )
            listOfPhoto2.add(
                    HousePhoto(
                            23L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/17/properties/Property-431f0000000005d200135f9be395-97656643.jpg",
                            "Bedroom"
                    )
            )
            listOfPhoto2.add(
                    HousePhoto(
                            24L,
                            "https://fr.luxuryestate.com/p97656643-appartement-en-vente-new-york",
                            "Facade"
                    )
            )
            listOfPhoto2.add(
                    HousePhoto(
                            25L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/1/properties/Property-431f0000000005d200025f9be394-97656643.jpg",
                            "Swimming pool"
                    )
            )
            return listOfPhoto2
        }

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #3 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet3() :ArrayList<HousePhoto>{
            val listOfPhoto3 = ArrayList<HousePhoto>()
            listOfPhoto3.add(
                    HousePhoto(
                            3L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/5/properties/Property-d7aa00000000054e00055fa5bc67-89041623.jpg",
                            "Living room"
                    )
            )
            listOfPhoto3.add(
                    HousePhoto(
                            31L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/9/properties/Property-d7aa00000000054e00065ebe7ef0-89041623.jpg",
                            "Kitchen"
                    )
            )
            listOfPhoto3.add(
                    HousePhoto(
                            32L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/13/properties/Property-d7aa00000000054e000a5ebe7ef0-89041623.jpg",
                            "WC"
                    )
            )
            listOfPhoto3.add(
                    HousePhoto(
                            33L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/1/properties/Property-d7aa00000000054e00015fa5bc67-89041623.jpg",
                            "Neighborhood"
                    )
            )
            return listOfPhoto3
        }

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #4 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet4() :ArrayList<HousePhoto>{
            val listOfPhoto4 = ArrayList<HousePhoto>()
            listOfPhoto4.add(
                    HousePhoto(
                            4L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/1/properties/Property-e79900000000055400015ec8cf50-89430503.jpg",
                            "Terrace"
                    )
            )
            listOfPhoto4.add(
                    HousePhoto(
                            41L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/2/properties/Property-e79900000000055400025ec8cf50-89430503.jpg",
                            "Living room"
                    )
            )
            listOfPhoto4.add(
                    HousePhoto(
                            42L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/3/properties/Property-e79900000000055400035ec8cf50-89430503.jpg",
                            "Terrace"
                    )
            )
            listOfPhoto4.add(
                    HousePhoto(
                            43L,
                            "https://fr.luxuryestate.com/p89430503-appartement-en-vente-new-york",
                            "Wine cellar"
                    )
            )
            listOfPhoto4.add(
                    HousePhoto(
                            44L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/8/properties/Property-e79900000000055400085ec8cf50-89430503.jpg",
                            "Bedroom",
                    )
            )
            listOfPhoto4.add(
                    HousePhoto(
                            45L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/13/properties/Property-e799000000000554000d5ec8cf50-89430503.jpg",
                            "Bathroom"
                    )
            )
            listOfPhoto4.add(
                    HousePhoto(
                            46L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/14/properties/Property-e799000000000554000e5ec8cf50-89430503.jpg",
                            "Kitchen"
                    )
            )
            listOfPhoto4.add(
                    HousePhoto(
                            47L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/11/properties/Property-e799000000000554000b5ec8cf50-89430503.jpg",
                            "Facade"
                    )
            )
            return listOfPhoto4
        }

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #5 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet5() :ArrayList<HousePhoto>{
            val listOfPhoto5 = ArrayList<HousePhoto>()
            listOfPhoto5.add(
                    HousePhoto(
                            5L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/1/properties/Property-b68c00000000011d0003554b9f71-18713782.jpg",
                            "Kitchen"
                    )
            )
            listOfPhoto5.add(
                    HousePhoto(
                            51L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/6/properties/Property-b68c00000000011d0007554b9f71-18713782.jpg",
                            "Terrace"
                    )
            )
            listOfPhoto5.add(
                    HousePhoto(
                            52L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/2/properties/Property-b68c00000000011d0004554b9f71-18713782.jpg",
                            "Facade"
                    )
            )
            return listOfPhoto5
        }

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #6 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet6() :ArrayList<HousePhoto>{
            val listOfPhoto6 = ArrayList<HousePhoto>()
            listOfPhoto6.add(
                    HousePhoto(
                            6L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/1/properties/Property-537600000000049900015d64ea56-77166163.jpg",
                            "Facade"
                    )
            )
            listOfPhoto6.add(
                    HousePhoto(
                            61L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/4/properties/Property-537600000000049900035d64e93a-77166163.jpg",
                            "Swimming pool"
                    )
            )
            listOfPhoto6.add(
                    HousePhoto(
                            62L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/5/properties/Property-537600000000049900045d64e93a-77166163.jpg",
                            "Living room"
                    )
            )
            listOfPhoto6.add(
                    HousePhoto(
                            43L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/6/properties/Property-537600000000049900055d64e93a-77166163.jpg",
                            "Office"
                    )
            )
            return listOfPhoto6
        }

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #7 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet7() :ArrayList<HousePhoto>{
            val listOfPhoto7 = ArrayList<HousePhoto>()
            listOfPhoto7.add(
                    HousePhoto(
                            7L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/1/properties/Property-0824a142bb3c7a06fe680fc2d4fc1840-94891005.jpg",
                            "Living room"
                    )
            )
            listOfPhoto7.add(
                    HousePhoto(
                            71L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/7/properties/Property-bf6e860a639e4e8c05652cf22fc57f91-94891005.jpg",
                            "Kitchen"
                    )
            )
            listOfPhoto7.add(
                    HousePhoto(
                            72L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/10/properties/Property-61d7f040c12ff4dc47f96ea4279cdf4b-94891005.jpg",
                            "Bedroom"
                    )
            )
            listOfPhoto7.add(
                    HousePhoto(
                            73L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/11/properties/Property-c5c91761f476af80cf704ef3969cb327-94891005.jpg",
                            "Dressing room"
                    )
            )
            listOfPhoto7.add(
                    HousePhoto(
                            74L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/12/properties/Property-12a70d1009b7f17b1d4ed394f5025c9c-94891005.jpg",
                            "Bathroom",
                    )
            )
            listOfPhoto7.add(
                    HousePhoto(
                            75L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/13/properties/Property-0a3fb3d49faf6fcf0b3fdf7bde0d1538-94891005.jpg",
                            "Bedroom"
                    )
            )
            return listOfPhoto7
        }

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #8 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet8() :ArrayList<HousePhoto>{
            val listOfPhoto8 = ArrayList<HousePhoto>()
            listOfPhoto8.add(
                    HousePhoto(
                            8L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/4/properties/Property-0b8e00000000055500035eca19cc-89493003.jpg",
                            "Living room"
                    )
            )
            listOfPhoto8.add(
                    HousePhoto(
                            81L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/5/properties/Property-0b8e00000000055500045eca19cc-89493003.jpg",
                            "Bathroom"
                    )
            )
            listOfPhoto8.add(
                    HousePhoto(
                            82L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/11/properties/Property-0b8e000000000555000b5eca19cc-89493003.jpg",
                            "Living room"
                    )
            )
            listOfPhoto8.add(
                    HousePhoto(
                            83L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/12/properties/Property-0b8e000000000555000c5eca19cc-89493003.jpg",
                            "Kitchen"
                    )
            )
            return listOfPhoto8
        }

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #9 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet9() :ArrayList<HousePhoto>{
            val listOfPhoto9 = ArrayList<HousePhoto>()
            listOfPhoto9.add(
                    HousePhoto(
                            9L,
                            "https://pic.le-cdn.com/thumbs/1024x768/794/6/properties/Property-7c963a464e52f0a0eeb1b6e7eb447a94-92568645.jpg",
                            "Living room"
                    )
            )
            listOfPhoto9.add(
                    HousePhoto(
                            91L,
                            "https://pic.le-cdn.com/thumbs/1024x768/794/9/properties/Property-69b030422e7b5797b5d1ed2d7a7a4cad-92568645.jpg",
                            "Kitchen"
                    )
            )
            listOfPhoto9.add(
                    HousePhoto(
                            92L,
                            "https://pic.le-cdn.com/thumbs/1024x768/794/12/properties/Property-2b587c7469ad28097641ae30e0d02db8-92568645.jpg",
                            "Pool room"
                    )
            )
            listOfPhoto9.add(
                    HousePhoto(
                            93L,
                            "https://pic.le-cdn.com/thumbs/1024x768/794/1/properties/Property-a92ad10fdfa6bba3173f3ac55b845dc0-92568645.jpg",
                            "Terrace"
                    )
            )
            listOfPhoto9.add(
                    HousePhoto(
                            94L,
                            "https://pic.le-cdn.com/thumbs/1024x768/794/5/properties/Property-b7c7018a1e441818b518c45486d79716-92568645.jpg",
                            "Hall"
                    )
            )
            return listOfPhoto9
        }
    }
}