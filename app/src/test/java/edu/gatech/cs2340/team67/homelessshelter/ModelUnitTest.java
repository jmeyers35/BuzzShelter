package edu.gatech.cs2340.team67.homelessshelter;

import org.junit.Test;
import org.junit.Before;

import java.util.List;
import java.util.NoSuchElementException;

import edu.gatech.cs2340.team67.homelessshelter.Models.Model;
import edu.gatech.cs2340.team67.homelessshelter.Models.Shelter;

import static org.junit.Assert.*;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ModelUnitTest {
    @Before public void getShelterByNameTest_before() {
        Model _model = Model.getInstance();
        _model.getShelters().clear();
    }

    @Test
    public void getShelterByNameTest_isCorrect() {
        String shelter_name_match = "MATCH_SHELTER";
        Shelter shelter_match = new Shelter(0, shelter_name_match, "50", "none", 0.123, 0.123, "123 my address", "555-555-5555");
        Shelter shelter_misc1 = new Shelter(1, "test1", "134", "men",1.6,1.8, "120 Atlanta GA", "555-555-5555");
        Shelter shelter_misc2 = new Shelter(2, "test2", "3", "women", 1.2, 1.5, "150 Atlanta Ga", "555-555-5555");
        Model _model = Model.getInstance();
        List<Shelter> shelterList = _model.getShelters();
        shelterList.clear();
        shelterList.add(shelter_match);
        shelterList.add(shelter_misc1);
        shelterList.add(shelter_misc2);

        Shelter foundShelter = _model.getShelterByName(shelter_name_match);
		assertEquals(foundShelter, shelter_match);
    }
	
	@Test(expected = NoSuchElementException.class)
	public void getShelterByNameTest_notInList()  {
        String shelter_name_match = "MATCH_SHELTER";
        String shelter_name_notInList = "NOT_IN_LIST";
        Shelter shelter_match = new Shelter(0, shelter_name_match, "50", "none", 0.123, 0.123, "123 my address", "555-555-5555");
        Shelter shelter_misc1 = new Shelter(1, "test1", "134", "men",1.6,1.8, "120 Atlanta GA", "555-555-5555");
        Shelter shelter_misc2 = new Shelter(2, "test2", "3", "women", 1.2, 1.5, "150 Atlanta Ga", "555-555-5555");
        Model _model = Model.getInstance();
        List<Shelter> shelterList = _model.getShelters();
        shelterList.clear();
        shelterList.add(shelter_match);
        shelterList.add(shelter_misc1);
        shelterList.add(shelter_misc2);

        Shelter foundShelter = _model.getShelterByName(shelter_name_notInList);
	}
}