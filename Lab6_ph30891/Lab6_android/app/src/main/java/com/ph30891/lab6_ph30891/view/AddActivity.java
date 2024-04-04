package com.ph30891.lab6_ph30891.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.ph30891.lab6_ph30891.R;
import com.ph30891.lab6_ph30891.adapter.ItemImageAdapter;
import com.ph30891.lab6_ph30891.adapter.ItemImageDetailAdapter;
import com.ph30891.lab6_ph30891.databinding.ActivityAddBinding;
import com.ph30891.lab6_ph30891.model.Distributor;
import com.ph30891.lab6_ph30891.model.Fruit;
import com.ph30891.lab6_ph30891.model.Response;
import com.ph30891.lab6_ph30891.networking.HttpRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AddActivity extends AppCompatActivity {
    ActivityAddBinding binding;
    private HttpRequest httpRequest;
    String id_distributor;
    private ArrayList<Distributor> distributorArrayList;
    private ArrayList<File> ds_image = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private String token;
    private boolean isAdd = true;
    String idUpdate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences = getSharedPreferences("INFO",AddActivity.MODE_PRIVATE);
        token = sharedPreferences.getString("token", "");
        httpRequest = new HttpRequest(token);

        setDataOnViewUpdate();
        init();
    }
    private void setDataOnViewUpdate(){
        Fruit fruit = (Fruit) getIntent().getSerializableExtra("fruit");
        if(fruit != null){
            isAdd = false;
            idUpdate = fruit.get_id();
            setDataImage(fruit.getImages());
            binding.edName.setText(fruit.getName());
            binding.edDes.setText(fruit.getDescription());
            binding.edPrice.setText(String.valueOf(fruit.getPrice()));
            binding.edQuantity.setText(String.valueOf(fruit.getQuantity()));
            binding.edStatus.setText(String.valueOf(fruit.getStatus()));
        }else {
            isAdd = true;
        }
    }
    private void init() {
        httpRequest.callApi().getListDistributor().enqueue(getDistributorAPI);
        binding.spnDistributor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Distributor distributor = distributorArrayList.get(position);
                id_distributor = distributor.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.spnDistributor.setSelection(0);

        binding.imvImage.setOnClickListener(v -> {
            chooseImage();
        });

        binding.btnAdd.setOnClickListener(v -> {
            if(isAdd){
                Map<String , RequestBody> mapRequestBody = new HashMap<>();
                String _name = binding.edName.getText().toString().trim();
                String _quantity = binding.edQuantity.getText().toString().trim();
                String _price = binding.edPrice.getText().toString().trim();
                String _status = binding.edStatus.getText().toString().trim();
                String _description = binding.edDes.getText().toString().trim();

                mapRequestBody.put("name", getRequestBody(_name));
                mapRequestBody.put("quantity", getRequestBody(_quantity));
                mapRequestBody.put("price", getRequestBody(_price));
                mapRequestBody.put("status", getRequestBody(_status));
                mapRequestBody.put("description", getRequestBody(_description));
                mapRequestBody.put("id_distributor", getRequestBody(id_distributor));
                ArrayList<MultipartBody.Part> _ds_image = new ArrayList<>();
                ds_image.forEach(file1 -> {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"),file1);
                    MultipartBody.Part multipartBodyPart = MultipartBody.Part.createFormData("image", file1.getName(),requestFile);
                    _ds_image.add(multipartBodyPart);
                });
                httpRequest.callApi().addFruitWithFileImage(mapRequestBody, _ds_image).enqueue(responseFruit);
            }else {
                // update
                Map<String , RequestBody> mapRequestBody = new HashMap<>();
                String _name = binding.edName.getText().toString().trim();
                String _quantity = binding.edQuantity.getText().toString().trim();
                String _price = binding.edPrice.getText().toString().trim();
                String _status = binding.edStatus.getText().toString().trim();
                String _description = binding.edDes.getText().toString().trim();
                // Lấy thông tin từ Spinner nhà phân phối
                String distributorId = distributorArrayList.get(binding.spnDistributor.getSelectedItemPosition()).getId();
                mapRequestBody.put("name", getRequestBody(_name));
                mapRequestBody.put("quantity", getRequestBody(_quantity));
                mapRequestBody.put("price", getRequestBody(_price));
                mapRequestBody.put("status", getRequestBody(_status));
                mapRequestBody.put("description", getRequestBody(_description));
                mapRequestBody.put("id_distributor", getRequestBody(id_distributor));
                ArrayList<MultipartBody.Part> _ds_image = new ArrayList<>();
                ds_image.forEach(file1 -> {
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"),file1);
                    MultipartBody.Part multipartBodyPart = MultipartBody.Part.createFormData("image", file1.getName(),requestFile);
                    _ds_image.add(multipartBodyPart);
                });
                // Gọi phương thức cập nhật dữ liệu
                if(ds_image != null){
                    Fruit fruit = new Fruit();
                    fruit.setName(_name); fruit.setQuantity(Integer.parseInt(_quantity));
                    fruit.setPrice(Double.parseDouble(_price)); fruit.setStatus(Integer.parseInt(_status));
                    fruit.setDescription(_description);
                    httpRequest.callApi().updateFruitById(idUpdate,mapRequestBody,_ds_image).enqueue(updateFruitApi);
                }else {
                    Fruit fruit = new Fruit();
                    fruit.setName(_name); fruit.setQuantity(Integer.parseInt(_quantity));
                    fruit.setPrice(Double.parseDouble(_price)); fruit.setStatus(Integer.parseInt(_status));
                    fruit.setDescription(_description);

                    httpRequest.callApi().updateFruitByIdNotImg(idUpdate, fruit).enqueue(updateFruitApi);
                }
            }

        });
    }
    Callback<Response<Fruit>> responseFruit = new Callback<Response<Fruit>>() {
        @Override
        public void onResponse(Call<Response<Fruit>> call, retrofit2.Response<Response<Fruit>> response) {
            if (response.isSuccessful()) {
                Log.d("123123", "onResponse: " + response.body().getStatus());
                if (response.body().getStatus()==200) {
                    Toast.makeText(AddActivity.this, "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddActivity.this, MainActivity.class));
                    finish();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Fruit>> call, Throwable t) {
            Log.e("Add fruit with image fail", "onFailure: "+t.getMessage() );
        }
    };
    private RequestBody getRequestBody(String value) {
        return RequestBody.create(MediaType.parse("multipart/form-data"),value);
    }
    Callback<Response<ArrayList<Distributor>>> getDistributorAPI = new Callback<Response<ArrayList<Distributor>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Distributor>>> call, retrofit2.Response<Response<ArrayList<Distributor>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    distributorArrayList = response.body().getData();
                    String[] items = new String[distributorArrayList.size()];

                    for (int i = 0; i < distributorArrayList.size(); i++) {
                        items[i] = distributorArrayList.get(i).getName();
                    }
                    ArrayAdapter<String> adapterSpin = new ArrayAdapter<>(AddActivity.this, android.R.layout.simple_spinner_item, items);
                    adapterSpin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.spnDistributor.setAdapter(adapterSpin);
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Distributor>>> call, Throwable t) {

        }
    };

    private void chooseImage() {
        Log.d("123123", "chooseAvatar: " + 123123);
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        getImage.launch(intent);

    }

    ActivityResultLauncher<Intent> getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == Activity.RESULT_OK) {
                        ds_image.clear();
                        Intent data = o.getData();
                        if (data.getClipData() != null) {
                            int count = data.getClipData().getItemCount();
                            for (int i = 0; i < count; i++) {
                                Uri imageUri = data.getClipData().getItemAt(i).getUri();

                                File file = createFileFormUri(imageUri, "image" + i);
                                ds_image.add(file);
                            }
                        } else if (data.getData() != null) {
                            Uri imageUri = data.getData();
                            File file = createFileFormUri(imageUri, "image");
                            ds_image.add(file);
                        }
                        showImageChoose(ds_image);
                        Glide.with(AddActivity.this)
                                .load(ds_image.get(0))
                                .thumbnail(Glide.with(AddActivity.this).load(R.drawable.image))
                                .centerCrop()
                                .circleCrop()
                                .into(binding.imvImage);
                    }
                }
            });

    private File createFileFormUri(Uri path, String name) {
        File _file = new File(AddActivity.this.getCacheDir(), name + ".png");
        try {
            InputStream in = AddActivity.this.getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(_file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            Log.d("123123", "createFileFormUri: " + _file);
            return _file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    public void showImageChoose(ArrayList<File> images) {
        ItemImageAdapter adapter = new ItemImageAdapter(this, images);
        binding.rcvImage.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.rcvImage.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void setDataImage(ArrayList<String> images){
        ItemImageDetailAdapter adapter = new ItemImageDetailAdapter(this,images);
        binding.rcvImage.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.rcvImage.setAdapter(adapter);
        Glide.with(this).load(images.get(0)).error(R.drawable.image).into(binding.imvImage);
        adapter.notifyDataSetChanged();
    }
    Callback<Response<Fruit>> updateFruitApi = new Callback<Response<Fruit>>() {
        @Override
        public void onResponse(Call<Response<Fruit>> call, retrofit2.Response<Response<Fruit>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus() == 200){
                    Toast.makeText(AddActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddActivity.this,MainActivity.class));
                    finish();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Fruit>> call, Throwable t) {

        }
    };
}