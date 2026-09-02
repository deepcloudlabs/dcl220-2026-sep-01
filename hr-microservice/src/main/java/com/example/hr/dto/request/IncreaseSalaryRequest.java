package com.example.hr.dto.request;

import com.example.hexagonal.helper.DataTransferObject;
import com.example.hexagonal.helper.TransferDirectionType;

import jakarta.validation.constraints.Positive;

@DataTransferObject(TransferDirectionType.INBOUND)
public record IncreaseSalaryRequest(@Positive double rate) {

}
