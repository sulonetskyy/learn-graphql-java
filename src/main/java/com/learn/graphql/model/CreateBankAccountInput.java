package com.learn.graphql.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateBankAccountInput {
    String firstName;
    int age;
}
