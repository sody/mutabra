package com.noname.domain.common;

import com.noname.domain.CodedEntity;
import com.noname.domain.TranslationType;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Ivan Khalopik
 * @since 1.0
 */
@Entity
@Table(name = "FACE")
public class Face extends CodedEntity {

	public Face() {
		super("FACE", TranslationType.STANDARD);
	}

}
