package com.ddlab.rcp.views;

import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import com.ddlab.rcp.actions.ActionUtil;
import com.ddlab.rcp.actions.SysInfoNextHandlerImpl;
import com.ddlab.rcp.contents.SysInfoDesignerContent;
import com.ddlab.rcp.core.Activator;
import com.ddlab.rcp.messages.Messages;

public class SystemInformationView extends ViewPart {

  public static final String ID = "sysInfoView"; // $NON-NLS-1$
  private FormToolkit toolkit;
  private Section section;
  private Form form;

  private SysInfoDesignerContent sysInfoDesigner;

  public SystemInformationView() {
    sysInfoDesigner = new SysInfoDesignerContent(this);
  }

  private void setFormAction(Form form) {
    String helpDesc = Messages.SystemInformationView_firstMsg;
    Action helpAction = ActionUtil.getHelpAction(form.getShell(), helpDesc);

    Action nextAction = ActionUtil.getAction(form.getShell(), new SysInfoNextHandlerImpl());

    form.getToolBarManager().add(nextAction);
    form.getToolBarManager().add(helpAction);

    form.getToolBarManager().update(true);
    form.getMenuManager().update(true);
  }

  private void createResetToolItem(ToolBar toolBar) {
    ToolItem resetItem = new ToolItem(toolBar, SWT.PUSH);
    resetItem.setImage(
        Activator.getImageDescriptor(Messages.SystemInformationView_refreshIcon16).createImage());
    resetItem.setToolTipText(Messages.SystemInformationView_resetFieldMsg);
    resetItem.addSelectionListener(
        new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
            //            MessageDialog.openInformation(new Shell(),
            // Messages.SystemInformationView_titleMsg, Messages.SystemInformationView_dialogMsg);

//            show13();
        	  runInBackgroundProgressService();
          }
        });
  }

  private void runInBackgroundProgressService() {
	    Job job =
	        new Job("Initiating a critical service") {

	          @Override
	          protected IStatus run(IProgressMonitor monitor) {
	            monitor.beginTask("Initiating critical service in background", 100);
	            // execute the task ...
	            
	            try {
	            	for( int i = 0 ; i < 10; i++) {
	            		TimeUnit.SECONDS.sleep(1);
	            		monitor.setTaskName("Completed task "+i+"of 10");
	            		monitor.worked(i*10);
	            	}
	            }
	            catch(Exception e) {
	            	e.printStackTrace();
	            }
	            monitor.done();


	            monitor.done();
	            return Status.OK_STATUS;
	          }
	        };
	    job.schedule();
	    //		job.setUser(true);

	  }



  void show13() {

    Job job =
        new Job("bla1") {

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

    //      PlatformUI.getWorkbench().getProgressService().showInDialog(getSite().getShell(), job);

    job.setUser(true);
    job.schedule();
    PlatformUI.getWorkbench().getProgressService().showInDialog(getSite().getShell(), job);
  }

  @Override
  public void createPartControl(Composite parent) {
    toolkit = new FormToolkit(parent.getDisplay());
    form = toolkit.createForm(parent);
    form.setText(Messages.SystemInformationView_formMsg);
    toolkit.decorateFormHeading(form);

    setFormAction(form);
    section = createSection(form);

    ToolBar bar = new ToolBar(section, SWT.FLAT | SWT.HORIZONTAL);
    createResetToolItem(bar);
    section.setTextClient(bar);

    Composite header = toolkit.createComposite(section, SWT.None);
    // Create a layout of 3 columns
    GridLayout hl = new GridLayout(2, false);
    hl.horizontalSpacing = 10;
    hl.verticalSpacing = 10;
    header.setLayout(hl);

    sysInfoDesigner.designContents(header, form);
    section.setClient(header);
    section.requestLayout();
  }

  @Override
  public void setFocus() {
    form.setFocus();
  }

  private Section createSection(Form form) {
    Section section =
        toolkit.createSection(form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
    section.setVisible(true);
    section.setEnabled(true);
    section.setBounds(1, 10, 365, 230);

    section.setText(Messages.SystemInformationView_sectionHeaderMsg);
    section.setDescription(Messages.SystemInformationView_sectionDescMsg);
    section.setToolTipText(Messages.SystemInformationView_sectionToolTipMsg);
    toolkit.createCompositeSeparator(section);

    GridLayout layout = new GridLayout(1, true);
    GridData gd_composite = new GridData(SWT.LEFT, SWT.BEGINNING, true, false, 1, 1);
    section.setLayoutData(gd_composite);
    section.setLayout(layout);

    return section;
  }

  @Override
  public void dispose() {
    toolkit.dispose();
  }

  public void updateImage(Image image) {
    setTitleImage(image);
  }

  public void updateNormal() {
    String baseImgPath = "/icons/SysInfo16.png";
    Image baseImage = Activator.getImageDescriptor(baseImgPath).createImage();
    setTitleImage(baseImage);
  }
}
