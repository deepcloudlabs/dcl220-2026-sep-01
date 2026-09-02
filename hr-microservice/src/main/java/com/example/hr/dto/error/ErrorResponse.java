package com.example.hr.dto.error;

import com.example.hexagonal.helper.DataTransferObject;
import com.example.hexagonal.helper.TransferDirectionType;

@DataTransferObject(TransferDirectionType.OUTBOUND)
public record ErrorResponse(String message,String reason) {

}
