package com.openclassrooms.realestatemanager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
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
    @Mock
    private EstateViewModel estateViewModel;
    @Mock
    private EstateRepository estateRepository;
    @Mock
    private UserViewModel userViewModel;

    @Mock
    private FirebaseAuth firebaseAuth;

    @Mock
    private UserRepository userRepository;

    ArrayList<Estate> estateList = new ArrayList<>();


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    /*TODO Creer les deux biens ici et les appeler des que besoin
       Crer une methode pour réinitialiser les valeurs des tests au besoin
       @Before avant chaque test @After après chaque test*/
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        estateViewModel = new EstateViewModel(estateRepository);
        userViewModel = new UserViewModel(userRepository);
        Estate estate1 = new Estate();
        Estate estate2 = new Estate();
        User user1 = new User();
        User user2 = new User();
        // USER
        user1.setUid("1");
        user1.setUsername("Jean Dupont");
        user1.setUrlPicture("https://image-de-profil.jpg");
        user1.setEmail("jean.dupont@example.com");
        user2.setUsername("Sophie Dupont");
        user2.setUid(String.valueOf(1));
        user2.setUrlPicture("https://image-de-profil.jpg");
        user2.setEmail("sophie.dupont@example.com");


        estate1.setId("1");
        //CREATING DUMMY ESTATE
        estate1.setId("1");
        estate1.setDescription("Appartement spacieux avec une vue magnifique");
        estate1.setCoverPictureUrl("https://image-de-couverture.jpg");
        estate1.setEstateType("Maison");
        estate1.setNumberOfRoom(4);
        estate1.setPrice(1100300);
        estate1.setSurface(250);
        estate1.setAddress("123 Rue DE PARIS");
        estate1.setEntryDate("2024-01-02");
        estate1.setSoldDate(null); // NOT SOLD
        estate1.setCity("Paris");
        estate1.setEstatesAvailable(true);
        estate1.setSchool(true);
        estate1.setStore(false);
        estate1.setPark(true);
        estate1.setParking(false);
        // DUMMY PICTURES
        List<Picture> pictures2 = new ArrayList<>();
        Picture picture3 = new Picture();
        picture3.setImageUrl("https://image1.jpg");
        Picture picture4 = new Picture();
        picture4.setImageUrl("https://image2.jpg");
        pictures2.add(picture3);
        pictures2.add(picture4);
        estate1.setPictures(pictures2);
        // FAKE LOCATION FOR THE ADDRESS
        estate1.setLatitude(48.8566);
        estate1.setLongitude(2.3522);
        estate1.setUser(user1);

        estate2.setId("1");
        //CREATING DUMMY ESTATE
        estate2.setId("1");
        estate2.setDescription("Appartement spacieux avec une vue magnifique");
        estate2.setCoverPictureUrl("https://image-de-couverture.jpg");
        estate2.setEstateType("Appartement");
        estate2.setNumberOfRoom(3);
        estate2.setPrice(200300);
        estate2.setSurface(150);
        estate2.setAddress("123 Rue DE PARIS");
        estate2.setEntryDate("2023-01-10");
        estate2.setSoldDate(null); // NOT SOLD
        estate2.setCity("Paris");
        estate2.setEstatesAvailable(true);
        estate2.setSchool(true);
        estate2.setStore(false);
        estate2.setPark(true);
        estate2.setParking(false);
        // DUMMY PICTURES
        List<Picture> pictures1 = new ArrayList<>();
        Picture picture1 = new Picture();
        picture1.setImageUrl("https://image1.jpg");
        Picture picture2 = new Picture();
        picture2.setImageUrl("https://image2.jpg");
        pictures1.add(picture1);
        pictures1.add(picture2);
        estate2.setPictures(pictures1);
        // FAKE LOCATION FOR THE ADDRESS
        estate2.setLatitude(48.8566);
        estate2.setLongitude(2.3522);
        estate2.setUser(user2);

        estateList.add(estate1);
        estateList.add(estate2);
    }

    @Test
    public void getCurrentUser() {
        Estate estate = estateList.get(0);
        User currentUser = estate.getUser();
        assertEquals("1", currentUser.getUid());
        assertEquals("Jean Dupont", currentUser.getUsername());
        assertEquals("https://image-de-profil.jpg", currentUser.getUrlPicture());
        assertEquals("jean.dupont@example.com", currentUser.getEmail());
    }


    @Test
    public void createEstate() {
        System.out.println("Before adding Estate: " + estateList.size());
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        Estate newEstate = new Estate();

        newEstate.setId(String.valueOf(1));
        estateList.add(newEstate);
        result.setValue(true);

        when(estateRepository.createEstate(Mockito.any(Estate.class))).thenReturn(result);
        estateViewModel.createEstate(newEstate).observeForever(success -> {
            assertEquals(estateList.size(),3);
            System.out.println("After adding new Estate: " + estateList.size());
        });
    }

    @Test
    public void getAllEstate() {
        MutableLiveData<List<Estate>> result = new MutableLiveData<>();
        Mockito.when(estateRepository.getEstates()).thenReturn(result);

        estateViewModel.getEstates().observeForever(allEstates -> {
            assertEquals(estateList, allEstates);
            assertEquals(2, allEstates.size());
            System.out.println(" Check estate list :" + estateList.size()+ "  and all Estate : " + allEstates.size());
        });
        result.setValue(estateList);
    }

    @Test
    public void updateEstate() {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        result.setValue(true);

        System.out.println("After update: Sold Date: " + estateList.get(0).getSoldDate() +
                " Description: " + estateList.get(0).getDescription() + " Price: " + estateList.get(0).getPrice()
                + " userName : " + estateList.get(0).getUser().getUsername());

        Mockito.when(estateRepository.updateEstate(estateList.get(0), estateList.get(0).getId())).thenReturn(result);
        estateViewModel.updateEstate(estateList.get(0), estateList.get(0).getId()).observeForever(success -> {
            estateList.get(0).setSoldDate("2024-01-12");
            estateList.get(0).setDescription("Updated");
            estateList.get(0).setPrice(12345);
            assertEquals(true, success);

            System.out.println("After update: Sold Date: " + estateList.get(0).getSoldDate() +
                    " Description: " + estateList.get(0).getDescription() + " Price: " + estateList.get(0).getPrice()
                    + " userName : " + estateList.get(0).getUser().getUsername());
        });
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
    public void testFormattedTodayDate() {
        String todayFormattedDate = Utils.getTodayFormatedDate();
        assertNotNull(todayFormattedDate);
        System.out.println(todayFormattedDate);
        assertTrue(todayFormattedDate.matches("\\d{2}/\\d{2}/\\d{4}"));
    }
    @Test
    public void testTodayDate() {
        String todayFormattedDate = Utils.getTodayDate();
        assertNotNull(todayFormattedDate);
        System.out.println(todayFormattedDate);
        assertTrue(todayFormattedDate.matches("\\d{4}/\\d{2}/\\d{2}"));
    }

}