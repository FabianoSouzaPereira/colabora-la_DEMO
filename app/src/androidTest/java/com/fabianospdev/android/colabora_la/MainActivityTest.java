package com.fabianospdev.android.colabora_la;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

  @Rule
  public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>( MainActivity.class );

  @Rule
  public GrantPermissionRule mGrantPermissionRule = GrantPermissionRule.grant( "android.permission.ACCESS_FINE_LOCATION", "android.permission.READ_SMS", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE" );

  @Test
  public void mainActivityTest() {
    // Added a sleep statement to match the app's execution delay.
    // The recommended way to handle such scenarios is to use Espresso idling resources:
    // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
    try {
      Thread.sleep( 700 );
    } catch ( InterruptedException e ) {
      e.printStackTrace();
    }

    ViewInteraction bottomNavigationItemView = onView( allOf( withId( R.id.camera ), withContentDescription( "Camera" ), childAtPosition( childAtPosition( withId( R.id.bottom_navigation ), 0 ), 2 ), isDisplayed() ) );
    bottomNavigationItemView.perform( click() );

    // Added a sleep statement to match the app's execution delay.
    // The recommended way to handle such scenarios is to use Espresso idling resources:
    // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
    try {
      Thread.sleep( 700 );
    } catch ( InterruptedException e ) {
      e.printStackTrace();
    }

    ViewInteraction appCompatButton = onView( allOf( withId( R.id.btnCapture ), withText( "capture" ), childAtPosition( childAtPosition( withId( android.R.id.content ), 0 ), 1 ), isDisplayed() ) );
    appCompatButton.perform( click() );
  }

  private static Matcher<View> childAtPosition( final Matcher<View> parentMatcher, final int position ) {

    return new TypeSafeMatcher<View>() {
      @Override
      public void describeTo( Description description ) {
        description.appendText( "Child at position " + position + " in parent " );
        parentMatcher.describeTo( description );
      }

      @Override
      public boolean matchesSafely( View view ) {
        ViewParent parent = view.getParent();
        return parent instanceof ViewGroup && parentMatcher.matches( parent ) && view.equals( ( (ViewGroup) parent ).getChildAt( position ) );
      }
    };
  }
}
