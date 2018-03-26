package com.qqtech.core.frame.model;

import java.io.Serializable;
import java.sql.Timestamp;

public interface ABaseable extends Serializable {

	public Integer getId();

	public void setId(Integer id);

	public Timestamp getNewAt();

	public void setNewAt(Timestamp newAt);

	public Timestamp getUpdAt();

	public void setUpdAt(Timestamp updAt);
}
