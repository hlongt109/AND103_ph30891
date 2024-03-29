package com.ph30891.lab8_ph30891.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ph30891.lab8_ph30891.R;
import com.ph30891.lab8_ph30891.adapter.Adapter_item_district_select_GHN;
import com.ph30891.lab8_ph30891.adapter.Adapter_item_province_select_GHN;
import com.ph30891.lab8_ph30891.adapter.Adapter_item_ward_select_GHN;
import com.ph30891.lab8_ph30891.databinding.ActivityOrderBinding;
import com.ph30891.lab8_ph30891.model.District;
import com.ph30891.lab8_ph30891.model.DistrictRequest;
import com.ph30891.lab8_ph30891.model.Fruit;
import com.ph30891.lab8_ph30891.model.GHNItem;
import com.ph30891.lab8_ph30891.model.GHNOrderRequest;
import com.ph30891.lab8_ph30891.model.GHNOrderResponse;
import com.ph30891.lab8_ph30891.model.Order;
import com.ph30891.lab8_ph30891.model.Province;
import com.ph30891.lab8_ph30891.model.ResponseGHN;
import com.ph30891.lab8_ph30891.model.Ward;
import com.ph30891.lab8_ph30891.networking.GHNRequest;
import com.ph30891.lab8_ph30891.networking.HttpRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    private ActivityOrderBinding binding;
    private String WardCode;
    private int DistrictId;
    private int ProvinceId;
    private GHNRequest ghnRequest;
    private HttpRequest httpRequest;

    private Adapter_item_ward_select_GHN adapterItemWardSelectGhn;
    private Adapter_item_district_select_GHN adapterItemDistrictSelectGhn;
    private Adapter_item_province_select_GHN adapterItemProvinceSelectGhn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

    }
    private void init() {
        ghnRequest = new GHNRequest();
        ghnRequest.callAPI().getListProvince().enqueue(responseProvince);
        binding.spnProvince.setOnItemSelectedListener(onItemSelectedListener);
        binding.spnDistrict.setOnItemSelectedListener(onItemSelectedListener);
        binding.spnWard.setOnItemSelectedListener(onItemSelectedListener);
        binding.spnProvince.setSelection(0);

        binding.btnOrder.setOnClickListener(v -> {
//            Toast.makeText(this, WardCode, Toast.LENGTH_SHORT).show();
            if(WardCode.equals("")) return;
            Fruit fruit = (Fruit) getIntent().getExtras().getSerializable("item");
            GHNItem ghnItem = new GHNItem();
            ghnItem.setName(fruit.getName());
            ghnItem.setPrice(fruit.getPrice());
            ghnItem.setCode(fruit.get_id());
            ghnItem.setQuantity(1);
            ghnItem.setWeight(50);
            ArrayList<GHNItem> items = new ArrayList<>();
            items.add(ghnItem);

            GHNOrderRequest ghnOrderRequest = new GHNOrderRequest(
                    binding.edTenNguoiNhan.getText().toString(),
                    binding.edPhoneNumber.getText().toString(),
                    binding.edLocation.getText().toString(),
                    WardCode,
                    DistrictId,
                    items
            );
            ghnRequest = new GHNRequest();
            ghnRequest.callAPI().GHNOrder(ghnOrderRequest).enqueue(responseOrder);
        });
    }
    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == R.id.spnProvince) {
                ProvinceId = ((Province) parent.getAdapter().getItem(position)).getProvinceID();
                DistrictRequest districtRequest = new DistrictRequest(ProvinceId);
                ghnRequest.callAPI().getListDistrict(districtRequest).enqueue(responseDistrict);
            }
            if(parent.getId() == R.id.spnDistrict){
                DistrictId = ((District) parent.getAdapter().getItem(position)).getDistrictID();
                ghnRequest.callAPI().getListWard(DistrictId).enqueue(responseWard);
            }
            if(parent.getId() == R.id.spnWard){
                WardCode = ((Ward)parent.getAdapter().getItem(position)).getWardCode();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    Callback<ResponseGHN<ArrayList<Province>>> responseProvince = new Callback<ResponseGHN<ArrayList<Province>>>() {
        @Override
        public void onResponse(Call<ResponseGHN<ArrayList<Province>>> call, Response<ResponseGHN<ArrayList<Province>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getCode() == 200) {
                    ArrayList<Province> ds = new ArrayList<>(response.body().getData());
                    SetDataSpinProvince(ds);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseGHN<ArrayList<Province>>> call, Throwable t) {

        }
    };
    Callback<ResponseGHN<ArrayList<District>>> responseDistrict = new Callback<ResponseGHN<ArrayList<District>>>() {
        @Override
        public void onResponse(Call<ResponseGHN<ArrayList<District>>> call, Response<ResponseGHN<ArrayList<District>>> response) {
            if(response.isSuccessful()){
                if(response.body().getCode() == 200){
                    ArrayList<District> list = new ArrayList<>(response.body().getData());
                    SetDataSpinDistrict(list);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseGHN<ArrayList<District>>> call, Throwable t) {

        }
    };
    Callback<ResponseGHN<ArrayList<Ward>>> responseWard = new Callback<ResponseGHN<ArrayList<Ward>>>() {
        @Override
        public void onResponse(Call<ResponseGHN<ArrayList<Ward>>> call, Response<ResponseGHN<ArrayList<Ward>>> response) {
            if(response.isSuccessful()){
                if(response.body().getCode() == 200){
                    ArrayList<Ward> list = new ArrayList<>();
                    if(response.body().getData() == null) return;
                    list.addAll(response.body().getData());
                    SetDataSpinWard(list);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseGHN<ArrayList<Ward>>> call, Throwable t) {

        }
    };
    private void SetDataSpinProvince(ArrayList<Province> list) {
        adapterItemProvinceSelectGhn = new Adapter_item_province_select_GHN(this,list);
        binding.spnProvince.setAdapter(adapterItemProvinceSelectGhn);
    }
    private void SetDataSpinDistrict(ArrayList<District> list) {
        adapterItemDistrictSelectGhn = new Adapter_item_district_select_GHN(this,list);
        binding.spnDistrict.setAdapter(adapterItemDistrictSelectGhn);
    }
    private void SetDataSpinWard(ArrayList<Ward> list) {
        adapterItemWardSelectGhn = new Adapter_item_ward_select_GHN(this,list);
        binding.spnWard.setAdapter(adapterItemWardSelectGhn);
    }
    Callback<ResponseGHN<GHNOrderResponse>> responseOrder = new Callback<ResponseGHN<GHNOrderResponse>>() {
        @Override
        public void onResponse(Call<ResponseGHN<GHNOrderResponse>> call, Response<ResponseGHN<GHNOrderResponse>> response) {
            if(response.isSuccessful()){
                if(response.body().getCode() == 200){
                    Toast.makeText(OrderActivity.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                    Order order = new Order();
                    order.setOrder_code(response.body().getData().getOrder_code());
                    order.setId_user("abc1");
                    httpRequest = new HttpRequest();
                    httpRequest.callApi().order(order).enqueue(responseOederDatabase);
                }
            }
        }

        @Override
        public void onFailure(Call<ResponseGHN<GHNOrderResponse>> call, Throwable t) {
            Log.e("Order error", "onFailure: "+t.getMessage());
        }
    };
   Callback<Response<com.ph30891.lab8_ph30891.model.Response<Order>>> responseOederDatabase = new Callback<Response<com.ph30891.lab8_ph30891.model.Response<Order>>>() {
       @Override
       public void onResponse(Call<Response<com.ph30891.lab8_ph30891.model.Response<Order>>> call, Response<Response<com.ph30891.lab8_ph30891.model.Response<Order>>> response) {
           if(response.isSuccessful()){
               if(response.body() != null){
                   Toast.makeText(OrderActivity.this, "Cam on da mua hang", Toast.LENGTH_SHORT).show();
                   finish();
               }
           }
       }

       @Override
       public void onFailure(Call<Response<com.ph30891.lab8_ph30891.model.Response<Order>>> call, Throwable t) {
           Log.e("Order data error", "onFailure: "+t.getMessage());
       }
   };
}
