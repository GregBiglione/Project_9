package com.openclassrooms.realestatemanager;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.database.dao.RealEstateManagerDatabase;
import com.openclassrooms.realestatemanager.model.Agent;
import com.openclassrooms.realestatemanager.model.Property;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class DaoTest {

    // For data
    private RealEstateManagerDatabase db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception {
        db = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                RealEstateManagerDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception {
        db.close();
    }

    // Data set for test
    private static final Agent AGENT_TEST1 = new Agent(
            "https://wallpapertag.com/wallpaper/full/a/2/0/569247-cool-terminator-2-wallpaper-1920x1200.jpg",
            "Schwarzenegger",
            "Arnold",
            "+1 260 222 8751",
            "aschwarzenegger79@gmail.com");


    private static final Agent AGENT_TEST2 = new Agent(
            "https://www.everythingaction.com/wp-content/uploads/2011/08/Cobra.1986.BDRip_.720p.01.png",
            "Stallone",
            "Sylvester",
            "+1 530 444 7842",
            "sylvesterstallonecobra@gmail.com");

    String phoneUpdated = "+1 440 222 8751";

    private static final Property PROPERTY_TEST1 = new Property(
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
            (long) 24540000,
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
            (long) 1597844394,
            null,
            (long) 1
    );

    private static final Property PROPERTY_TEST2 = new Property(
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
            (long) 51730800,
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
            (long) 1603204794,
            null,
            (long) 1
    );

    String proximityPointOfInterest4 = "Museum";

    @Test
    public void createAndGetAllAgents() throws InterruptedException{
        // Before add agent
        db.getAgentDao().createAgent(AGENT_TEST1);
        db.getAgentDao().createAgent(AGENT_TEST2);
        // Test
        List<Agent> agents = LiveDataTestUtil.getValue(db.getAgentDao().getAllAgents());
        assertEquals(2, agents.size());
    }

    @Test
    public void createAndUpdateAgent() throws InterruptedException{
        // Before add agent
        db.getAgentDao().createAgent(AGENT_TEST1);
        Agent agentUpdated = AGENT_TEST1;
        agentUpdated.setPhoneNumber(phoneUpdated);
        db.getAgentDao().updateAgent(agentUpdated);
        // Test
        List<Agent> agents = LiveDataTestUtil.getValue(db.getAgentDao().getAgent(1));
        assertEquals(agents.get(0).getPhoneNumber(), 1, agents.size());
    }

    @Test
    public void createAndDeleteAgent() throws InterruptedException{
        // Before add agent
        db.getAgentDao().createAgent(AGENT_TEST1);
        db.getAgentDao().createAgent(AGENT_TEST2);
        Agent agentToDelete = AGENT_TEST1;
        db.getAgentDao().deleteAgent(agentToDelete);
        // Test
        List<Agent> agents = LiveDataTestUtil.getValue(db.getAgentDao().getAgent(1));
        assertEquals(1, agents.size());
    }

    @Test
    public void createAndGetAllProperties() throws InterruptedException{
        // Before add agent before a property
        db.getAgentDao().createAgent(AGENT_TEST1);
        db.getPropertyDao().createProperty(PROPERTY_TEST1);
        db.getPropertyDao().createProperty(PROPERTY_TEST2);
        // Test
        List<Property> properties = LiveDataTestUtil.getValue(db.getPropertyDao().getAllProperties());
        assertEquals(2, properties.size());
    }

    @Test
    public void createAndUpdateProperty() throws InterruptedException{
        // Before add agent before a property
        db.getAgentDao().createAgent(AGENT_TEST1);
        db.getPropertyDao().createProperty(PROPERTY_TEST1);
        Property propertyToUpdate = PROPERTY_TEST1;
        propertyToUpdate.setProximityPointsOfInterest4(proximityPointOfInterest4);
        db.getPropertyDao().updateProperty(propertyToUpdate);
        // Test
        List<Property> properties = LiveDataTestUtil.getValue(db.getPropertyDao().getProperty(1));
        assertEquals(properties.get(0).getProximityPointsOfInterest4(), 1, properties.size());
    }
}
