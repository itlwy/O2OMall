package itlwy.com.o2omall.home;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.click;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by mac on 16/12/30.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);
    @Test
    public void testSay() {
//        onView(withId(R.id.login_et_account)).perform(typeText("sam"));
//        onView(withId(R.id.login_et_password)).perform(typeText("123456"), closeSoftKeyboard());
//        onView(withId(R.id.login_btn_login)).perform(click());
        onData(allOf
                (is(instanceOf(Map.class)),hasEntry(equalTo("STR"),is("item:50")))
        ).perform(click());
//        onView(withId(R.id.testtv)).perform(replaceText("peter"), closeSoftKeyboard());
    }

}