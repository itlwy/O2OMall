package itlwy.com.o2omall.product.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.lndroid.lndroidlib.base.BaseMVPActivity;
import com.lndroid.lndroidlib.base.BaseMVPFragment;
import com.lndroid.lndroidlib.factory.FragmentFactory;
import com.lndroid.lndroidlib.utils.DensityUtil;
import com.lndroid.lndroidlib.utils.ImageLoaderUtil;
import com.lndroid.lndroidlib.utils.UIManager;
import com.lndroid.lndroidlib.view.LoadingPage;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itlwy.com.o2omall.ConstantValue;
import itlwy.com.o2omall.R;
import itlwy.com.o2omall.data.ClientKernal;
import itlwy.com.o2omall.data.product.model.OrdersModel;
import itlwy.com.o2omall.data.product.model.ProductModel;
import itlwy.com.o2omall.data.user.UserRepository;
import itlwy.com.o2omall.data.user.model.AddressModel;
import itlwy.com.o2omall.product.OrderActivity;
import itlwy.com.o2omall.product.contract.OrderContract;
import itlwy.com.o2omall.user.address.AddressContract;
import itlwy.com.o2omall.user.address.AddressManagerPresenter;

public class OrderFragment extends BaseMVPFragment implements OrderContract.IOrderView {

    @Bind(R.id.order_receiver_tv)
    TextView mOrderReceiverTv;
    @Bind(R.id.order_phone_tv)
    TextView mOrderPhoneTv;
    @Bind(R.id.order_address_tv)
    TextView mOrderAddressTv;
    @Bind(R.id.order_product_llt)
    LinearLayout mOrderProductLlt;
    @Bind(R.id.order_product_count_tv)
    TextView mOrderProductCountTv;
    @Bind(R.id.order_payway_spinner)
    Spinner mOrderPaywaySpinner;
    @Bind(R.id.order_sendway_spinner)
    Spinner mOrderSendwaySpinner;
    @Bind(R.id.order_product_money_tv)
    TextView mOrderProductMoneyTv;
    @Bind(R.id.order_express_money_tv)
    TextView mOrderExpressMoneyTv;
    @Bind(R.id.order_total_money_tv)
    TextView mOrderTotalMoneyTv;
    @Bind(R.id.order_address_rlt)
    RelativeLayout mOrder_address_rlt;
    private OrderContract.IOrderPresenter presenter;
    private int ivSize;
    private float productsMoney;  // 商品总金额
    private float sendMoney = 7;  // 配送费
    private float totalMoney;  // 总金额
    private int ivMargin;
    private int addressID;  // 确定的地址id
    private int distributionNum; // 发货数量
    private List<ProductModel> productList;

    public int getIvMargin() {
        if (ivMargin == 0) {
            ivMargin = DensityUtil.dip2px(getActivity(), 10);
        }
        return ivMargin;
    }

    public int getIvSize() {
        if (ivSize == 0) {
            ivSize = DensityUtil.dip2px(getActivity(), 43);
        }
        return ivSize;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (((OrderActivity) getActivity()).isFromAddressBack()) {
            AddressModel selectedModel = ((OrderActivity) getActivity()).getAddressModel();
            presenter.subscribe(selectedModel);
            ((OrderActivity) getActivity()).setFromAddressBack(false);
        } else if (((OrderActivity) getActivity()).isFromProductDetailsBack()) {
            ((OrderActivity) getActivity()).setFromProductDetailsBack(false);
        } else
            presenter.subscribe(null);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    public void killMyself() {
        getActivity().finish();
    }

    @Override
    protected void inits() {

    }

    @Override
    protected View createSuccessView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_main, null);
        ButterKnife.bind(this, view);
        SpinnerAdapter paywayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.payways));
        mOrderPaywaySpinner.setAdapter(paywayAdapter);
        SpinnerAdapter sendwayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sendways));
        mOrderSendwaySpinner.setAdapter(sendwayAdapter);

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
    public void bindViewDatas(List<ProductModel> results, AddressModel addressModel) {
        addressID = addressModel.getAddressID();
        mOrderReceiverTv.setText(addressModel.getReceiver());
        mOrderPhoneTv.setText(addressModel.getPhone());
        mOrderAddressTv.setText(addressModel.getDetailsAddress());
        if (results != null) {
            productList = results;
            mOrderProductCountTv.setText("共" + results.size() + "件");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(getIvSize(), getIvSize());
            for (ProductModel item : results) {
                ImageView iv = new ImageView(getActivity());
                params.setMargins(getIvMargin(), getIvMargin(), getIvMargin(), getIvMargin());
                iv.setLayoutParams(params);
                ImageLoader.getInstance().displayImage(item.getMainImageUrl(), iv,
                        ImageLoaderUtil.getInstance().getOptions());
                mOrderProductLlt.addView(iv);
                distributionNum += item.getNum();
                productsMoney += item.getNum() * item.getPrice();
            }
            totalMoney = sendMoney + productsMoney;
            mOrderTotalMoneyTv.setText("总金额:" + totalMoney);
            mOrderExpressMoneyTv.setText("配送费" + sendMoney);
            mOrderProductMoneyTv.setText("商品总额" + productsMoney);
        }
    }

    @Override
    public void setPresenter(OrderContract.IOrderPresenter presenter) {
        this.presenter = presenter;
    }


    @OnClick({R.id.order_address_rlt, R.id.order_product_llt, R.id.order_submit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_address_rlt:
                Bundle bundle = new Bundle();
                bundle.putString("flag", ConstantValue.ORDERFRAGMENT);
                BaseMVPFragment fragment = FragmentFactory.createFragment(getActivity(), ConstantValue.ADDRESSMANAGERFRAGMENT, true);
                AddressManagerPresenter.newInstance((AddressContract.IAddressManagerView) fragment, new UserRepository());
                UIManager.getInstance().changeFragment(getActivity(), ((BaseMVPActivity) getActivity()).getFragmentContain()
                        , fragment, true, bundle);
                break;
            case R.id.order_product_llt:
                BaseMVPFragment fragmentProducts = FragmentFactory.createFragment(getActivity(), ConstantValue.ORDERPRODUCTSFRAGMENT, true);

                UIManager.getInstance().changeFragment(getActivity(), ((BaseMVPActivity) getActivity()).getFragmentContain()
                        , fragmentProducts, true, null);
                break;
            case R.id.order_submit_btn:
                OrdersModel orderModel = new OrdersModel();
                orderModel.setActualPrice(totalMoney);
                orderModel.setAddressID(addressID);
                orderModel.setDistributionNum(distributionNum + "");
                orderModel.setDistributionPrice(sendMoney);
                orderModel.setDistributionWay(mOrderSendwaySpinner.getSelectedItem().toString());
                orderModel.setPayWay(mOrderPaywaySpinner.getSelectedItem().toString());
                orderModel.setUserID(ClientKernal.getInstance().getUserModel().getUserID());
                orderModel.setTotalPrice(totalMoney);
                orderModel.setUserName(ClientKernal.getInstance().getUserModel().getUserName());
                orderModel.setProducts(productList);
                presenter.submitOrder(orderModel);
                break;
        }
    }
}
