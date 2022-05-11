package com.metan.websalesecurityequipment.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String flatAndStreet;
    private String subDistrict;
    private String district;
    private String province;
}
