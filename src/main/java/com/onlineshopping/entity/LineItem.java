package com.onlineshopping.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode @ToString
@Entity

public class LineItem {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long lineItemId;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id")
	private Product product =new Product();
	private double unitPrice;
	private int quantity;
	private double itemTotal;
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinColumn(name = "cart_id")
	private Cart cart= new Cart();
	

}
