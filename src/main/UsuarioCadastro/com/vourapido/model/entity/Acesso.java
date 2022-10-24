package com.vourapido.model.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Acesso")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Acesso{

	@Id
	@GeneratedValue
	private Long id;
	private String name;

	public Acesso(Long id) {
		super();
		this.id = id;
	}

}