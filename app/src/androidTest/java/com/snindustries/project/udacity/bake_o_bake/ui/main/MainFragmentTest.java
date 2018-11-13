package com.snindustries.project.udacity.bake_o_bake.ui.main;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.exoplayer2.ui.PlayerView;
import com.snindustries.project.udacity.bake_o_bake.MainActivity;
import com.snindustries.project.udacity.bake_o_bake.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.AllOf.allOf;

/**
 * @author Shaaz Noormohammad
 * (c) 11/13/18
 */
@RunWith(AndroidJUnit4.class)
public class MainFragmentTest {

    private static final String RECIPE_NAME_BROWNIES = "Brownies";
    private static final String RECIPE_NAME_CHEESECAKE = "Cheesecake";
    private static final String RECIPE_NAME_NUTELLA_PIE = "Nutella Pie";
    private static final String RECIPE_NAME_YELLOW_CAKE = "Yellow Cake";

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource idlingResource;

    @Test
    public void clickStepRecyclerViewItem_OpensRecipeStep() {
        onView(withId(R.id.recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(allOf(instanceOf(PlayerView.class), withId(R.id.video_player))).check(matches(isDisplayed()));
    }

    @Test
    public void listIsFilledWithCorrectData() {
        onView(withId(R.id.recycler)).perform(scrollToPosition(0));
        onView(withText(RECIPE_NAME_NUTELLA_PIE)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler)).perform(scrollToPosition(1));
        onView(withText(RECIPE_NAME_BROWNIES)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler)).perform(scrollToPosition(2));
        onView(withText(RECIPE_NAME_YELLOW_CAKE)).check(matches(isDisplayed()));
        onView(withId(R.id.recycler)).perform(scrollToPosition(3));
        onView(withText(RECIPE_NAME_CHEESECAKE)).check(matches(isDisplayed()));
    }

    @Before
    public void setUp() {
        idlingResource = activityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }

}