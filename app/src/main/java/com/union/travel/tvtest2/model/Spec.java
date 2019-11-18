package com.union.travel.tvtest2.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Spec implements Serializable {


	@SerializedName("General")
	private
	GeneralSpec generalSpec;

	@SerializedName("Compability")
	private
	CompabilitySpec compabilitySpec;

	public GeneralSpec getGeneralSpec() {
		return generalSpec;
	}

	public void setGeneralSpec(GeneralSpec generalSpec) {
		this.generalSpec = generalSpec;
	}

	public CompabilitySpec getCompabilitySpec() {
		return compabilitySpec;
	}

	public void setCompabilitySpec(CompabilitySpec compabilitySpec) {
		this.compabilitySpec = compabilitySpec;
	}
}
