package com.ddlab.rcp.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import com.ddlab.rcp.core.Activator;
import com.ddlab.rcp.dialogs.CustomPopupView;

public class ActionUtil {
  public static Action getHelpAction(Shell shell, String text) {
    Action helpAction = null;
    helpAction =
        new Action("Help") {
          @Override
          public void run() {
            CustomPopupView pd =
                new CustomPopupView(
                    shell,
                    SWT.None,
                    true,
                    true,
                    true,
                    true,
                    false,
                    "Information",
                    "More details refer user guide");
            pd.setText(text);
            pd.open();
          }
        };
    helpAction.setImageDescriptor(Activator.getImageDescriptor("/icons/help24.png"));
    helpAction.setToolTipText("Help");
    return helpAction;
  }

  public static Action getAction(Shell shell, ActionHandler handler) {
    Action action = null;
    action =
        new Action(handler.getName()) {
          @Override
          public void run() {
            handler.execute();
          }
        };
    action.setImageDescriptor(Activator.getImageDescriptor(handler.getImage()));
    action.setToolTipText(handler.getToolTip());
    return action;
  }
}
