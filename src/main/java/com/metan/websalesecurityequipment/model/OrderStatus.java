package com.metan.websalesecurityequipment.model;

public enum OrderStatus {
    PROCESSING("processing"),
    SHIPPING("shipping"),
    CANCELLED("cancelled"),
    COMPLETED("completed");
    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
