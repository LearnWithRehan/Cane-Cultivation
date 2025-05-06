package com.example.bhagesworsurvey;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SurveyDetailActivity extends AppCompatActivity {

    private static final String TAG = "SurveyDetailActivity";

    // Views declaration
    TextView tvMachineNo, tvVCode, tvGCode, tvGName, tvFName, tvVName, tvVariety, tvCtype, tvPlotNo, tvArea,sdate,plantaiondt;
    Spinner valcanetype, valsugarpalnted, valirragations, valweeding, valHelth, valBanding;
    RadioGroup radiogroupfertilizers, radiogroupinsticide, radiogroupdiasease, radiogroupinsecticide, radiogroupEarthingup, radiogrouptopdresshing;
    Button btnSave;

    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_detail);

        initializeViews();
        setupRetrofit();
        getIntentData();
        setupButtonClickListener();
    }

    private void initializeViews() {
        tvMachineNo = findViewById(R.id.tvMachineNo);
        tvVCode = findViewById(R.id.tvVCode);
        tvGCode = findViewById(R.id.tvGCode);
        tvGName = findViewById(R.id.tvGName);
        tvFName = findViewById(R.id.tvFName);
        tvVName = findViewById(R.id.tvVName);
        tvVariety = findViewById(R.id.tvVariety);
        tvCtype = findViewById(R.id.tvCtype);
        tvPlotNo = findViewById(R.id.tvPlotNo);
        tvArea = findViewById(R.id.tvArea);
        sdate = findViewById(R.id.sdate);
        plantaiondt = findViewById(R.id.txtSelectedDate);

        valcanetype = findViewById(R.id.valcanetype);
        valsugarpalnted = findViewById(R.id.valsugarpalnted);
        valirragations = findViewById(R.id.valirragations);
        valweeding = findViewById(R.id.valweeding);
        valHelth = findViewById(R.id.valHelth);
        valBanding = findViewById(R.id.valBanding);

        radiogroupfertilizers = findViewById(R.id.radiogroupfertilizers);
        radiogroupinsticide = findViewById(R.id.radiogroupinsticide);
        radiogroupdiasease = findViewById(R.id.radiogroupdiasease);
        radiogroupinsecticide = findViewById(R.id.radiogroupinsecticide);
        radiogroupEarthingup = findViewById(R.id.radiogroupEarthingup);
        radiogrouptopdresshing = findViewById(R.id.radiogrouptopdresshing);

        btnSave = findViewById(R.id.btnsave);


        plantaiondt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ctype = extractValue(tvCtype.getText().toString());
                if (ctype.equals("P")){
                    final Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    // Use the correct context (e.g., YourActivity.this)
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            view.getContext(), // or YourActivity.this
                            (datePicker, selectedYear, selectedMonth, selectedDay) -> {
                                String selectedDate = selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear;
                                plantaiondt.setText(selectedDate);
                            },
                            year, month, day
                    );

                    datePickerDialog.show();
                }else {
                    Toast.makeText(view.getContext(), "Date selection is not allowed for type R", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }

    private void setupRetrofit() {
        // Add logging interceptor to see request/response in logcat
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://122.176.50.103:8080/rehan/bsc2425/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("SurveyData")) {
            SurveyData surveyData = (SurveyData) intent.getSerializableExtra("SurveyData");
            if (surveyData != null) {
                populateFields(surveyData);
            }
        }
    }

    private void populateFields(SurveyData surveyData) {
        tvMachineNo.setText("Machine No:-  " + surveyData.getMachineNo());
        tvVCode.setText("Village Code:-  " + surveyData.getV_code());
        tvGCode.setText("Grower Code:-  " + surveyData.getG_code());
        tvGName.setText("Grower Name:-  " + surveyData.getG_name());
        tvFName.setText("Father Name:-  " + surveyData.getF_name());
        tvVName.setText("Village Name:-  " + surveyData.getV_Name());
        sdate.setText("Survey Date:-  " + surveyData.getSdate());
        tvVariety.setText("Variety:-  " + surveyData.getVariety());
        tvCtype.setText("Type:-  " + surveyData.getCtype());
        tvPlotNo.setText("Plot No:-  " + surveyData.getPlotNo());
        tvArea.setText("Katha:-  " + surveyData.getAREA());

    }

    private void setupButtonClickListener() {
        btnSave.setOnClickListener(v -> {
            if (validateForm()) {
                gatherFormData();
                Intent intent = new Intent(SurveyDetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }



    private boolean validateForm() {
        // Validate all Spinners
        if (valcanetype.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select Cane Type", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (valsugarpalnted.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select Sugar Planted Method", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (valirragations.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select Irrigations Times", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (valweeding.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select Weeding times", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (valHelth.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select Health condition", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (valBanding.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select Banding", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validate all RadioGroups
        if (radiogroupfertilizers.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select Fertilizers Used option", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (radiogroupinsticide.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select Insticide Used option", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (radiogroupdiasease.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select Disease option", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (radiogroupinsecticide.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select Insecticide option", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (radiogroupEarthingup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select Earthing up option", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (radiogrouptopdresshing.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select Topdressing option", Toast.LENGTH_SHORT).show();
            return false;
        }

        // All validations passed
        return true;
    }


    private void gatherFormData() {
        try {
            // Extract values from views
            String machineNo = extractValue(tvMachineNo.getText().toString());
            String villageCode = extractValue(tvVCode.getText().toString());
            String growerCode = extractValue(tvGCode.getText().toString());
            String growerName = extractValue(tvGName.getText().toString());
            String fatherName = extractValue(tvFName.getText().toString());
            String villageName = extractValue(tvVName.getText().toString());
            String Surveydt = extractValue(sdate.getText().toString());
            String variety = extractValue(tvVariety.getText().toString());
            String cropType = extractValue(tvCtype.getText().toString());
            String plotNo = extractValue(tvPlotNo.getText().toString());
            String area = extractValue(tvArea.getText().toString());
            String Plantationdt = plantaiondt.getText().toString();

            // Get Spinner values
            String caneType = valcanetype.getSelectedItem().toString();
            String sugarPlantedMethod = valsugarpalnted.getSelectedItem().toString();
            String irrigationTimes = valirragations.getSelectedItem().toString();
            String weedingTimes = valweeding.getSelectedItem().toString();
            String healthCondition = valHelth.getSelectedItem().toString();
            String bandingType = valBanding.getSelectedItem().toString();


            // Get RadioButton values safely
            boolean fertilizersUsed = getRadioValue(radiogroupfertilizers);
            boolean insecticideUsed = getRadioValue(radiogroupinsticide);
            boolean diseasePresent = getRadioValue(radiogroupdiasease);
            boolean insecticidePresent = getRadioValue(radiogroupinsecticide);
            boolean earthingUp = getRadioValue(radiogroupEarthingup);
            boolean topdressing = getRadioValue(radiogrouptopdresshing);

            // Create data object
            CultivationData data = new CultivationData(
                    machineNo, villageCode, growerCode, growerName, fatherName, villageName,Surveydt,
                    variety, cropType, plotNo, area, caneType, sugarPlantedMethod, irrigationTimes,
                    weedingTimes, healthCondition, bandingType, fertilizersUsed, insecticideUsed,
                    diseasePresent, insecticidePresent, earthingUp, topdressing, Plantationdt
            );

            // Save data
            saveCultivationData(data);
        } catch (Exception e) {
            Log.e(TAG, "Error gathering form data", e);
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String extractValue(String textWithLabel) {
        // Remove label prefix if present
        if (textWithLabel.contains(":-")) {
            return textWithLabel.split(":-")[1].trim();
        }
        return textWithLabel.trim();
    }

    private boolean getRadioValue(RadioGroup radioGroup) {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId != -1) {
            RadioButton radioButton = findViewById(selectedId);
            return radioButton.getText().toString().equalsIgnoreCase("Y");
        }
        return false;
    }

    private void saveCultivationData(CultivationData data) {
        Call<ApiResponseculti> call = apiService.saveCultivationData(data);
        call.enqueue(new Callback<ApiResponseculti>() {
            @Override
            public void onResponse(Call<ApiResponseculti> call, Response<ApiResponseculti> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        handleApiResponse(response.body());
                    } else {
                        Toast.makeText(SurveyDetailActivity.this,
                                "Empty response from server", Toast.LENGTH_LONG).show();
                    }
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<ApiResponseculti> call, Throwable t) {
                Log.e(TAG, "API call failed", t);
                Toast.makeText(SurveyDetailActivity.this,
                        "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleApiResponse(ApiResponseculti response) {
        if (response.isSuccess()) {
            Toast.makeText(this, "Data saved successfully!", Toast.LENGTH_SHORT).show();
            // Optionally finish the activity or clear the form
        } else {
            String errorMsg = "Failed to save data";
            if (response.getMessage() != null) {
                errorMsg += ": " + response.getMessage();
            }
            Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show();
        }
    }

    private void handleErrorResponse(Response<ApiResponseculti> response) {
        try {
            String errorBody = response.errorBody() != null ?
                    response.errorBody().string() : "No error details";

            Log.e(TAG, "Server error: " + response.code() + " - " + errorBody);
            Toast.makeText(this,
                    "Server error: " + response.code() + "\n" + errorBody,
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e(TAG, "Error parsing error response", e);
        }
    }
}