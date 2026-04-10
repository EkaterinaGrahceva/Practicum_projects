package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AlexLionTest {

    @Mock
    Feline feline;

    @Test
    public void getFriendsReturnsCorrectList() throws Exception {
        AlexLion alex = new AlexLion(feline);
        List<String> expectedFriends = List.of("Марти", "Глория", "Мелман");
        assertEquals(expectedFriends, alex.getFriends());
    }

    @Test
    public void getPlaceOfLivingReturnsNewYorkZoo() throws Exception {
        AlexLion alex = new AlexLion(feline);
        assertEquals("Нью-Йоркский зоопарк", alex.getPlaceOfLiving());
    }

    @Test
    public void getKittensReturnsZero() throws Exception {
        AlexLion alex = new AlexLion(feline);
        assertEquals(0, alex.getKittens());
    }

    @Test
    public void alexIsMale() throws Exception {
        AlexLion alex = new AlexLion(feline);
        assertTrue(alex.doesHaveMane());
    }
}