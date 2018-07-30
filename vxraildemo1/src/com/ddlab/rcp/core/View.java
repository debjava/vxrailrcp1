package com.ddlab.rcp.core;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.forms.events.HyperlinkAdapter;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import com.ddlab.rcp.actions.ActionUtil;
import com.ddlab.rcp.actions.NextActionHandler;

public class View extends ViewPart {

  public static final String ID = "vxraildemo1.view";

  //  /** The text control that's displaying the content of the email message. */
  //  private Text messageText;

  private FormToolkit toolkit;
  private Section section;
  private Form form;

  private void openBrowser() {

    IWorkbenchBrowserSupport support = PlatformUI.getWorkbench().getBrowserSupport();
    IWebBrowser browser;
    try {
      browser = support.createBrowser("someId");
      browser.openURL(new URL("https://in.yahoo.com/?p=us"));

    } catch (PartInitException e1) {
      e1.printStackTrace();
    } catch (MalformedURLException e1) {
      e1.printStackTrace();
    }
  }

  private void openBrowser1() {
    IWorkbenchBrowserSupport support = PlatformUI.getWorkbench().getBrowserSupport();
    IWebBrowser browser;
    try {
      browser = support.createBrowser(IWorkbenchBrowserSupport.AS_VIEW, "someId", "temp", "temp");
      //      browser.openURL(new URL("https://in.yahoo.com/?p=us"));
      browser.openURL(new URL("https://www.dellemc.com/en-us/index.htm"));

    } catch (PartInitException e1) {
      e1.printStackTrace();
    } catch (MalformedURLException e1) {
      e1.printStackTrace();
    }
  }

  private void setFormAction(Form form) {
	String helpMsg = "The Dell EMC VxRail Appliance family "
	    + "\nsimplifies deployment of virtualized applications."
	    + "\nWith 73% faster deployment1 than a traditional "
	    + "\ninfrastructure, VxRail allows you to leverage "
	    + "\nthe continuous innovation of Dell EMC PowerEdge "
	    + "\nplatforms and VMware vSAN to predictably "
	    + "\nevolve your VMware environment while "
	    + "\ndelivering a staggering 619% ROI1 over five years.";  
    Action helpAction = ActionUtil.getHelpAction(form.getShell(), helpMsg);

    Action nextAction = ActionUtil.getAction(form.getShell(), new NextActionHandler());

    form.getToolBarManager().add(nextAction);
    form.getToolBarManager().add(helpAction);

    form.getToolBarManager().update(true);
    form.getMenuManager().update(true);
  }

  @Override
  public void createPartControl(Composite parent) {
    toolkit = new FormToolkit(parent.getDisplay());
    form = toolkit.createForm(parent);
    form.setText("VxRail Network Validation Tool");
    toolkit.decorateFormHeading(form);

    setFormAction(form);

    GridLayout layout1 = new GridLayout();
    form.getBody().setLayout(layout1);

    Hyperlink link =
        toolkit.createHyperlink(form.getBody(), "Click here to know more about DellEmc.", SWT.WRAP);

    link.addHyperlinkListener(
        new HyperlinkAdapter() {
          public void linkActivated(HyperlinkEvent e) {
            openBrowser1();
          }
        });

    Composite top = new Composite(form.getBody(), SWT.None);
    GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    top.setLayout(layout);
    Composite banner = new Composite(top, SWT.NONE);
    banner.setLayoutData(
        new GridData(
            GridData.HORIZONTAL_ALIGN_FILL, GridData.VERTICAL_ALIGN_BEGINNING, true, false));

    layout = new GridLayout();
    layout.marginHeight = 5;
    layout.marginWidth = 10;
    layout.numColumns = 2;
    banner.setLayout(layout);

    Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
    Label l = new Label(banner, SWT.NONE);
    l.setText(
        "Welcome to VxRail Network Validation Tool, Start validating the VxRail installation");
    l.setFont(boldFont);
  }

  @Override
  public void setFocus() {
    //    messageText.setFocus();
    form.setFocus();
  }
}
