package com.example.tcbhomework.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class AddingPoolRequest {
    @NotNull
    private Integer poolId;
    @NotEmpty
    private List<Integer> poolValues;
}
