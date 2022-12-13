package com.dimas.common;

public enum UserStatus {

    NEW, //just created cannot be used for balance operation, account must be created prior
    ACCOUNT_PENDING,//account created command is sent but not completed yet
    NORMAL//user can use balance operation
}
