package com.example.tcbhomework.controller.rest;

import com.example.tcbhomework.dto.request.AddingPoolRequest;
import com.example.tcbhomework.dto.request.QueryPoolRequest;
import com.example.tcbhomework.dto.response.AddingPoolResponse;
import com.example.tcbhomework.dto.response.QueryPoolResponse;
import com.example.tcbhomework.exception.PoolNotFoundException;
import com.example.tcbhomework.service.PoolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pool")
public class PoolController {
    private final PoolService poolService;

    public PoolController(PoolService poolService) {
        this.poolService = poolService;
    }

    @PostMapping("/add")
    public ResponseEntity<AddingPoolResponse> addingPoolHandler(@Valid @RequestBody AddingPoolRequest request) {
        return ResponseEntity.ok(AddingPoolResponse.builder()
                .status(poolService.addPool(request.getPoolId(), request.getPoolValues().stream().mapToInt(Integer::intValue).toArray()))
                .build());
    }

    @PostMapping("/query")
    public ResponseEntity<QueryPoolResponse> queryPoolHandler(@Valid @RequestBody QueryPoolRequest request) throws PoolNotFoundException {
        return ResponseEntity.ok(poolService.queryPool(request.getPoolId(), request.getPercentile()));
    }

    @ExceptionHandler(PoolNotFoundException.class)
    public ResponseEntity<String> poolNotFoundHandler(PoolNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PoolId " + exception.getPoolId() +" has not found!");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
