package com.baeldung.reactive.domain;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Document
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {

	@Id
	private ObjectId id;
	private String userId;
	private Long total;
	private String paymentMode;
	private Address shippingAddress;
	private String orderStatus;
	private Date shippingDate;

}
