import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void setup_for_tests() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE

        //average-case
        LocalTime testTime = LocalTime.parse("12:30:00");

        Restaurant mockedRestaurant = Mockito.spy(this.restaurant);

        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(testTime);

        assertTrue(mockedRestaurant.isRestaurantOpen());

        //edge-case: currentTime is same as opening time- 10:30 (Assuming the expected behavior should be that the restaurant is open)
        testTime = LocalTime.parse("12:30:00");
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(testTime);

        assertTrue(mockedRestaurant.isRestaurantOpen());

    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        Restaurant mockedRestaurant = Mockito.spy(this.restaurant);

        LocalTime testTime;

        //Before opening time
        testTime = LocalTime.parse("08:00:00");
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(testTime);
        assertFalse(mockedRestaurant.isRestaurantOpen());

        //after closing time
        testTime = LocalTime.parse("23:00:00");
        Mockito.when(mockedRestaurant.getCurrentTime()).thenReturn(testTime);
        assertFalse(mockedRestaurant.isRestaurantOpen());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //ASSIGNMENT
    //>>>>>>>>>>>>>>>>>>>>>>TASK-3<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void calculate_items_price_should_return_the_price_of_the_given_items() {
        List<String> itemNames = new ArrayList<String>();

        List<Item> menuItems = this.restaurant.getMenu();
        for(int i = 0; i < menuItems.size(); ++i) {
            itemNames.add(menuItems.get(i).getName());
        }

        assertNotEquals(this.restaurant.calculateItemsPrice(itemNames), 0);

        assertEquals(this.restaurant.calculateItemsPrice(itemNames), 388);
    }

    @Test
    public void calculate_items_price_should_ignore_non_existing_item() {
        List<String> itemNames = new ArrayList<String>();

        List<Item> menuItems = this.restaurant.getMenu();
        for(int i = 0; i < menuItems.size(); ++i) {
            itemNames.add(menuItems.get(i).getName());
        }

        itemNames.add("Fake item");

        assertEquals(this.restaurant.calculateItemsPrice(itemNames), 388);
    }

    @Test
    void calculate_items_price_should_return_0_when_the_list_of_strings_is_empty_or_no_item_in_list_exists() {
        List<String> itemNames = new ArrayList<String>();

        assertEquals(this.restaurant.calculateItemsPrice(itemNames), 0);

        itemNames.add("Fake name 1");
        itemNames.add("Fake name 2");

        assertEquals(this.restaurant.calculateItemsPrice(itemNames), 0);
    }
    //<<<<<<<<<<<<<<<<<<<<<<TASK-3>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}