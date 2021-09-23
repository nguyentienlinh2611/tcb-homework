package com.example.tcbhomework.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PoolNotFoundException extends Exception {
    private final int poolId;
}
