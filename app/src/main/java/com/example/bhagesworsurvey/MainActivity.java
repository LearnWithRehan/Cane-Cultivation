package com.example.bhagesworsurvey;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText edtvcodeval, edtvnameval, edtgcodeval, edtGnameval;
    private ApiService apiService;
    private RecyclerView recyclerView;
    private Button btnShowData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        btnShowData = findViewById(R.id.btnshowdata);
        edtvcodeval = findViewById(R.id.edtvcodeval);
        edtvnameval = findViewById(R.id.edtvnameval);
        edtgcodeval = findViewById(R.id.edtGcodeval);
        edtGnameval = findViewById(R.id.edtgnameval);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apiService = RetrofitClient.getClient().create(ApiService.class);

        btnShowData.setOnClickListener(view -> {
            String vCode = edtvcodeval.getText().toString().trim();
            String gCode = edtgcodeval.getText().toString().trim();
            if (vCode.isEmpty()){
                Toast.makeText(getApplicationContext(),"Enter Village Number",Toast.LENGTH_SHORT).show();
            } else if (gCode.isEmpty()) {
                Toast.makeText(getApplicationContext(),"Enter Grower Code",Toast.LENGTH_SHORT).show();
            }else {

                if (!vCode.isEmpty() && !gCode.isEmpty()) {
                    fetchSurveyData(vCode, gCode);
                }
            }

        });

        // Text watcher for dynamic fetching of village and grower name
        edtvcodeval.addTextChangedListener(new TextWatcher() {
            private Timer timer = new Timer();
            private final long DELAY = 50;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> {
                            String vCode = s.toString().trim();
                            if (!vCode.isEmpty()) {
                                fetchVillageName(vCode);
                            }
                        });
                    }
                }, DELAY);
            }
        });

        edtgcodeval.addTextChangedListener(new TextWatcher() {
            private Timer timer = new Timer();
            private final long DELAY = 50;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                timer.cancel();
            }

            @Override
            public void afterTextChanged(Editable s) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(() -> {
                            String gCode = s.toString().trim();
                            if (!gCode.isEmpty()) {
                                fetchGrowerName(gCode);
                            }
                        });
                    }
                }, DELAY);
            }
        });
    }

    private void fetchSurveyData(String vCode, String gCode) {
        Call<SurveyResponse> call = apiService.getSurveyData(vCode, gCode);
        call.enqueue(new Callback<SurveyResponse>() {
            @Override
            public void onResponse(Call<SurveyResponse> call, Response<SurveyResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getSuccess() == 1) {
                    List<SurveyData> list = response.body().getResult();
                    SurveyAdapter adapter = new SurveyAdapter(list, data -> {
                        // Handle item click
                        Toast.makeText(MainActivity.this, "Selected: " + data.getG_name(), Toast.LENGTH_SHORT).show();
                    });
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<SurveyResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchGrowerName(String gCode) {
        String gNo = edtgcodeval.getText().toString().trim();
        String gVill = edtvcodeval.getText().toString().trim();
        if (gNo.isEmpty() || gVill.isEmpty()) {
            Toast.makeText(this, "Please enter both V_code and G_code", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<GrowerResponse> call = apiService.getGrowerName(gNo, gVill);
        call.enqueue(new Callback<GrowerResponse>() {
            @Override
            public void onResponse(Call<GrowerResponse> call, Response<GrowerResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().success == 1) {
                    List<Grower> growers = response.body().result;
                    if (!growers.isEmpty()) {
                        edtGnameval.setText(growers.get(0).getG_name());
                    } else {
                        edtGnameval.setText("Not found");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GrowerResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "API error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchVillageName(String vCode) {
        Call<ApiResponse> call = apiService.getVillageName(vCode);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getSuccess() == 1) {
                    List<VillageResponse> villageList = response.body().getResult();
                    if (!villageList.isEmpty()) {
                        edtvnameval.setText(villageList.get(0).getV_name());
                    } else {
                        edtvnameval.setText("Not found");
                    }
                } else {
                    edtvnameval.setText("Error");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                edtvnameval.setText("Failed");
            }
        });
    }
}
