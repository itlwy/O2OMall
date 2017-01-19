package itlwy.com.o2omall.user.login;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import itlwy.com.o2omall.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by mac on 16/12/28.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {
//    private UserRepository mUserRepository;
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<LoginActivity>(
            LoginActivity.class);


    @Before
    public void setUp() throws Exception {
        System.out.println("LoginActivityTest setup");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testSay() {
        onView(withId(R.id.login_et_account)).perform(typeText("sam"));
        onView(withId(R.id.login_et_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.login_btn_login)).perform(click());
//        onView(withId(R.id.testtv)).perform(replaceText("peter"), closeSoftKeyboard());
    }


}