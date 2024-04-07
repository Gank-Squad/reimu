package com.git.ganksquad.data;

public class NoneData implements Data {
	
	public static final NoneData instance = new NoneData();
	
	private NoneData() {}
	
	@Override
	public int getClassKey() {

		return ClassKeys.NONE_DATA;
	}
}
