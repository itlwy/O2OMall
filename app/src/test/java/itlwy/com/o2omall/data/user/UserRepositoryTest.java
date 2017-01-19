package itlwy.com.o2omall.data.user;

import org.junit.Before;
import org.junit.Test;

import itlwy.com.o2omall.data.user.model.UserModel;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

/**
 * Created by mac on 16/12/27.
 */

public class UserRepositoryTest {

    private UserRepository mUserRepository;

    @Before
    public void setup(){
//        MockitoAnnotations.initMocks(this);
        mUserRepository = new UserRepository();
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getIOScheduler() {
                return Schedulers.immediate();
            }
        });
        RxAndroidPlugins.getInstance().registerSchedulersHook(new RxAndroidSchedulersHook() {
            @Override
            public Scheduler getMainThreadScheduler() {
                return Schedulers.immediate();
            }
        });
    }

    @Test
    public void testLogin(){
        String user = "sam";
        String password = "123456";
        mUserRepository.login(new Subscriber<UserModel>() {
            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println(e.toString());
            }

            @Override
            public void onNext(UserModel userModel) {
                System.out.println(userModel.toString());
            }
        },user,password);
    }
}
