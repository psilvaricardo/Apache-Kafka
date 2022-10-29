package com.kafka2022.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data // getter, setters, toString, etc
@Builder // is going to give you the fluid API style of building this library
public class LibraryEvent {

    private Integer libraryEventId;
    private LibraryEventType libraryEventType;
    @NotNull
    @Valid // it will be mandatory to pass valid values as part of the unit tests, field-level validations
    private Book book;

}
