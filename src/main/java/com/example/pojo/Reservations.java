package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservations {
    private String custName;
    private int resvType;
    private String resvKey;
    private String resvContent;
}
