package com.example.hr.dto.response;

import com.example.hexagonal.helper.DataTransferObject;
import com.example.hexagonal.helper.TransferDirectionType;

@DataTransferObject(TransferDirectionType.OUTBOUND)
public record EmployeePhotoResponse(String identity,String photo) {

}
