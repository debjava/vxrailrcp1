package com.ddlab.rcp.contents;

import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.Form;

import com.ddlab.rcp.core.Activator;
import com.ddlab.rcp.ui.util.ImageUtil;
import com.ddlab.rcp.views.NavigationView;
import com.ddlab.rcp.views.SystemInformationView;

public class SysInfoDesignerContent {

  private SystemInformationView sysView;

  public SysInfoDesignerContent() {}

  public SysInfoDesignerContent(SystemInformationView sysView) {
	  this.sysView = sysView;
  }

  private void createVxRailNodeLabel(Composite header) {
    Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
    Label l1 = new Label(header, SWT.NONE);
    l1.setText("Number of nodes in VxRail :");
    l1.setFont(boldFont);
    l1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
  }

  private void addListenerForNodeText(Text txt, Form form) {
    ControlDecoration decorator = new ControlDecoration(txt, SWT.CENTER);
    decorator.setDescriptionText("Only numeric values are allowed");
    decorator.setImage(ImageUtil.PROPOSAL_IMAGE);

    IViewPart viewPart =
        PlatformUI.getWorkbench()
            .getActiveWorkbenchWindow()
            .getActivePage()
            .findView("vxraildemo1.navigationView");

    final NavigationView navView = (NavigationView) viewPart;

    txt.addModifyListener(
        new ModifyListener() {
          @Override
          public void modifyText(ModifyEvent e) {
            if (txt.getText().length() > 0) {
              form.setMessage(null, IMessageProvider.NONE);
              decorator.setImage(ImageUtil.PROPOSAL_IMAGE);
              decorator.show();
              navView.updateNormal();
              sysView.updateNormal();
            } else {
              form.setMessage("Error in the section", IMessageProvider.ERROR);
              decorator.setImage(ImageUtil.ERROR_IMAGE);
              decorator.show();

              navView.updateImage("System Information");
              
              String baseImgPath = "/icons/SysInfo16.png";
              Image baseImage = Activator.getImageDescriptor(baseImgPath).createImage();
              Image errorTitleImage =
                  new DecorationOverlayIcon(
                          baseImage,
                          PlatformUI.getWorkbench()
                              .getSharedImages()
                              .getImageDescriptor(ISharedImages.IMG_DEC_FIELD_ERROR),
                          IDecoration.BOTTOM_LEFT)
                      .createImage();
              
              sysView.updateImage(errorTitleImage);
            }
          }
        });
  }

  private void createNodeTxt(Composite header, Form form) {
    Text txt = new Text(header, SWT.BORDER);
    txt.setText("44");

    GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
    gd.heightHint = 15;
    gd.widthHint = 60;
    gd.horizontalAlignment = SWT.FILL;
    gd.verticalAlignment = SWT.FILL;
    txt.setLayoutData(gd);

    addListenerForNodeText(txt, form);
  }

  private void createVersionLabel(Composite header) {
    Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
    Label versionLbl = new Label(header, SWT.NONE);
    versionLbl.setText("Version :");
    versionLbl.setFont(boldFont);
    versionLbl.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
  }

  private void createVersionCombo(Composite header) {
    Combo versionCombo = new Combo(header, SWT.DROP_DOWN | SWT.READ_ONLY);
    String[] ITEMS = {"4.5.x", "4.3.1"}; 
    versionCombo.setItems(ITEMS);
    versionCombo.select(0);

    GridData gd1 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
    gd1.heightHint = 15;
    gd1.widthHint = 60;

    versionCombo.setLayoutData(gd1);
  }

  private void createServerPortSpeed(Composite header) {
    Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
    Label serverPortSpeed = new Label(header, SWT.NONE);
    serverPortSpeed.setText("Server Port Speed :"); // $NON-NLS-1$
    serverPortSpeed.setFont(boldFont);
    serverPortSpeed.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
  }

  private void createPortSpeedCombo(Composite header) {
    Combo portSpeedCombo = new Combo(header, SWT.DROP_DOWN | SWT.READ_ONLY);
    String[] ITEMS1 = {"10Gbe", "20GB"}; // $NON-NLS-1$ //$NON-NLS-2$
    portSpeedCombo.setItems(ITEMS1);
    portSpeedCombo.select(0);

    GridData gd2 = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
    gd2.heightHint = 15;
    gd2.widthHint = 60;

    portSpeedCombo.setLayoutData(gd2);
  }

  public void designContents(Composite header, Form form) {
    createVxRailNodeLabel(header);
    createNodeTxt(header, form);
    createVersionLabel(header);
    createVersionCombo(header);
    createServerPortSpeed(header);
    createPortSpeedCombo(header);
  }
}
