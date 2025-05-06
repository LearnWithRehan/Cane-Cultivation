package com.example.bhagesworsurvey;

import java.util.List;

public class SurveyResponse {
    private int success;
    private List<SurveyData> result;

    public int getSuccess() {
        return success;
    }

    public List<SurveyData> getResult() {
        return result;
    }
}
