package edu.gatech.cs2340.team67.homelessshelter;

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

import org.junit.Before;
import org.junit.Test;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.List;
import java.util.NoSuchElementException;

import edu.gatech.cs2340.team67.homelessshelter.models.Model;
import edu.gatech.cs2340.team67.homelessshelter.models.Shelter;
import edu.gatech.cs2340.team67.homelessshelter.models.User;

import static org.junit.Assert.*;

/**
 * Created by JMadison on 4/16/18.
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({FirebaseDatabase.class})
public class JordanUnitTest {

    private Model model;

    @Before public void setUp() {
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


        //Get list Shelters from the model and clear them

        model = model.getInstance();
        model.getShelters().clear();
    }

    @Test
    public void reserveVacancy_isCorrect() {
        Shelter shelter1 = new Shelter(0, "Test Home", "50", "none", 0.123, 0.123, "123 my address", "555-555-5555");


        //Make a reservation
        List<Shelter> shelterList = model.getShelters();
        shelterList.clear();

        //request 8 beds
        shelter1.reserveVacancy(8);
        shelterList.add(shelter1);

        //Vacancy should equal 50 - 8
        assertEquals(Integer.parseInt(shelter1.getVacancy()), 42 );

    }

    @Test
    public void reserveVacancy_MoreThan() {

        Shelter shelter1 = new Shelter(0, "Test Home", "50", "none", 0.123, 0.123, "123 my address", "555-555-5555");


        //Make a reservation
        List<Shelter> shelterList = model.getShelters();
        shelterList.clear();

        //Should return false
        assertEquals(shelter1.reserveVacancy(11), false);

    }

    @Test
    public void reserveVacancy_isLess() {
        Shelter shelter1 = new Shelter(0, "Test Home", "50", "none", 0.123, 0.123, "123 my address", "555-555-5555");


        //Make a reservation
        List<Shelter> shelterList = model.getShelters();
        shelterList.clear();

        //request all beds
        //Integer.parseInt(shelter1.getVacancy())
        shelter1.reserveVacancy(10);
        shelter1.reserveVacancy(10);
        shelter1.reserveVacancy(10);
        shelter1.reserveVacancy(10);
        shelter1.reserveVacancy(10);


        //Vacancy should throw false
        assertEquals(shelter1.reserveVacancy(10), false );

    }
}
