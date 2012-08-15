package shapemining.controller;

import javax.swing.SwingWorker;

/**
 * Generic Command class. Action is fired in separate thread.
 *
 */
public abstract class Command extends SwingWorker<Void, Void> {
	  protected HiddenOnProccess app;
	  
	  public Command(HiddenOnProccess app) {
	    this.app = app;
	  }
	  
	  @Override
	  protected void done() {
		  app.setProcessing(false);
	  }

}
