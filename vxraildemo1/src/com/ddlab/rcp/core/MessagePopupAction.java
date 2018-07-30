package com.ddlab.rcp.core;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

public class MessagePopupAction extends Action {

  private final IWorkbenchWindow window;

  MessagePopupAction(String text, IWorkbenchWindow window) {
    super(text);
    this.window = window;
    // The id is used to refer to the action in a menu or toolbar
    setId(ICommandIds.CMD_OPEN_MESSAGE);
    // Associate the action with a pre-defined command, to allow key bindings.
    setActionDefinitionId(ICommandIds.CMD_OPEN_MESSAGE);
    setImageDescriptor(com.ddlab.rcp.core.Activator.getImageDescriptor("/icons/Hello24.png"));
  }

  @Override
  public void run() {
//    MessageDialog.openInformation(window.getShell(), "Open", "Open Message Dialog!");
    
	  MessageDialog.openInformation(window.getShell(), "Open", "Don't teae me.");
    
//    show13();
  }
  
  
  
void show13() {
	  
	  Job job = new Job("bla1") {

          @Override
          protected IStatus run(IProgressMonitor monitor) {
              monitor.beginTask("Dummy 2", 100);
              for (int i = 0; i < 100; i++) {
                  if (monitor.isCanceled()) {
                      return Status.CANCEL_STATUS;
                  }
                  System.out.println(i);
                  try {
                      Thread.sleep(100);
                  } catch (InterruptedException e) {

                      e.printStackTrace();
                  }
                  monitor.worked(1);
              }
              monitor.done();
              return Status.OK_STATUS;
          }

      };

//      PlatformUI.getWorkbench().getProgressService().showInDialog(new Shell(), job);
      
      PlatformUI.getWorkbench().getProgressService().showInDialog(new Shell(), job);
      
      job.setUser(true);
      job.schedule();
	  
	  
  }
  
  
}
