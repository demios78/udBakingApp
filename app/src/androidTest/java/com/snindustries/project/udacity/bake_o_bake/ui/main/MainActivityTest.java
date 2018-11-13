package com.snindustries.project.udacity.bake_o_bake.ui.main;


import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.snindustries.project.udacity.bake_o_bake.MainActivity;
import com.snindustries.project.udacity.bake_o_bake.NetworkIdlingResource;
import com.snindustries.project.udacity.bake_o_bake.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private NetworkIdlingResource idlingResource;

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    @Test
    public void mainActivityTest() {
        // Done: Adding a idlingResource to MainActivity

        ViewInteraction constraintLayout = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                ViewMatchers.withId(R.id.recycler),
                                0),
                        0),
                        isDisplayed()));
        constraintLayout.perform(click());
        ViewInteraction constraintLayout2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.recycler),
                                1),
                        0),
                        isDisplayed()));
        constraintLayout2.perform(click());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        // TODO add Idling Resource to exoplayer/StepDeatilFragment
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction button = onView(
                allOf(withId(R.id.previous_btn),
                        isDisplayed()));
        button.check(matches(not(isEnabled())));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.next_btn),
                        isDisplayed()));
        button2.check(matches(allOf(isDisplayed(), isEnabled())));

        ViewInteraction frameLayout = onView(
                allOf(withId(R.id.video_player),
                        isDisplayed()));
        frameLayout.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.short_description), withText("Recipe Introduction"),

                        isDisplayed()));
        textView.check(matches(withText("Recipe Introduction")));
    }

    @Before
    public void setUp() {
        idlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource);
    }
}
