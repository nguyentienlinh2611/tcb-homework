package com.example.tcbhomework.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class QueryPoolRequest {
    @NotNull
    private Integer poolId;

    @Min(value = 0)
    @NotNull
    private Double percentile;

}
