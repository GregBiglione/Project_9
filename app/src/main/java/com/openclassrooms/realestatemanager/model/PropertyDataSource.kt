package com.openclassrooms.realestatemanager.model

import kotlin.collections.ArrayList

class PropertyDataSource {
    companion object{
        fun createPropertyDataSet(): ArrayList<Property>{
            val listOfProperty = ArrayList<Property>()
            listOfProperty.add(
                    Property(
                            "https://pic.le-cdn.com/thumbs/1024x768/04/1/properties/Property-b2660000000001e2000157b5fd0a-31614642.jpg",
                            "Living room",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/2/properties/Property-b2660000000001e2000257b5fd0a-31614642.jpg",
                            "Terrace",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/3/properties/Property-b2660000000001e2000357b5fd0a-31614642.jpg",
                            "Fitness room",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/5/properties/Property-b2660000000001e2000557b5fd0a-31614642.jpg",
                            "Swimming pool",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/8/properties/Property-b2660000000001e2000857b5fd0a-31614642.jpg",
                            "Bed room",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/9/properties/Property-b2660000000001e2000957b5fd0a-31614642.jpg",
                            "Kitchen",
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            "Penthouse",
                            "Financial District",
                            "",
                            24540000,
                            313,
                            12,
                            3,
                            3,
                            "Massive half-floor penthouse with 176m2 square foot terrace features a wall of curved glass windows offering a breathtaking panoramic view of the Hudson River, NY Harbor, Statue of Liberty, World Trade Center and Manhattan Skyline. \n" +
                                    "\n" +
                                    "The L-Series, a collection of 50 West Streets penthouses, full and half ground floor residences, offers an impressive array of additional features. The kitchens, featuring stone slab counters and backsplashes, are equipped with an extra spacious refrigerator and freezer, a six-burner stove, and a full-height wine refrigerator. Large marble stone slabs, a freestanding soaking tub, steam shower and a benched sauna enhance the elegant master bathroom. West, a 64-story residential tower located in the center of New Downtown, offers breathtaking views of New York Harbor, the Hudson and East Rivers, the Statue of Liberty and Ellis Island. \n" +
                                    "\n" +
                                    "Internationally renowned architect Helmut Jahn designed the approximately 780' skyscraper to feature floor-to-ceiling curved glass windows. The extensive interior layouts, ranging from one to five bedrooms and featuring a network of duplexes and double height spaces, were designed and finished by Thomas Juul-Hansen. \n" +
                                    "\n" +
                                    "Four floors of the tower are devoted to state-of-the-art amenities: a huge fitness center, the beautifully appointed Water Club, unique childrens amenities, and the West Street Observatory, a spectacular 64th floor outdoor entertainment space with seemingly endless views of New York City and beyond. penthouse.",
                            true,
                            "Available",
                            "Sea",
                            "Shops",
                            "Park",
                            null,
                            null,
                            1597844394,
                            null,
                            5
                    )
            )
            listOfProperty.add(
                    Property(
                            "https://pic.le-cdn.com/thumbs/1024x768/04/4/properties/Property-431f0000000005d2000a5f9be394-97656643.jpg",
                            "Living room",
                            "https://fr.luxuryestate.com/p97656643-appartement-en-vente-new-york",
                            "Terrace",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/15/properties/Property-431f0000000005d200115f9be395-97656643.jpg",
                            "Bathroom",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/17/properties/Property-431f0000000005d200135f9be395-97656643.jpg",
                            "Bedroom",
                            "https://fr.luxuryestate.com/p97656643-appartement-en-vente-new-york",
                            "Facade",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/1/properties/Property-431f0000000005d200025f9be394-97656643.jpg",
                            "Swimming pool",
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            "Apartment",
                            "Broadway",
                            "",
                            51730800,
                            769,
                            17,
                            7,
                            5,
                            "New York's most sophisticated, stunning and elegant penthouse.\n" +
                                    "Magnificent view of Central Park from this 6 bedroom apartment graciously combined. Magical views from every room - Central Park, Hudson River and Open City - the apartment is surrounded by light and green, as well as sparkling water and boats passing by during the day and sparkling city lights all around at night. The carefully designed floor plan creates two separate bedroom wings and an entertainment space that can appear open and lofty or be elegantly divided into more discreet rooms for formal receptions. The residence is in triple new condition and is beautifully decorated. It is also quite unique in the building.\n" +
                                    "\n" +
                                    "This approx. 769 square meter apartment is a rare combination of large space, incredible light, spectacular views and excellence in design and execution.\n" +
                                    "The level of custom finishes is unsurpassed, starting with the inlaid stone floors of the private elevator entrance and continuing into the spectacular 42-foot-long living room with floor-to-ceiling windows overlooking Central Park, double fireplaces, Bubinga wood doors and floors, and Padouk wood beams and moldings. Next to the living room is a beautiful wood-panelled sitting area with fireplace. Next to the den is a spectacular 22-foot dining room with beautiful marble floors, textured skin walls and a breathtaking view of the city and Central Park.\n" +
                                    "\n" +
                                    "The professional chef's kitchen has a center island with sink and gas cooktop, high-end appliances, marble countertops and custom sycamore wood cabinets . A cozy breakfast nook next to the kitchen leads to a northwest facing multimedia room with a view of the Hudson River. A staff bedroom with ensuite bathroom is also located in this wing of the apartment.\n" +
                                    "\n" +
                                    "Flanking the other side of the living room is the library with leather upholstered walls and a fireplace. Next to the library is the corner master suite. This suite is unsurpassed in terms of comfort and luxury. There are two separate marble bathrooms and two walk-in closets with exquisite custom cabinets offering generous storage. Passing past the master suite to the west wing are four additional bedrooms with en-suite bathrooms. One of the bedrooms is currently a home gym and another is configured as a golf simulator.\n" +
                                    "\n" +
                                    "The apartment is fully wired for technology, including central air conditioning and heating zones, Crestron system, security system, touch panels in each room that manage lighting, temperature and electronic blinds.\n" +
                                    "The entire apartment is wired with electric blinds, and all rooms have opaque and solar blinds. The apartment also has central heating and air conditioning, plenty of storage space and an AV audio system with speakers throughout.\n" +
                                    "\n" +
                                    "Condominium with white gloves. The residences have access to all the five-star amenities of the Mandarin, including a 24-hour concierge, maid service, room service, valet parking, doorman, a 3990 m2 fitness center with a 45-meter enclosed swimming pool and the Spa.\n" +
                                    "\n",
                            true,
                            "Available",
                            "Metro",
                            "Shops",
                            "Park",
                            null,
                            null,
                            1603204794,
                            null,
                            2
                    )
            )
            listOfProperty.add(
                    Property(
                            "https://pic.le-cdn.com/thumbs/1024x768/04/5/properties/Property-d7aa00000000054e00055fa5bc67-89041623.jpg",
                            "Living romm",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/9/properties/Property-d7aa00000000054e00065ebe7ef0-89041623.jpg",
                            "Kitchen",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/13/properties/Property-d7aa00000000054e000a5ebe7ef0-89041623.jpg",
                            "WC",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/1/properties/Property-d7aa00000000054e00015fa5bc67-89041623.jpg",
                            "Neighborhood",
                            null,
                            null,
                            null, null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            "Apartment",
                            "Central Park",
                            "",
                            53146800,
                            657,
                            12,
                            5,
                            5,
                            "Experience the ultimate in privacy and luxury living in this grand full-floor residence at Central Park Tower. This expansive home in the sky features over 100 feet of Central Park frontage. The custom-designed curtain wall frames Central Park views to the north, the famed Manhattan skyline to the south and the City's waterways to the east and west.\n" +
                                    "\n" +
                                    "The five bedroom, five-and-a-half-bathroom simplex home offers unparalleled scale and proportion. The gracious entry foyer leads to a Grand Salon that provides the quintessential setting for formal entertaining. This vast, 1,550 square-foot room is the centerpiece of this exceptional home, with soaring 11'4 ceilings and unobstructed views in three directions. The corner gourmet kitchen features custom cabinetry by Smallbone of Devizes, premium Miele appliances and an adjacent family room. A formal library and four of the homes en-suite bedrooms enjoy unmatched Central Park views.\n" +
                                    "\n" +
                                    "The dramatic master bedroom suite wraps the entire western portion of the residence and includes more than 800 square feet of private living quarters. The suite includes a corner boudoir with unobstructed south and west views. An opulent seven-fixture bathroom with dual water closets and showers as well as a separate soaking tub. Two oversized dressing rooms and two additional walk-in-closets provide for the ultimate in wardrobe storage. A corner sitting room featuring captivating Central Park and Hudson River views completes this private oasis.\n" +
                                    "\n" +
                                    "Central Park will offer the most elevated residences in New York City. The towers vast footprint offered the unique opportunity to create the most gracious layouts and exceptional floor plans. The definitive aspect of living in Central Park Tower is the extraordinary views. The building was designed from the inside out to maximize these incomparable panoramas.",
                            true,
                            "Available",
                            "Sea",
                            "Shops",
                            "Park",
                            "School",
                            null,
                            1589539194,
                            null,
                            2
                    )
            )
            listOfProperty.add(
                    Property(
                            "https://pic.le-cdn.com/thumbs/1024x768/04/1/properties/Property-e79900000000055400015ec8cf50-89430503.jpg",
                            "Terrace",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/2/properties/Property-e79900000000055400025ec8cf50-89430503.jpg",
                            "Living room",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/3/properties/Property-e79900000000055400035ec8cf50-89430503.jpg",
                            "Terrace",
                            "https://fr.luxuryestate.com/p89430503-appartement-en-vente-new-york",
                            "Wine cellar",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/8/properties/Property-e79900000000055400085ec8cf50-89430503.jpg",
                            "Bedroom",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/9/properties/Property-e79900000000055400095ec8cf50-89430503.jpg",
                            "Bedroom",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/13/properties/Property-e799000000000554000d5ec8cf50-89430503.jpg",
                            "Bathroom",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/14/properties/Property-e799000000000554000e5ec8cf50-89430503.jpg",
                            "Kitchen",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/11/properties/Property-e799000000000554000b5ec8cf50-89430503.jpg",
                            "Facade",
                            null,
                            null,
                            "Apartment",
                            "Broadway",
                            "",
                            63560200,
                            1840,
                            26,
                            14,
                            11,
                            "With only three houses per floor and a limited number of half- and full-storey homes, offering privacy on a large scale. Each one to four-bedroom residence offers a corner view benefiting from bay windows and spectacular ceiling heights from 11 to 23 feet, maximizing light and air and enhancing views from multiple exposures.\n" +
                                    "\n" +
                                    "The residences have been meticulously crafted, kitchens are equipped with Miele and SubZero appliances, while private bathrooms and powder rooms feature Dornbracht appliances and luxurious natural stone surfaces. All houses have been pre-wired for future smart home customization and electric blinds.\n" +
                                    "\n" +
                                    "24-hour doorman and concierge services on site, as well as a sunny living room and an adjacent outdoor area for reflection and relaxation. On the Health and Wellness floors, additional amenities include a 67-foot saltwater swimming pool, spa and steam rooms, a fitness club, a children's playroom and a club room. A pet spa as well as additional storage and bicycle storage will also be available.\n" +
                                    "\n" +
                                    "Artist renderings reflect the planned scale and interior design and are subject to the Sponsor's right to change specifications and design of materials. The views presented are approximate and will vary depending on the unit and floor. Not all residences contain the same specifications for materials, finishes and fixtures.\n" +
                                    "\n" +
                                    "Translated with www.DeepL.com/Translator (free version)",
                            true,
                            "Available",
                            "Metro",
                            "Shops",
                            "Park",
                            "School",
                            "Hospital",
                            1590251994,
                            null,
                            3
                    )
            )
            listOfProperty.add(
                    Property(
                            "https://pic.le-cdn.com/thumbs/1024x768/04/1/properties/Property-b68c00000000011d0003554b9f71-18713782.jpg",
                            "Kitchen",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/6/properties/Property-b68c00000000011d0007554b9f71-18713782.jpg",
                            "Terrace",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/2/properties/Property-b68c00000000011d0004554b9f71-18713782.jpg",
                            "Facade",
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            "Private Mansion",
                            "West Vilage",
                            "29 Downing St, New York, NY 10014",
                            10923500,
                            3480,
                            12,
                            3,
                            2,
                            "In 1977, SELLERS arrived in New York from abroad with money and a dream. PROSPECTIVE CREATIVES FOR ONLY ONE GRANIER TO LIVE, they instead brought to a \"29\" by a friend and instantly became LOVE-TERRASSE. \n" +
                                    "\n" +
                                    "At the time, it HAD a light bulb on each level, no bathroom, and only a PIGNIGNON of water, but they saw their VISION; ONE STUDIO ART BELOW AND TWO LEVELS dominating residential building a life. NEARLY 40 YEARS LATER, it is now your turn. If you travel LOW HISTORICAL Downing Street, you will see that the 25 foot wide, TROTTOIR-CUT 29 Downing one of the only constants that remain in ORIGINAL form. Immediately recognizable, the LOVE of neighbors and passers-by is apparent. \n" +
                                    "\n" +
                                    "A Carriage House first built in 1829 on land previously owned by the Third Vice President of the United States, Aaron Burr, this house offers the white canvas to restore or recreate the life of your dreams. \n" +
                                    "\n" +
                                    "IN VIDEO shoots for MAJOR AND PRINCIPAL fashion brands, the purchase of this special home means that you will be buying a property that you can't find anywhere else in the world. You'll be buying a truly unique piece OF ART, ONE OF MANHATTAN'S CERTIFIED TREASURES and in the historic neighborhood of one of the most famous and sought-after neighborhoods in the world.",
                            true,
                            "Available",
                            "Metro",
                            "Restaurant",
                            "Metro",
                            "Shops",
                            "Park",
                            1588945194,
                            null,
                            4
                    )
            )
            listOfProperty.add(
                    Property(
                            "https://pic.le-cdn.com/thumbs/1024x768/04/1/properties/Property-537600000000049900015d64ea56-77166163.jpg",
                            "Facade",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/3/properties/Property-537600000000049900025d64e93a-77166163.jpg",
                            "Hall",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/4/properties/Property-537600000000049900035d64e93a-77166163.jpg",
                            "Swimming pool",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/5/properties/Property-537600000000049900045d64e93a-77166163.jpg",
                            "Living room",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/6/properties/Property-537600000000049900055d64e93a-77166163.jpg",
                            "Office",
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            "Prestige Residence",
                            "East New York",
                            "29 Downing St, New York, NY 10014",
                            10923500,
                            2323,
                            19,
                            7,
                            9,
                            "A 25,000 square foot stone mansion on a fenced property of approximately 4.5 acres surrounded by other multi-million dollar homes and only a 25 minute drive from New York City.\n" +
                                    "\n" +
                                    "Impeccable construction for the royalty Large gala reception area Commercial quality construction with poured cement and steel A green home with solar and geothermal energy Radiant heat throughout through the judicious use of rare wood, stone and marble Hand carved stone fireplaces with custom design and imported imported stone fireplaces Indoor swimming pool with 24 foot Water Falls Pool Entrance features Porte-cochère with large parking in the yard. Regulation USTA quality tennis court with lights. Massive terraces in all suites and main reception room. \n" +
                                    "\n" +
                                    "Hammam basketball court. Walking distance to an upscale outdoor shopping mall Walking distance to a private preschool to grade 12 Exterior Smart Corinthian Stone House House with interior and exterior security Crestron System Built for the latest generations",
                            true,
                            "Available",
                            "Swimming pool",
                            "Sea",
                            "Park",
                            null,
                            null,
                            1597846674,
                            null,
                            1
                    )
            )
            listOfProperty.add(
                    Property(
                            "https://pic.le-cdn.com/thumbs/1024x768/08/1/properties/Property-0824a142bb3c7a06fe680fc2d4fc1840-94891005.jpg",
                            "Living room",
                            "https://pic.le-cdn.com/thumbs/1024x768/08/7/properties/Property-bf6e860a639e4e8c05652cf22fc57f91-94891005.jpg",
                            "Kitchen",
                            "https://pic.le-cdn.com/thumbs/1024x768/08/10/properties/Property-61d7f040c12ff4dc47f96ea4279cdf4b-94891005.jpg",
                            "Bedroom",
                            "https://pic.le-cdn.com/thumbs/1024x768/08/11/properties/Property-c5c91761f476af80cf704ef3969cb327-94891005.jpg",
                            "Dressing room",
                            "https://pic.le-cdn.com/thumbs/1024x768/08/12/properties/Property-12a70d1009b7f17b1d4ed394f5025c9c-94891005.jpg",
                            "Bathroom",
                            "https://pic.le-cdn.com/thumbs/1024x768/08/13/properties/Property-0a3fb3d49faf6fcf0b3fdf7bde0d1538-94891005.jpg",
                            "Bedroom",
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            "Luxury House",
                            "Upper East Side",
                            "901 Lexington Ave, Manhattan, NY 10065",
                            2516600,
                            258,
                            8,
                            4,
                            3,
                            "MAJOR PRICE REDUCTION! This bright, sunny, high-floor, 8-room apartment, superbly renovated by L.C. Pei, Architects, in a modern but traditional taste, has a flexible and gracious floor plan. \n" +
                                    "\n" +
                                    "One enters the apartment from a private elevator landing into an entrance foyer leading to a powder room, a coat closet, an intimate study or 4th bedroom, and the large, open, living/dining room with adjacent library. All three reception rooms have wood burning fireplaces and over-scale, six over six, windows with open western views. \n" +
                                    "\n" +
                                    "A corridor, to the east of the living room, leads to a double bedroom, a large master bedroom with dressing room, a spectacular and cheery eat-in kitchen, a third bedroom, and a generous laundry. All three of the bedrooms have beautiful en suite bathrooms. \n" +
                                    "\n" +
                                    "This property is in pristine condition and has thru-the-wall air conditioning. Pet friendly building. Flip tax: 3% by purchaser. Built in 1907, 901 Lexington Avenue was designed by George Mott Pollard, the renowned architect of the Hotel des Artistes. In addition to a full-time doorman/elevator man, there is a live-in superintendent. The building has a bike room, a laundry and private storage bins for tenants in the basement.",
                            true,
                            "Sold",
                            "Metro",
                            "Shops",
                            "Park",
                            null,
                            null,
                            null,
                            1599661734,
                            5
                    )
            )
            listOfProperty.add(
                    Property(
                            "https://pic.le-cdn.com/thumbs/1024x768/04/4/properties/Property-0b8e00000000055500035eca19cc-89493003.jpg",
                            "Living room",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/5/properties/Property-0b8e00000000055500045eca19cc-89493003.jpg",
                            "Bathroom",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/11/properties/Property-0b8e000000000555000b5eca19cc-89493003.jpg",
                            "Living room",
                            "https://pic.le-cdn.com/thumbs/1024x768/04/12/properties/Property-0b8e000000000555000c5eca19cc-89493003.jpg",
                            "Kitchen",
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            "Luxury Apartment",
                            "Civic Center",
                            "",
                            53621500,
                            741,
                            12,
                            4,
                            4,
                            "Unique Sumptuous Penthouse Central Park New York\n" +
                                    "Just moments from Fifth Avenue and high above The Museum of Modern Art, a modern, elegantly tapering tower soars. Masterfully designed West 53 is a dramatic and refreshingly exuberant addition to the skyline and the cultural life of the city as it welcomes the expansion of The Museum of Modern Art. Behind its striking façade are a collection of impeccably detailed residences designed by renowned interior architect Thierry Despont, each spacious and light-filled, with panoramic views.\n" +
                                    "\n" +
                                    "West 53 combines world-class service and unique privileges with architectural distinction. The suite of in-house services and amenities include a 24-hour doorman and concierge, porter services, elevator starter, and 65-foot lap pool, squash court, golf simulator, private screening room, and a double height lounge with private dining room overlooking Central Park. Residents will also receive an exclusive Benefactor Membership to The Museum of Modern Art downstairs.",
                            true,
                            "Available",
                            "Metro",
                            "Shops",
                            "Park", "School",
                            null,
                            1590330954,
                            null,
                            4
                    )
            )
            listOfProperty.add(
                    Property(
                            "https://pic.le-cdn.com/thumbs/1024x768/794/6/properties/Property-7c963a464e52f0a0eeb1b6e7eb447a94-92568645.jpg",
                            "Living room",
                            "https://pic.le-cdn.com/thumbs/1024x768/794/9/properties/Property-69b030422e7b5797b5d1ed2d7a7a4cad-92568645.jpg",
                            "Kitchen",
                            "https://pic.le-cdn.com/thumbs/1024x768/794/12/properties/Property-2b587c7469ad28097641ae30e0d02db8-92568645.jpg",
                            "Pool room",
                            "https://pic.le-cdn.com/thumbs/1024x768/794/1/properties/Property-a92ad10fdfa6bba3173f3ac55b845dc0-92568645.jpg",
                            "Terrace",
                            "https://pic.le-cdn.com/thumbs/1024x768/794/5/properties/Property-b7c7018a1e441818b518c45486d79716-92568645.jpg",
                            "Hall",
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            null,
                            "Luxury Apartment",
                            "Lenox Hill",
                            "515 Park Avenue, New York, NY, 10022",
                            27728800,
                            560,
                            14,
                            5,
                            7,
                            "This mansion in the sky boasts 2000 square feet of spectacular terraces overlooking Park Avenue as well as 6000 square feet of beautiful interiors in this huge duplex in pristine condition in a premier full-service condominium building. Enter through a private elevator that leads to an impressive gallery that leads to a large corner living room with a fireplace and patio doors opening onto the terraces. \n" +
                                    "\n" +
                                    "Immediately adjacent is a large and elegant formal dining room and a beautiful billiard room/library. There is a huge kitchen with high end appliances, and adjoining the chef's kitchen is a spacious breakfast room with patio doors opening onto the terraces. \n" +
                                    "\n" +
                                    "The second floor also offers a shower room, dry bar and wine cellar, laundry room, bedroom and full bathroom, perfect for staff or guests. A beautiful and spectacular custom designed wrought iron staircase leads to the second floor, where there are five bedrooms, all with en-suite bathrooms, including a huge master suite with a luxurious bathroom and a large dressing room. One of the bedrooms is currently an exquisite home office with red lacquered walls and bookcases. \n" +
                                    "\n" +
                                    "A home theater/media drama room completes the second floor of this huge duplex. This elegant duplex is flooded with sunshine and offers unobstructed views of the city in several directions from its advantageous corner exposures. The apartment features distinguished architectural details, beautiful hardwood floors and high ceilings. \n" +
                                    "\n" +
                                    "There is a large closet and storage space throughout the apartment. Included in this offer is a separate studio apartment on the third floor, perfect for staff or guests or as a private home office, and it also includes an individual wine storage unit. 515 Park Avenue is one of Manhattan's most sought-after condominiums and is perfectly positioned at the corner of Park Avenue and 60th Street.",
                            true,
                            "Available",
                            "Metro",
                            "Shops",
                            "Park",
                            null,
                            null,
                            1595339154,
                            null,
                            5
                    )
            )
            return listOfProperty
        }
    }
}
