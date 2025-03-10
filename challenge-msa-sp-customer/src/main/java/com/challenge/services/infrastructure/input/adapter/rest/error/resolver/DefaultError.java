package com.challenge.services.infrastructure.input.adapter.rest.error.resolver;

public class DefaultError {
    public static final ErrorModel error_001_ValueNull = new ErrorModel("001","Error, valor nulo.");
    public static final ErrorModel error_002_Customer_Not_Fount = new ErrorModel("002","Customer not found in database.");
    public static final ErrorModel error_003_Unauthorized = new ErrorModel("003","Missing or invalid Authorization header");
    public static final ErrorModel error_004_Invalid_Jwt = new ErrorModel("004","Invalid JWT token");
    public static final ErrorModel error_005_Have_Account = new ErrorModel("005","Cannot delete customer, associated account has transfers");

}
