package itlwy.com.o2omall.home.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.base.BaseApplication;
import itlwy.com.o2omall.base.BaseHolder;
import itlwy.com.o2omall.base.BaseMVPFragment;
import itlwy.com.o2omall.data.ClientKernal;
import itlwy.com.o2omall.data.user.model.UserModel;
import itlwy.com.o2omall.home.contract.MyContract;
import itlwy.com.o2omall.user.address.AddressActivity;
import itlwy.com.o2omall.user.login.LoginActivity;
import itlwy.com.o2omall.utils.ActivityUtils;
import itlwy.com.o2omall.utils.UIManager;
import itlwy.com.o2omall.view.LoadingPage;
import support.utils.PicUtils;

/**
 * Created by Administrator on 2016/2/17.
 */
public class MyFragment extends BaseMVPFragment implements MyContract.IMyView {
    private static final int PHOTO = 101;
    private MyHolder myHolder;
    private MyContract.IMyPresenter presenter;
    private String mLogoImageName = "mylogo.jpg";

    @Override
    public void onStart() {
        super.onStart();
        presenter.subscribe(null);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    protected View createSuccessView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myHolder = new MyHolder(getActivity());
        return myHolder.getContentView();
    }

    @Override
    protected String getFragmentKey() {
        return ConstantValue.MYFRAGMENT;
    }

    @Override
    protected LoadingPage.ReLoadListener getReloadListener() {
        return null;
    }

    @Override
    protected void inits() {

    }

    @Override
    public void bindViewDatas(String result) {
        myHolder.setData(result);
    }

    @Override
    public void refreshMyLogo(String imageUrl) {
        ImageLoader.getInstance().displayImage(imageUrl, myHolder.mMyLogoIb, myHolder.getOptions());
    }


    @Override
    public void setPresenter(MyContract.IMyPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (PHOTO == requestCode && resultCode == Activity.RESULT_OK) {
            Bitmap bmp = null;
            if (data != null) {
                Uri uriphoto = data.getData();
                if (uriphoto != null) {
                    bmp = PicUtils.getBitmapFromFile(new File(uriphoto.getPath()), 800, 600);
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null)
                        bmp = (Bitmap) extras.get("data");
                }
            }
            if (bmp == null)
                bmp = PicUtils.getBitmapFromFile(new File(BaseApplication.sTempImagePath + mLogoImageName),
                        800, 600);
            if (bmp == null) {
                showToast("拍照失败，请检查你的相机！");
                return;
            }
            FileOutputStream outputStream = null;
            try {
                File out = new File(BaseApplication.sImagePath, mLogoImageName);
                outputStream = new FileOutputStream(out);
            } catch (FileNotFoundException e) {
                showToast(e.toString());
                return;
            }
            if (bmp.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)) {
                presenter.uploadMyLogo(mLogoImageName);
            } else {
                showToast("拍照失败,请检查相机是否正常");
                if (bmp != null) {
                    bmp.recycle();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class MyHolder extends BaseHolder<String, Void> {

        @Bind(R.id.my_ib_login)
        ImageButton myIbLogin;
        //        @Bind(R.id.my_tv_username)
//        TextView myTvUsername;
        @Bind(R.id.before_login_rlt)
        RelativeLayout mBeforeLoginRlt;
        @Bind(R.id.my_logo_ib)
        ImageView mMyLogoIb;
        @Bind(R.id.my_username_tv)
        TextView mMyUsernameTv;
        @Bind(R.id.my_nickname_tv)
        TextView mMyNicknameTv;
        @Bind(R.id.my_mail_tv)
        TextView mMyMailTv;
        @Bind(R.id.my_phone_tv)
        TextView mMyPhoneTv;
        @Bind(R.id.after_login_rlt)
        RelativeLayout mAfterLoginRlt;
        @Bind(R.id.my_vip_tv)
        TextView mMyVipTv;
        private DisplayImageOptions options;

        public MyHolder(Context ctx) {
            super(ctx);
        }

        @Override
        public View initView() {
            View v = View.inflate(getContext(), R.layout.fragment_my, null);
            ButterKnife.bind(this, v);
//            myIbLogin.setOnClickListener(this);
            return v;
        }

        @Override
        public void refreshView(String aVoid) {
            UserModel userModel = ClientKernal.getInstance().getUserModel();
            if (userModel != null) {
                mAfterLoginRlt.setVisibility(View.VISIBLE);
                mBeforeLoginRlt.setVisibility(View.GONE);
                mMyUsernameTv.setText("姓名:" + userModel.getUserName());
                mMyNicknameTv.setText("昵称:" + userModel.getNickName());
                mMyMailTv.setText("email:" + userModel.getEmail());
                mMyPhoneTv.setText("电话:" + userModel.getPhone());
                mMyVipTv.setText("vip:" + userModel.getVipLevel());
                ImageLoader.getInstance().displayImage(userModel.getLogo(), mMyLogoIb, getOptions());
            } else {
                mBeforeLoginRlt.setVisibility(View.VISIBLE);
                mAfterLoginRlt.setVisibility(View.GONE);
            }
        }

        public DisplayImageOptions getOptions() {
            if (options == null) {
                options = new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .displayer(new RoundedBitmapDisplayer(20))
                        .build();
            }
            return options;
        }

        @OnClick({R.id.my_ib_login, R.id.my_logo_ib,R.id.my_address_tv})
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.my_ib_login:
                    UIManager.getInstance().changeActivity(getActivity(), LoginActivity.class, null);
                    break;
                case R.id.my_logo_ib:
                    ActivityUtils.takePicture(MyFragment.this, PHOTO, mLogoImageName);
                    break;
                case R.id.my_address_tv:
                    Intent intent = new Intent(getActivity(), AddressActivity.class);
                    startActivity(intent);
                    break;
            }
        }

    }

}
