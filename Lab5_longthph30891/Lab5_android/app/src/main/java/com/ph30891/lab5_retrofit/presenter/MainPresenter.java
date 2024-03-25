package com.ph30891.lab5_retrofit.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.textfield.TextInputEditText;
import com.ph30891.lab5_retrofit.adapter.DistributorAdapter;
import com.ph30891.lab5_retrofit.databinding.ActivityMainBinding;
import com.ph30891.lab5_retrofit.databinding.LayoutAddUpdateBinding;
import com.ph30891.lab5_retrofit.model.Distributor;
import com.ph30891.lab5_retrofit.presenter.contract.Item_Distributor_handle;
import com.ph30891.lab5_retrofit.presenter.contract.MainInterface;
import com.ph30891.lab5_retrofit.services.HttpRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {
    private MainInterface mainInterface;
    private ArrayList<Distributor> list = new ArrayList<>();
    private HttpRequest httpRequest;
    private DistributorAdapter adapter;
    private ActivityMainBinding binding;


    public MainPresenter(MainInterface mainInterface, ActivityMainBinding binding) {
        this.mainInterface = mainInterface;
        this.binding = binding;
    }
    Callback<ArrayList<Distributor>> getDistributorApi = new Callback<ArrayList<Distributor>>() {
        @Override
        public void onResponse(Call<ArrayList<Distributor>> call, retrofit2.Response<ArrayList<Distributor>> response) {
            if(response.isSuccessful()){
                ArrayList<Distributor> list = response.body();
                getData(list);
                loading(false);
            }
        }

        @Override
        public void onFailure(Call<ArrayList<Distributor>> call, Throwable t) {
            Log.e("Fetch data error", "onFailure: "+t.getMessage());
        }
    };
;
    public  void fetchData(){
        loading(true);
        httpRequest = new HttpRequest();
        httpRequest.callApi().getListDistributor().enqueue(getDistributorApi);
    }
    public void handleSearch(TextInputEditText editText){
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                String key = String.valueOf(editText.getText());
                httpRequest = new HttpRequest();
                httpRequest.callApi().searchDistributor(key).enqueue(getDistributorApi);
                return true;
            }else {
                httpRequest = new HttpRequest();
                httpRequest.callApi().getListDistributor().enqueue(getDistributorApi);
            }
            return false;
        });
    }
    private void getData(ArrayList<Distributor> list){
        adapter = new DistributorAdapter(mainInterface.getContext(),list);
        binding.rcvDistributor.setLayoutManager(new LinearLayoutManager(mainInterface.getContext()));
        binding.rcvDistributor.setAdapter(adapter);
        adapter.onClickItem(new Item_Distributor_handle() {
            @Override
            public void Delete(String id) {
                // delete
                openDelete(id);

            }

            @Override
            public void Update(String id, Distributor distributor) {

                // update
                openDialodUpdate(distributor);
            }
        });
    }

    public void openDialodUpdate(Distributor distributor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainInterface.getContext());
        LayoutInflater layoutInflater = ((Activity) mainInterface.getContext()).getLayoutInflater();
        LayoutAddUpdateBinding auBinding = LayoutAddUpdateBinding.inflate(layoutInflater);
        View view = auBinding.getRoot();
        builder.setView(view);
        builder.setCancelable(true);
        Dialog dialog = builder.create();
        dialog.show();
        //
        auBinding.edName.setText(distributor.getName());
        auBinding.tvTitle.setText("Update");
        //
        auBinding.btnClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        auBinding.btnSave.setOnClickListener(v -> {
            String id = String.valueOf(distributor.getId());
            String name = String.valueOf(auBinding.edName.getText());
            if(validate(name,auBinding)){
                distributor.setName(name);
                handelUpdateDistributor(id,distributor,auBinding);
            }
        });
    }
    public void openDialodAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainInterface.getContext());
        LayoutInflater layoutInflater = ((Activity) mainInterface.getContext()).getLayoutInflater();
        LayoutAddUpdateBinding auBinding = LayoutAddUpdateBinding.inflate(layoutInflater);
        View view = auBinding.getRoot();
        builder.setView(view);
        builder.setCancelable(true);
        Dialog dialog = builder.create();
        dialog.show();
        auBinding.tvTitle.setText("Add");
        //
        auBinding.btnClose.setOnClickListener(v -> {
            dialog.dismiss();
        });
        auBinding.btnSave.setOnClickListener(v -> {
            String name = String.valueOf(auBinding.edName.getText());
            if(validate(name,auBinding)){
                Distributor distributor = new Distributor(name);
                handleAddDistributor(distributor,auBinding);
            }
        });
    }

    private void handleAddDistributor(Distributor distributor,LayoutAddUpdateBinding binding) {
        httpRequest = new HttpRequest();
        httpRequest.callApi().addDistributor(distributor).enqueue(new Callback<Distributor>() {
            @Override
            public void onResponse(Call<Distributor> call, retrofit2.Response<Distributor> response) {
                if(response.isSuccessful()){
                    httpRequest.callApi().getListDistributor().enqueue(getDistributorApi);
                    binding.edName.setText("");
                    Toast.makeText(mainInterface.getContext(), "Add success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Distributor> call, Throwable t) {
                Log.e("Add fail", "onFailure: "+t.getMessage() );
            }
        });
    }
    private void handelUpdateDistributor(String id, Distributor distributor,LayoutAddUpdateBinding binding) {
        httpRequest = new HttpRequest();
        httpRequest.callApi().updateDistributor(id,distributor).enqueue(new Callback<Distributor>() {
            @Override
            public void onResponse(Call<Distributor> call, retrofit2.Response<Distributor> response) {
                if(response.isSuccessful()){
                    httpRequest.callApi().getListDistributor().enqueue(getDistributorApi);
                    binding.edName.setText("");
                    Toast.makeText(mainInterface.getContext(), "Update success", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Distributor> call, Throwable t) {
                Log.e("Update fail", "onFailure: "+t.getMessage() );
            }
        });
    }
    private void openDelete(String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainInterface.getContext());
        builder.setTitle("Message");
        builder.setMessage("Do you want to delete ?");
        builder.setCancelable(false);
        builder.setNegativeButton("Cancel",null);
        builder.setPositiveButton("Delete",(dialog, which) -> {
            httpRequest = new HttpRequest();
            httpRequest.callApi().deleteDistributor(id).enqueue(new Callback<Distributor>() {
                @Override
                public void onResponse(Call<Distributor> call, Response<Distributor> response) {
                    if(response.isSuccessful()){
                        httpRequest.callApi().getListDistributor().enqueue(getDistributorApi);
                        Toast.makeText(mainInterface.getContext(), "Delete success", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Distributor> call, Throwable t) {
                    Log.e("Delete error", "onFailure: "+ t.getMessage());
                }
            });
        });
        builder.create().show();
    }
    private boolean validate(String name,LayoutAddUpdateBinding binding){
        if(TextUtils.isEmpty(name)){
            binding.tilName.setError("Name cannot be empty");
            return false;
        }else {
            binding.tilName.setError(null);
            return true;
        }
    }
    private void loading(boolean isLoading) {
        if(isLoading){
            mainInterface.loading();
        }else {
            mainInterface.stopLoad();
        }
    }

}
