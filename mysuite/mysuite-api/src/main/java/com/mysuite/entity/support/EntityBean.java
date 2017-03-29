package com.mysuite.entity.support;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class EntityBean implements Serializable{

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;
	@Version
	private Integer version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
