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

import org.junit.Before;
import org.junit.Test;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by jacobmeyers on 4/11/18.
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnit4.class)
@PrepareForTest({FirebaseDatabase.class})
public class JacobUnitTest {
    private Model model;
    @Before
    public void setUp() throws Exception {
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
        User u1 = new User("tyler1@hehexd.com", false);
        User u2 = new User("nickfoles@eagles.com", true);
        User u3 = new User("jmeyers35@gatech.edu", true);
        model.addUser(u1);
        model.addUser(u2);
        model.addUser(u3);

    }

    @Test
    public void testFindValidUser() {
        assertEquals(model.findUserByEmail("tyler1@hehexd.com"), new User("tyler1@hehexd.com", false));
        assertEquals(model.findUserByEmail("nickfoles@eagles.com"), new User("nickfoles@eagles.com", true));
        assertEquals(model.findUserByEmail("jmeyers35@gatech.edu"), new User("jmeyers35@gatech.edu", true));
    }

    @Test(expected = NoSuchElementException.class)
    public void testNoSuchElementException() {
        model.findUserByEmail("notinthelist@nothere.com");
    }


}