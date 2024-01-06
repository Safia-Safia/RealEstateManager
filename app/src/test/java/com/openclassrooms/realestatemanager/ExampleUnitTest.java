package com.openclassrooms.realestatemanager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.openclassrooms.realestatemanager.model.Estate;
import com.openclassrooms.realestatemanager.model.Picture;
import com.openclassrooms.realestatemanager.model.User;
import com.openclassrooms.realestatemanager.repository.EstateRepository;
import com.openclassrooms.realestatemanager.repository.UserRepository;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.viewModel.EstateViewModel;
import com.openclassrooms.realestatemanager.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private User user;
    private final Estate estate = new Estate();
    private final List<Picture> pictures = new ArrayList<>();
    @Mock
    private EstateViewModel estateViewModel;
    @Mock
    private EstateRepository estateRepository;
    @Mock
    private UserViewModel userViewModel;

    @Mock
    private UserRepository userRepository;



    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        estateViewModel = new EstateViewModel(estateRepository);
        userViewModel = new UserViewModel(userRepository);
    }

    @Test
    public void getCurrentUser() {
        String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
        user = new User("1", "Jean Dupont", "https://photoDeJean.jpg","dupont@gmail.com");
        List<User> users = new ArrayList<>();
        users.add(user);
        assertEquals("1", users.get(0).getUid());
        assertEquals("Jean Dupont", users.get(0).getUsername());
        assertEquals("https://photoDeJean.jpg", users.get(0).getUrlPicture());
        assertEquals("dupont@gmail.com", true, emailPattern.matcher(users.get(0).getEmail()).matches());
    }


    @Test
    public void testConvertDollarToEuro() {
        assertEquals(406, Utils.convertDollarToEuro(500));
    }

    @Test
    public void testConvertEuroToDollar() {
        assertEquals(615, Utils.convertEuroToDollar(500));
    }

    @Test
    public void testFormattedTodayDate(){
        String todayFormattedDate = Utils.getTodayFormatedDate();
        assertNotNull(todayFormattedDate);
        System.out.println(todayFormattedDate);
        assertTrue(todayFormattedDate.matches("\\d{2}/\\d{2}/\\d{4}"));
    }

    @Test
    public void testTodayDate(){
        String todayFormattedDate = Utils.getTodayDate();
        assertNotNull(todayFormattedDate);
        System.out.println(todayFormattedDate);
        assertTrue(todayFormattedDate.matches("\\d{4}/\\d{2}/\\d{2}"));
    }
}