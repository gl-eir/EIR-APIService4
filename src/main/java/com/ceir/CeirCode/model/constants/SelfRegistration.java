package com.ceir.CeirCode.model.constants;

public enum SelfRegistration {
	EXTERNAL(1), INTERNAL(0),OTHER(2),Enable(1),Disable(0);	
	private int code;

	SelfRegistration(int code) {
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public static SelfRegistration getActionNames(int code) {
		for (SelfRegistration codes : SelfRegistration.values()) {
			if (codes.getCode() == code)
				return codes;
		}

		return null;
	}
}
