package com.example.tcbhomework.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class QueryPoolResponse {
    private double calculatedQuantile;
    private int totalCount;
}
