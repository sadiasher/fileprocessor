package com.processor.modal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Property {

	@JsonProperty("MAPBLKLOT")
	private String MAPBLKLOT;
	
	@JsonProperty("BLKLOT")
	private String BLKLOT;
	
	@JsonProperty("BLOCK_NUM")
	private String BLOCK_NUM;
	
	@JsonProperty("LOT_NUM")
	private String LOT_NUM;
	
	@JsonProperty("FROM_ST")
	private String FROM_ST;
	
	@JsonProperty("TO_ST")
	private String TO_ST;
	
	@JsonProperty("STREET")
	private String STREET;
	
	@JsonProperty("ST_TYPE")
	private String ST_TYPE;
	
	@JsonProperty("ODD_EVEN")
	private String ODD_EVEN;
	
}
