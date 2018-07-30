package com.ddlab.rcp.dialogs;

import org.eclipse.jface.dialogs.PopupDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class CustomPopupView extends PopupDialog {

  private String contents = "";

  public CustomPopupView(
      Shell parent,
      int shellStyle,
      boolean takeFocusOnOpen,
      boolean persistSize,
      boolean persistLocation,
      boolean showDialogMenu,
      boolean showPersistActions,
      String titleText,
      String infoText) {
    super(
        parent,
        shellStyle,
        takeFocusOnOpen,
        persistSize,
        persistLocation,
        showDialogMenu,
        showPersistActions,
        titleText,
        infoText);
  }

  @Override
  protected void setInfoText(String text) {
    super.setInfoText(text);
  }

  private Text text;

  protected Control createDialogArea(Composite parent) {
    text = new Text(parent, SWT.MULTI | SWT.READ_ONLY | SWT.WRAP | SWT.NO_FOCUS);
    text.setText(contents);
    return text;
  }

  public void setText(String textContents) {
    this.contents = textContents;
  }
}
