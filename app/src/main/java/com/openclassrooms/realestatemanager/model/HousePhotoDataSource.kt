package com.openclassrooms.realestatemanager.model

class HousePhotoDataSource {
    companion object {

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #1 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet1(): ArrayList<HousePhoto> {
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

        fun createHousePhotoDataSet2(): ArrayList<HousePhoto> {
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
                            "https://pic.le-cdn.com/thumbs/1024x768/04/12/properties/Property-431f0000000005d2000e5f9be395-97656643.jpg",
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
                            "https://pic.le-cdn.com/thumbs/1024x768/04/5/properties/Property-431f0000000005d200035f9be394-97656643.jpg",
                            "Facade"
                    )
            )
            listOfPhoto2.add(
                    HousePhoto(
                            25L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/15/properties/Property-431f0000000005d200125f9be395-97656643.jpg",
                            "Swimming pool"
                    )
            )
            return listOfPhoto2
        }

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #3 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet3(): ArrayList<HousePhoto> {
            val listOfPhoto3 = ArrayList<HousePhoto>()
            listOfPhoto3.add(
                    HousePhoto(
                            3L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/6/properties/Property-8eb10ae34814890a650a7b36ef6da9e5-90899465.jpg",
                            "Living room"
                    )
            )
            listOfPhoto3.add(
                    HousePhoto(
                            31L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/22/properties/Property-1d7fce4bc6400c55bb2b2c3f1427efee-90899465.jpg",
                            "Kitchen"
                    )
            )
            listOfPhoto3.add(
                    HousePhoto(
                            32L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/13/properties/Property-d80e3eb0c14c9f0221689297d65371da-90899465.jpg",
                            "Bed room"
                    )
            )
            listOfPhoto3.add(
                    HousePhoto(
                            33L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/12/properties/Property-d58c683a16004784deeed87157eb7529-90899465.jpg",
                            "Bed room"
                    )
            )
            return listOfPhoto3
        }

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #4 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet4(): ArrayList<HousePhoto> {
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
                            "https://pic.le-cdn.com/thumbs/1024x768/04/8/properties/Property-e79900000000055400085ec8cf50-89430503.jpg",
                            "Bedroom",
                    )
            )
            listOfPhoto4.add(
                    HousePhoto(
                            44L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/13/properties/Property-e799000000000554000d5ec8cf50-89430503.jpg",
                            "Bathroom"
                    )
            )
            listOfPhoto4.add(
                    HousePhoto(
                            45L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/14/properties/Property-e799000000000554000e5ec8cf50-89430503.jpg",
                            "Kitchen"
                    )
            )
            listOfPhoto4.add(
                    HousePhoto(
                            46L,
                            "https://pic.le-cdn.com/thumbs/1024x768/04/11/properties/Property-e799000000000554000b5ec8cf50-89430503.jpg",
                            "Facade"
                    )
            )
            return listOfPhoto4
        }

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #5 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet5(): ArrayList<HousePhoto> {
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

        fun createHousePhotoDataSet6(): ArrayList<HousePhoto> {
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

        fun createHousePhotoDataSet7(): ArrayList<HousePhoto> {
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

        fun createHousePhotoDataSet8(): ArrayList<HousePhoto> {
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
                            "https://pic.le-cdn.com/thumbs/1024x768/08/11/properties/Property-ca9da726012b8b2ce688d2510e589ae7-103063565.jpg",
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

        fun createHousePhotoDataSet9(): ArrayList<HousePhoto> {
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

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #10 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet10(): ArrayList<HousePhoto> {
            val listOfPhoto10 = ArrayList<HousePhoto>()
            listOfPhoto10.add(
                    HousePhoto(
                            100L,
                            "https://pic.le-cdn.com/thumbs/1024x768/794/2/properties/Property-f2add1b6d3c418d3e686b716a130c81e-92568845.jpg",
                            "Living room"
                    )
            )
            listOfPhoto10.add(
                    HousePhoto(
                            101L,
                            "https://pic.le-cdn.com/thumbs/1024x768/794/8/properties/Property-0665124d7a584de00c8aa726f572205a-92568845.jpg",
                            "Kitchen"
                    )
            )
            listOfPhoto10.add(
                    HousePhoto(
                            102L,
                            "https://pic.le-cdn.com/thumbs/1024x768/794/6/properties/Property-7670d7e83a61634352ec2816174b83b0-92568845.jpg",
                            "Living room"
                    )
            )
            return listOfPhoto10
        }

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #11 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet11(): ArrayList<HousePhoto> {
            val listOfPhoto11 = ArrayList<HousePhoto>()
            listOfPhoto11.add(
                    HousePhoto(
                            110L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/1/properties/Property-6c612a5874cf5fc48a9c4e4658fa6ccf-96708465.jpg",
                            "Living room"
                    )
            )
            listOfPhoto11.add(
                    HousePhoto(
                            111L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/16/properties/Property-2a29b43ce16a9de61940e601a9ce5282-96708465.jpg",
                            "Terrace"
                    )
            )
            listOfPhoto11.add(
                    HousePhoto(
                            112L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/9/properties/Property-b5ba9859001495dfcca9e115d67cf6f6-96708465.jpg",
                            "Bed room"
                    )
            )
            return listOfPhoto11
        }

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #12 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet12(): ArrayList<HousePhoto> {
            val listOfPhoto12 = ArrayList<HousePhoto>()
            listOfPhoto12.add(
                    HousePhoto(
                            120L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/1/properties/Property-c9850c48d481c8c0fd4a10a4a00168b5-101415865.jpg",
                            "Living room"
                    )
            )
            listOfPhoto12.add(
                    HousePhoto(
                            121L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/5/properties/Property-f454215afd3cd3f4e9d35cc8b8093abe-101415865.jpg",
                            "Bed room"
                    )
            )
            listOfPhoto12.add(
                    HousePhoto(
                            122L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/10/properties/Property-27994131f903538f1d20406e87ec6420-101415865.jpg",
                            "Terrace"
                    )
            )
            return listOfPhoto12
        }

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #13 ------------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet13(): ArrayList<HousePhoto> {
            val listOfPhoto13 = ArrayList<HousePhoto>()
            listOfPhoto13.add(
                    HousePhoto(
                            130L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/4/properties/Property-eb4978bb7108583f6a0bdfc715cb2b3b-105248805.jpg",
                            "Dinning room"
                    )
            )
            listOfPhoto13.add(
                    HousePhoto(
                            131L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/5/properties/Property-281fdbf82e7276a87c92c1fe1661d881-105248805.jpg",
                            "Kitchen"
                    )
            )
            listOfPhoto13.add(
                    HousePhoto(
                            132L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/6/properties/Property-9b92ad7aaff3db71b5e6e4481aae0a6c-105248805.jpg",
                            "Garden"
                    )
            )
            listOfPhoto13.add(
                    HousePhoto(
                            133L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/7/properties/Property-8950abf75e67e7abafb02ecf3fe623e6-105248805.jpg",
                            "Bed room"
                    )
            )
            listOfPhoto13.add(
                    HousePhoto(
                            134L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/8/properties/Property-e41d13c68ea0a529c6ea6391a2608a5b-105248805.jpg",
                            "Bath room"
                    )
            )
            listOfPhoto13.add(
                    HousePhoto(
                            135L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/9/properties/Property-6781ffabc224fcf8c5b6c8e3ac18781c-105248805.jpg",
                            "Bed room"
                    )
            )
            listOfPhoto13.add(
                    HousePhoto(
                            136L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/10/properties/Property-b15488bfec24bc9e032dd9a93293e10d-105248805.jpg",
                            "Bed room"
                    )
            )
            return listOfPhoto13
        }

        //------------------------------------------------------------------------------------------
        //-------------------------------- House #14 -----------------------------------------------
        //------------------------------------------------------------------------------------------

        fun createHousePhotoDataSet14(): ArrayList<HousePhoto> {
            val listOfPhoto14 = ArrayList<HousePhoto>()
            listOfPhoto14.add(
                    HousePhoto(
                            140L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/5/properties/Property-9b96a9edbac88589344f8343bf0210b1-87213305.jpg",
                            "Living room"
                    )
            )
            listOfPhoto14.add(
                    HousePhoto(
                            141L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/8/properties/Property-49c0dc7377906eed3e35ca66d84a7500-87213305.jpg",
                            "Terrace"
                    )
            )
            listOfPhoto14.add(
                    HousePhoto(
                            142L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/13/properties/Property-7ca97e15e30b2350e7e28df900467322-87213305.jpg",
                            "Bed room"
                    )
            )
            listOfPhoto14.add(
                    HousePhoto(
                            143L,
                            "https://pic.le-cdn.com/thumbs/1024x768/08/16/properties/Property-500b9fd20d9d730ee09a38a87316df77-87213305.jpg",
                            "Bath room"
                    )
            )
            return listOfPhoto14
        }
    }
}