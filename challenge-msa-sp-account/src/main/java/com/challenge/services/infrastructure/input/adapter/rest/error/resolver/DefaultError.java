package com.challenge.services.infrastructure.input.adapter.rest.error.resolver;

public class DefaultError {
    public static final ErrorModel error_001_ValueNull = new ErrorModel("001","Error, valor nulo.");
    public static final ErrorModel error_002_Account_Not_Fount = new ErrorModel("002","Account not found in database.");
    public static final ErrorModel error_003_Duplicate = new ErrorModel("003","The account and customer relationship must not be duplicated or does not exist.");
    public static final ErrorModel error_004_Transaction_Not_Fount = new ErrorModel("004","Transaction not found in database.");
    public static final ErrorModel error_005_Balance_Not_Available= new ErrorModel("005","Balance not available.");
    public static final ErrorModel error_006_Type= new ErrorModel("006","Transaction type not allowed.");
    public static final ErrorModel error_007_Have_Transactions = new ErrorModel("007","Cannot delete account with existing transactions.");



}
