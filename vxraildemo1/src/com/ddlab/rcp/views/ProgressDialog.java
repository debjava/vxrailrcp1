package com.ddlab.rcp.views;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class ProgressDialog extends ProgressMonitorDialog {
  // Run in Background button control
  protected Button runInBackground;
  // To check status of cancel button
  public static boolean isCanceled = false;

  public ProgressDialog(Shell parent) {
    super(parent);
    isCanceled = false;
  }

  protected void createButtonsForButtonBar(Composite parent) {
    createRunInBackgroundButton(parent);
    createCancelButton(parent);
  }

   protected void createRunInBackgroundButton(Composite parent) {
   runInBackground = createButton(parent,IDialogConstants.PROCEED_ID,"RunInBackground", true);
   if (arrowCursor == null)
   arrowCursor = new Cursor(runInBackground.getDisplay(),
   SWT.CURSOR_ARROW);
   runInBackground.setCursor(arrowCursor);
   
   
   }

  /** Notifies that this dialog's button with the given id has been pressed. */
  protected void buttonPressed(int buttonId) {
	  System.out.println("Button Id :::"+buttonId);
    if (IDialogConstants.OK_ID == buttonId) okPressed();
    else if (IDialogConstants.PROCEED_ID == buttonId) {
    	runInBackgroundPressed();
    	
    }
    else cancelPressed();
  }
  /** The cancel button has been pressed. */
  protected void cancelPressed() {
    System.out.println("cancel pressed ");
    this.dialogArea.setVisible(false);
    isCanceled = true;
    super.cancelPressed();
  }

  protected void runInBackgroundPressed() {

    Job job =
        new Job("Progress") {
          public IStatus run(IProgressMonitor monitor) {
            if (!monitor.isCanceled()) {
              try {
                monitor.beginTask("ExecutingOperation", 100);
                // work calculated here
                monitor.done();
              } catch (Exception e) {
                e.printStackTrace();
              }
              return Status.OK_STATUS;
            } else {
              System.out.println("Cancelled Job!");
              monitor.done();
              return Status.CANCEL_STATUS;
            }
          };
        };
    // job.setUser(true);
    job.schedule();
  }
}
