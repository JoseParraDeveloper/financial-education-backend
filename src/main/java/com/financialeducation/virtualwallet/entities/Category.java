package com.financialeducation.virtualwallet.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "categories")
public class Category extends FinancialEducationAndVirtualWalletPersistentObject {
	@NotBlank(message = "Title may not be blank")
	private String title;
	@NotBlank(message = "Description may not be blank")
	private String description;

}
