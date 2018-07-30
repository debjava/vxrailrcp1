package com.ddlab.rcp.actions;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class NextActionHandler implements ActionHandler {

  @Override
  public String getName() {
    return "Next";
  }

  @Override
  public String getToolTip() {
    return "Go to next view";
  }

  @Override
  public String getImage() {
    return "/icons/next24.png";
  }

  @Override
  public void execute() {
    try {
      PlatformUI.getWorkbench()
          .getActiveWorkbenchWindow()
          .getActivePage()
          .showView("sysInfoView", null, IWorkbenchPage.VIEW_ACTIVATE);
    } catch (PartInitException e) { 
      e.printStackTrace();
    }
  }
}
