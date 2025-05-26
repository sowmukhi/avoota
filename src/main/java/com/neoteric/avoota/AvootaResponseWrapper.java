package com.neoteric.avoota;

public class AvootaResponseWrapper {
    private String avootaStatus;
    private AvootaResponse response;

    public String getAvootaStatus() {
        return avootaStatus;
    }

    public void setAvootaStatus(String avootaStatus) {
        this.avootaStatus = avootaStatus;
    }

    public AvootaResponse getResponse() {
        return response;
    }

    public void setResponse(AvootaResponse response) {
        this.response = response;
    }
}
