package com.mysuite.commons.entity;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

	@Override
	public String toString() {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(this.getClass().getSimpleName());
		stringBuffer.append("{");
		stringBuffer.append("id=" + id);
		this.appendToString(stringBuffer);
		stringBuffer.append(", version=" + version);
		stringBuffer.append("}");
		return stringBuffer.toString();
	}

	protected abstract void appendToString(final StringBuffer stringBuffer);
}
