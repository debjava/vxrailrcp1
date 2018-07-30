package com.ddlab.rcp.actions;

public interface ActionHandler {
	
	String getName();
	
	String getToolTip();
	
	String getImage();
	
	void execute();
}
