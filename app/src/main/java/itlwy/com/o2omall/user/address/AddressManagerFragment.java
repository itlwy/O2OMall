package itlwy.com.o2omall.user.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.adapter.BaseRCAdapter;
import itlwy.com.o2omall.base.BaseMVPActivity;
import itlwy.com.o2omall.base.BaseMVPFragment;
import itlwy.com.o2omall.data.user.UserRepository;
import itlwy.com.o2omall.data.user.model.AddressModel;
import itlwy.com.o2omall.factory.FragmentFactory;
import itlwy.com.o2omall.product.OrderActivity;
import itlwy.com.o2omall.utils.UIManager;
import itlwy.com.o2omall.view.LoadingPage;

public class AddressManagerFragment extends BaseMVPFragment implements AddressContract.IAddressManagerView {

    @Bind(R.id.manager_rcv)
    RecyclerView mManagerRcv;
    private AddressContract.IAddressManagerPresenter presenter;
    private AddressManagerAdapter mManagerRcvAdapter;
    private String flag;  // 标示是从哪个界面跳转过来的，目前有订单提交页面 和地址管理页面

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
    protected void inits() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            flag = bundle.getString("flag");
        }
    }

    @Override
    protected View createSuccessView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addres_manager, null);
        ButterKnife.bind(this, view);
        mManagerRcvAdapter = new AddressManagerAdapter(getActivity(), null, presenter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mManagerRcv.setLayoutManager(manager);
        mManagerRcv.setAdapter(mManagerRcvAdapter);
        if (ConstantValue.ORDERFRAGMENT.equals(flag)) {
            mManagerRcvAdapter.setOnItemClickListener(new BaseRCAdapter.OnItemClick() {
                @Override
                public void onItemClick(View view, int position) {
                    AddressModel item = mManagerRcvAdapter.getmDatas().get(position);
                    ((OrderActivity)getActivity()).setFromAddressBack(true);
                    ((OrderActivity)getActivity()).setSelectedAddress(item);
                    getFragmentManager().popBackStack();
                }
            });
        }
        return view;
    }

    @Override
    protected String getFragmentKey() {
        return "";
    }

    @Override
    protected LoadingPage.ReLoadListener getReloadListener() {
        return null;
    }


    @Override
    public void setPresenter(AddressContract.IAddressManagerPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @OnClick(R.id.manager_add_btn)
    public void onClick() {
        BaseMVPFragment fragment = FragmentFactory.createFragment(getActivity(), ConstantValue.ADDRESSADDFRAGMENT, true);
        AddressAddPresenter.newInstance((AddressContract.IAddressAddView) fragment, new UserRepository());
        UIManager.getInstance().changeFragment(getActivity(),
                ((BaseMVPActivity) getActivity()).getFragmentContain(), fragment, true, null);
//        ((BaseMVPActivity)getActivity()).changeFragment(fragment,true);
    }


    @Override
    public void bindViewDatas(List<AddressModel> results) {
        mManagerRcvAdapter.setmDatas(results);
    }

    @Override
    public void refreshView() {
        presenter.subscribe(null);
    }
}
