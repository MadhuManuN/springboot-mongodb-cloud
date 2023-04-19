package com.zkteco.mongodb.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Document(collection = "todos")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoDTO {

	@Id
	private String id;
	@NotNull(message = "ToDo cannot Be Null")
	private String todo;
	@NotNull(message = "Description cannot Be Null")
	private String description;
	@NotNull(message = "Completed cannot Be Null")
	private Boolean completed;
	private Date createAt;
	private Date updatedAt;
	
}
