package com.metan.websalesecurityequipment.model;

//@Entity
//@Table(name = "authentication_methods")
public class AuthenticationMethod {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    @Column(columnDefinition = "nvarchar(255)")
    private String name;
    private int capacity;
}
