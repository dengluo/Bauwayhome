package com.bauwayhome.ec.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bauwayhome.ec.App.Constants;
import com.bauwayhome.ec.R;
import com.bauwayhome.ec.base.BaseActivity;
import com.bauwayhome.ec.bean.JsonBean;
import com.bauwayhome.ec.bean.User;
import com.bauwayhome.ec.util.DialogUtil;
import com.bauwayhome.ec.util.GetJsonDataUtil;
import com.bigkoo.pickerview.OptionsPickerView;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 完善机构注册信息
 * danny
 */
public class PerfectInfoActivity extends BaseActivity {

    private static final String TAG = "PerfectInfoActivity";

    @BindView(R.id.et_perfectinfo_organization_name)
    EditText et_perfectinfo_organization_name;
    @BindView(R.id.et_perfectinfo_legal_representative)
    EditText et_perfectinfo_legal_representative;
    @BindView(R.id.et_perfectinfo_personinfo_head)
    EditText et_perfectinfo_personinfo_head;
    @BindView(R.id.et_perfectinfo_registration_mark)
    EditText et_perfectinfo_registration_mark;
    @BindView(R.id.tv_perfectinfo_address)
    TextView tv_perfectinfo_address;
    @BindView(R.id.bt_perfectinfo_register)
    Button bt_perfectinfo_register;
    @BindView(R.id.iv_return)
    ImageView iv_return;

    private Thread thread;
    private boolean isLoaded = false;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static final int MSG_LOAD_ADDRESS = 0x0004;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        Toast.makeText(PerfectInfoActivity.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    Toast.makeText(PerfectInfoActivity.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(PerfectInfoActivity.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
                case MSG_LOAD_ADDRESS:
                    tv_perfectinfo_address.setText(msg.obj.toString());
                    break;

            }
        }
    };

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    private void ShowPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                Message msg = new Message();
                msg.what = MSG_LOAD_ADDRESS;
                msg.obj = tx;//可以是基本类型，可以是对象，可以是List、map等；
                mHandler.sendMessage(msg);

//                Toast.makeText(PerfectInfoActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_perfectinfo;
    }

    @Override
    protected void initComplete(Bundle savedInstanceState) {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        BmobSMS.initialize(this, Constants.BMOB_ID);
    }

    @Override
    protected void initView() {
        String emailHistory = userRxPreferences.getString(Constants.LOGIN_PHONE).get();

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }


    @OnClick({R.id.iv_return, R.id.bt_perfectinfo_register, R.id.tv_perfectinfo_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_perfectinfo_address:
                if (isLoaded) {
                    ShowPickerView();
                } else {
                    Toast.makeText(PerfectInfoActivity.this, "Please waiting until the data is parsed", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_return:
                this.finish();
                break;
            case R.id.bt_perfectinfo_register:
                perfectinfoRegister();
                break;
            default:
                break;
        }
    }


    private void perfectinfoRegister() {
        String name = et_perfectinfo_organization_name.getText().toString().trim();
        String representative = et_perfectinfo_legal_representative.getText().toString().trim();
        String head = et_perfectinfo_personinfo_head.getText().toString().trim();
        String mark = et_perfectinfo_registration_mark.getText().toString().trim();
        String address = tv_perfectinfo_address.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            ToastUtils.showShort(R.string.plz_input_organization_name);
            return;
        }
//        if (TextUtils.isEmpty(representative)) {
//            ToastUtils.showShort(R.string.plz_input_legal_representative);
//            return;
//        }
//        if (TextUtils.isEmpty(head)) {
//            ToastUtils.showShort(R.string.plz_input_personinfo_head);
//            return;
//        }
//        if (TextUtils.isEmpty(mark)) {
//            ToastUtils.showShort(R.string.plz_input_registration_mark);
//            return;
//        }
        if (TextUtils.isEmpty(address)) {
            ToastUtils.showShort(R.string.plz_input_address);
            return;
        }
        String[] arr = {name, representative, head, mark, address};

        DialogUtil.progressDialog(PerfectInfoActivity.this, getString(R.string.register_now), false);
        String objectId = BmobUser.getCurrentUser().getObjectId();
        final String phoneNumber = BmobUser.getCurrentUser().getMobilePhoneNumber();
        final User user = new User();
        user.setInfo(arr);
        user.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    Log.i("bmob", "更新成功" + phoneNumber);
                    userRxPreferences.getString(Constants.LOGIN_PHONE).set(phoneNumber);
                    startActivity(new Intent(PerfectInfoActivity.this, LoginActivity.class));
                    PerfectInfoActivity.this.finish();
                } else {
                    Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        DialogUtil.hide();//dismissDialog();
        super.onDestroy();
    }
}