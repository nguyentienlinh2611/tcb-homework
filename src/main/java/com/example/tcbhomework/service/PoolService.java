package com.example.tcbhomework.service;

import com.example.tcbhomework.dto.AddingPoolStatusEnum;
import com.example.tcbhomework.dto.response.QueryPoolResponse;
import com.example.tcbhomework.exception.PoolNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

@Service
public class PoolService {
    private final ConcurrentHashMap<Integer, int[]> poolData = new ConcurrentHashMap<>();

    public AddingPoolStatusEnum addPool(int poolId, int[] poolValues) {
        if (poolData.containsKey(poolId)) {
            sortPool(poolId, poolData.get(poolId), poolValues);
            return AddingPoolStatusEnum.APPENDED;
        }
        sortPool(poolId, poolValues);
        poolData.put(poolId, poolValues);
        return AddingPoolStatusEnum.INSERTED;
    }

    // Return total count of element in the pool
    public QueryPoolResponse queryPool(int poolId, double percentile) throws PoolNotFoundException {
        if (poolData.containsKey(poolId)) {
            int[] arr = poolData.get(poolId);
            double quantile = (arr.length + 1) * percentile/100;
            if (quantile >= arr.length) {
                quantile = arr.length;
            }
            int roundDownIndex = (int) quantile - 1;
            double calculatedQuantile = arr[roundDownIndex] + (quantile - (double) roundDownIndex) * (arr[roundDownIndex + 1] - arr[roundDownIndex]);

            return QueryPoolResponse.builder()
                    .totalCount(arr.length)
                    .calculatedQuantile(calculatedQuantile)
                    .build();
        }
        throw new PoolNotFoundException(poolId);
    }

    private void sortPool(int poolId, int[] poolValues) {
        Arrays.sort(poolValues);
        poolData.put(poolId, poolValues);
    }

    private void sortPool(int poolId, int[] poolValues, int[] appendingValues) {
        int[] newPoolValues = IntStream.concat(Arrays.stream(poolValues), Arrays.stream(appendingValues)).toArray();
        Arrays.sort(newPoolValues);
        poolData.put(poolId, newPoolValues);
    }

}
