package com.example.bhagesworsurvey;

import java.util.List;

public class ApiResponse {

    private int success;
    private List<VillageResponse> result;

    public int getSuccess() {
        return success;
    }

    public List<VillageResponse> getResult() {
        return result;
    }
}
