package com.metan.websalesecurityequipment.model;

public enum  AuthenticationProvider {
    LOCAL("Local"), GOOGLE("Google"), FACEBOOK("Facebook");

    private final String clientName;

    private AuthenticationProvider(String clientName) {
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }
}
