package com.ddlab.rcp.core;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;
import org.eclipse.ui.internal.WorkbenchPlugin;

import com.ddlab.rcp.views.NavigationView;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

  public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
    super(configurer);
  }

  @Override
  public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
    return new ApplicationActionBarAdvisor(configurer);
  }

  @Override
  public void preWindowOpen() {
    IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
    configurer.setInitialSize(new Point(600, 400));
    configurer.setShowCoolBar(true);
    configurer.setShowStatusLine(true);
    
    configurer.setShowProgressIndicator(true); 
  }

  @Override
  public void postWindowOpen() {
	  //To open in maximized window
    getWindowConfigurer().getWindow().getShell().setMaximized(true);
    //Do not comment out the line, otherwise ProgressBar dialog will not pop up.
  //https://www.eclipse.org/forums/index.php/t/262592/
    WorkbenchPlugin.getDefault().getPreferenceStore().setValue("RUN_IN_BACKGROUND", false);
    // Configure IPartListnerner2, below add the part listener
    WorkbenchPartListnerImpl partImpl = new WorkbenchPartListnerImpl();
    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    page.addPartListener(partImpl);
  }
}
