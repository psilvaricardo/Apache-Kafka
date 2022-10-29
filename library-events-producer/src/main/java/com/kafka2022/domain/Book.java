package com.kafka2022.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book {
    @NotNull // this will enforce to have valid values as part of the Unit Test
    private Integer bookId;
    @NotBlank // this will enforce to have valid values as part of the Unit Test
    private String bookName;
    @NotBlank // this will enforce to have valid values as part of the Unit Test
    private String bookAuthor;
}
