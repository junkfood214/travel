package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flights {
    private String flightNum;
    private int price;
    private int numSeats;
    private int numAvail;
    private String FromCity;
    private String ArivCity;

}
