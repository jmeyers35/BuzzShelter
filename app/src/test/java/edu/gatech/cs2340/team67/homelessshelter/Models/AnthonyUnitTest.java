package edu.gatech.cs2340.team67.homelessshelter.Models;

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

import static org.junit.Assert.*;

/**
 * Created by Anthony Teachey on 4/11/18.
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({FirebaseDatabase.class})
public class AnthonyUnitTest {
    private Model model;

    @Before
    public void getReady() {
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
        model = Model.getInstance();
        model.getShelters().clear();
    }

    @Test
    public void testReserveBedsTrue() {
        User u1 = new User("tyler1@hehexd.com", false);
        model.addUser(u1);
        Shelter s1 = new Shelter(0, "name", "10", "restrictions", 0.0, 0.0, "address", "phone");
        List<Shelter> theShelters = model.getShelters();
        theShelters.clear();
        theShelters.add(s1);
        assertEquals(u1.reserveBeds(1, s1), true);
    }

    @Test
    public void testReserveBedsFalse() {
        User u1 = new User("tyler1@hehexd.com", false);
        model.addUser(u1);
        Shelter s1 = new Shelter(0, "name", "10", "restrictions", 0.0, 0.0, "address", "phone");
        List<Shelter> theShelters = model.getShelters();
        theShelters.clear();
        theShelters.add(s1);
        assertEquals(u1.reserveBeds(11, s1), false);
    }

    @Test
    public void testReserveBedsOuterFalse() {
        User u1 = new User("tyler1@hehexd.com", false);
        model.addUser(u1);
        u1.setReservedBedsNumber(1);
        Shelter s1 = new Shelter(0, "name", "10", "restrictions", 0.0, 0.0, "address", "phone");
        List<Shelter> theShelters = model.getShelters();
        theShelters.clear();
        theShelters.add(s1);
        assertEquals(u1.reserveBeds(1, s1), false);
    }
}