package com.ddlab.rcp.core;

import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PlatformUI;

import com.ddlab.rcp.views.NavigationView;

public class WorkbenchPartListnerImpl implements IPartListener2 {

  @Override
  public void partActivated(IWorkbenchPartReference partRef) {
    IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    NavigationView nv = (NavigationView) page.findView("vxraildemo1.navigationView");
    Tree tree = nv.getViewer().getTree();
    TreeItem[] treeItems = tree.getItems();
    for (TreeItem item : treeItems) {
      if (item.getText().equals(partRef.getTitle())) {
        item.setChecked(true);
        tree.setSelection(item);
      }
    }
  }

  @Override
  public void partBroughtToTop(IWorkbenchPartReference partRef) {}

  @Override
  public void partClosed(IWorkbenchPartReference partRef) {}

  @Override
  public void partDeactivated(IWorkbenchPartReference partRef) {

  }

  @Override
  public void partOpened(IWorkbenchPartReference partRef) {}

  @Override
  public void partHidden(IWorkbenchPartReference partRef) {}

  @Override
  public void partVisible(IWorkbenchPartReference partRef) {}

  @Override
  public void partInputChanged(IWorkbenchPartReference partRef) {}
}
