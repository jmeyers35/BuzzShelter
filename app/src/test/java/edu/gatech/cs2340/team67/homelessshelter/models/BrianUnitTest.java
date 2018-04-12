package edu.gatech.cs2340.team67.homelessshelter.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import static org.mockito.Matchers.anyString;

import static org.mockito.Mockito.when;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import org.junit.Test;
import org.junit.Before;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;


/**
 * Unit Testing for finding shelters in our model
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({FirebaseDatabase.class})
public class BrianUnitTest {
    private Model _model;
    @Before public void getShelterByNameTest_before() {
        // Need a mock DatabaseReference and FirebaseReference
        DatabaseReference mockReference = Mockito.mock(DatabaseReference.class);
        FirebaseDatabase mockedDatabase = Mockito.mock(FirebaseDatabase.class);
        /* These lines make it so that when _database.getReference() or _database.getReference(String) is called (e.g.
         * Model line 61), the mock DatabaseReference is returned.
         */
        when(mockedDatabase.getReference(anyString())).thenReturn(mockReference);
        when(mockedDatabase.getReference()).thenReturn(mockReference);

        /* These lines make sure the mock FirebaseDatabase is returned when FirebaseDatabase.getInstance
         * is called (e.g. Model line 51).
         */
        PowerMockito.mockStatic(FirebaseDatabase.class);
        when(FirebaseDatabase.getInstance()).thenReturn(mockedDatabase);

        // When a DatabaseReference has .child() called on it (e.g. Model line 62)
        when(mockReference.child(anyString())).thenReturn(mockReference);

        _model = Model.getInstance();
        _model.getShelters().clear();
    }

    @Test
    public void getShelterByNameTest_isCorrect() {
        String shelter_name_match = "MATCH_SHELTER";
        Shelter shelter_match = new Shelter(0, shelter_name_match, "50", "none", 0.123, 0.123, "123 my address", "555-555-5555");
        Shelter shelter_misc1 = new Shelter(1, "test1", "134", "men",1.6,1.8, "120 Atlanta GA", "555-555-5555");
        Shelter shelter_misc2 = new Shelter(2, "test2", "3", "women", 1.2, 1.5, "150 Atlanta Ga", "555-555-5555");

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

        List<Shelter> shelterList = _model.getShelters();
        shelterList.clear();
        shelterList.add(shelter_match);
        shelterList.add(shelter_misc1);
        shelterList.add(shelter_misc2);

        Shelter foundShelter = _model.getShelterByName(shelter_name_notInList);
	}
}