package shapemining.controller;

/**
 * Defines a method that hides(disables) application while it's processing and shows 
 * "Please wait..." modal dialog.  
 *
 */
public interface HiddenOnProccess {

	public void setProcessing(boolean processing);
}
