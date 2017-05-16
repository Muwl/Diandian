package com.daqianjietong.diandian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.TextView;

import com.daqianjietong.diandian.R;
import com.daqianjietong.diandian.adapter.PayListAdapter;
import com.daqianjietong.diandian.base.BaseActivity;
import com.daqianjietong.diandian.dialog.CustomeDialog;
import com.daqianjietong.diandian.model.PartEntity;
import com.daqianjietong.diandian.model.PayListEntity;
import com.daqianjietong.diandian.model.ReserveEntity;
import com.daqianjietong.diandian.utils.Api;
import com.daqianjietong.diandian.utils.HttpUtil;
import com.daqianjietong.diandian.utils.SpUtil;
import com.daqianjietong.diandian.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/7.
 */

public class PayListActivity extends BaseActivity {
    @BindView(R.id.title_title)
    TextView titleTitle;
    @BindView(R.id.paylist_list)
    ListView paylistList;
    private PayListAdapter adapter;
    private List<ReserveEntity> entities=new ArrayList<>();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    int poi=msg.arg1;
                    Intent intent=new Intent(PayListActivity.this,PayActivity.class);
                    intent.putExtra("reserve",entities.get(poi));
                    startActivity(intent);
                    break;
                case CustomeDialog.RET_OK:
                    int position=msg.arg2;
                    unsetReserve(position);
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_paylist;
    }

    @Override
    public void initView() {
        titleTitle.setText("支付");
        adapter=new PayListAdapter(this,entities,handler);
        paylistList.setAdapter(adapter);
        parkingIndex();
    }



    @OnClick(R.id.title_back)
    public void onViewClicked() {
        finish();
    }


    private void parkingIndex(){
        showDialog();
        Api.getInstance().paylist(SpUtil.getUid(),new HttpUtil.URLListenter<List<ReserveEntity>>() {
            @Override
            public void onsucess(List<ReserveEntity> payListEntities) throws Exception {
                dissDialog();
                if (entities!=null){
                    for (ReserveEntity reserveEntity:payListEntities){
                        entities.add(reserveEntity);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onfaild(String error) {
                dissDialog();
                ToastUtil.show(error);
            }
        });
    }

    private void unsetReserve(final int position){
        showDialog();
        Api.getInstance().unsetReserve(entities.get(position).reserveid,new HttpUtil.URLListenter<String>() {
            @Override
            public void onsucess(String string) throws Exception {
                dissDialog();
                ToastUtil.show("取消成功！");
                entities.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onfaild(String error) {
                dissDialog();
                ToastUtil.show(error);
            }
        });
    }
}
