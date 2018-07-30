package com.ddlab.rcp.views;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.progress.ProgressMonitorFocusJobDialog;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.progress.IProgressConstants;
import org.eclipse.ui.progress.IProgressService;

import com.ddlab.rcp.core.Activator;

public class ValidationView extends ViewPart {

  public static final String ID = "validationview";

  @Override
  public void createPartControl(Composite parent) {

    Composite top = new Composite(parent, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.marginHeight = 0;
    layout.marginWidth = 0;
    top.setLayout(layout);
    // top banner
    Composite banner = new Composite(top, SWT.NONE);
    banner.setLayoutData(
        new GridData(
            GridData.HORIZONTAL_ALIGN_FILL, GridData.VERTICAL_ALIGN_BEGINNING, true, false));
    layout = new GridLayout();
    layout.marginHeight = 5;
    layout.marginWidth = 10;
    layout.numColumns = 2;
    banner.setLayout(layout);

    // setup bold font
    Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);

    Label l = new Label(banner, SWT.NONE);
    l.setText("Some message");
    l.setFont(boldFont);
    l.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));

    Button btn = new Button(banner, SWT.None);
    btn.setText("Validate");
    btn.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));

    btn.addSelectionListener(
        new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
            try {
              show13();
            } catch (Exception e1) {
              e1.printStackTrace();
            }
          }
        });

    Button errBtn1 = new Button(banner, SWT.None);
    errBtn1.setText("Show Error Type1");
    errBtn1.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));

    errBtn1.addSelectionListener(
        new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
        	  
        	  Status status = new Status(IStatus.ERROR, "My Plug-in ID", 0,
        	            "Status Error Message", null);
        	  ErrorDialog.openError( new Shell(), "Error Info", "Critical error", status);
          }
        });
    
    
    Button detailedErrBtn = new Button(banner, SWT.None);
    detailedErrBtn.setText("Show Detailed Error");
    detailedErrBtn.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));

    detailedErrBtn.addSelectionListener(
        new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
        	  try {
        		  throw new NullPointerException("Values can not be null");
        	  }
        	  catch(Exception ex) {
//        		  ex.printStackTrace();
        		  MultiStatus status = createMultiStatus(ex.getLocalizedMessage(), ex);
        		  ErrorDialog.openError(new Shell(), "Error", "This is an error", status);
        	  }
        	  
          }
        });
  }
  
  
  private static MultiStatus createMultiStatus(String msg, Throwable t) {

      List<Status> childStatuses = new ArrayList<>();
      StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();

      for (StackTraceElement stackTrace: stackTraces) {
          Status status = new Status(IStatus.ERROR,
                  "com.some.id", stackTrace.toString());
          childStatuses.add(status);
      }

      MultiStatus ms = new MultiStatus("com.example.e4.rcp.todo",
              IStatus.ERROR, childStatuses.toArray(new Status[] {}),
              t.toString(), t);
      return ms;
  }
  
  
  
  

  void show13() {

    //	  WorkbenchPlugin.getDefault().getPreferenceStore().setValue("RUN_IN_BACKGROUND", false);
    // https://www.eclipse.org/forums/index.php/t/262592/

    Job job =
        new Job("Progress Information") {

          @Override
          protected IStatus run(IProgressMonitor monitor) {
            monitor.beginTask("Validation in Progress", 100);
            for (int i = 0; i < 100; i++) {
              if (monitor.isCanceled()) {
                return Status.CANCEL_STATUS;
              }
              //                  System.out.println(i);
              //                  monitor.subTask("Copying file " + (i + 1) + " of " + "100" +
              // "...");

              monitor.subTask("Performing validation " + (i + 1) + " of " + "100" + "...");

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

  void show12() {

    //	  MyD pmd = new MyD(null);

    //	  ProgressMonitorFocusJobDialog pmd = new ProgressMonitorFocusJobDialog(
    // PlatformUI.getWorkbench().getDisplay().getActiveShell() );
    ProgressMonitorFocusJobDialog pmd = new ProgressMonitorFocusJobDialog(null);
    ValThread1 th1 = new ValThread1(10);
    try {
      pmd.run(true, true, th1);

      //    	pmd.run(true, true, new IRunnableWithProgress(){
      //
      //      @Override
      //      public void run(IProgressMonitor monitor) throws InvocationTargetException,
      // InterruptedException {
      //
      //    	  for (int i = 0; i < 10; i++) {
      //              System.out.println("This is a MYJob");
      //              try {
      //                TimeUnit.SECONDS.sleep(1);
      //              } catch (InterruptedException e) {
      //                e.printStackTrace();
      //              }
      //              monitor.subTask("Copying file " + (i + 1) + " of " + "workload" + "...");
      //            }
      //
      ////            return Status.OK_STATUS;
      //
      //      }
      //    });

    } catch (InvocationTargetException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void show11() {
    Job job =
        new WorkspaceJob("name") {
          @Override
          public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {

            for (int i = 0; i < 10; i++) {
              System.out.println("This is a MYJob");
              try {
                TimeUnit.SECONDS.sleep(1);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              monitor.subTask("Copying file " + (i + 1) + " of " + "workload" + "...");
            }

            return Status.OK_STATUS;
          }
        };

    job.setUser(true);
    job.schedule();

    //	  try
    //	   {
    //	     job.join();
    //	   }
    //	  catch (InterruptedException e)
    //	   {
    //	     //
    //	   }
  }

  private void runInUI2() throws InvocationTargetException, InterruptedException {

    Thread th =
        new Thread(
            new Runnable() {

              @Override
              public void run() {

                //    	MyJob runJob = new MyJob("Some Name");
                //    	runJob.setUser(true);
                //		runJob.schedule();

                try {
                  runInUI1();
                } catch (InvocationTargetException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                } catch (InterruptedException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
                }
              }
            });

    th.start();
  }

  static void runInUI1() throws InvocationTargetException, InterruptedException {
    IProgressService progressService = PlatformUI.getWorkbench().getProgressService();

    //    Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
    //    IProgressService progressService = PlatformUI.getWorkbench().getProgressService();

    //		CodeGenJob job = new CodeGenJob(file);
    MyJob runJob = new MyJob("Some Name");
    //    progressService.showInDialog(new Shell(), runJob);
    // runJob.setRule(ISchedulingRule);
    runJob.setUser(true);
    runJob.schedule();

    progressService.runInUI(
        PlatformUI.getWorkbench().getProgressService(),
        new IRunnableWithProgress() {
          public void run(IProgressMonitor monitor) throws InterruptedException {
            // do UI work
            monitor.setCanceled(true);
            monitor.beginTask("Copying files", 10);

            for (int i = 0; i < 10; i++) {
              System.out.println("This is a MYJob");
              TimeUnit.SECONDS.sleep(1);
              monitor.subTask("Copying file " + (i + 1) + " of " + "workload" + "...");

              monitor.worked(i);
            }
            monitor.worked(90);
            monitor.done();
          }
        },
        null);
  }

  static void runInUI() throws InvocationTargetException, InterruptedException {
    IProgressService progressService = PlatformUI.getWorkbench().getProgressService();

    progressService.runInUI(
        PlatformUI.getWorkbench().getProgressService(),
        new IRunnableWithProgress() {
          public void run(IProgressMonitor monitor) throws InterruptedException {
            // do UI work
            monitor.beginTask("Copying files", 10);

            for (int i = 0; i < 10; i++) {
              System.out.println("This is a MYJob");
              TimeUnit.SECONDS.sleep(1);
              monitor.subTask("Copying file " + (i + 1) + " of " + "workload" + "...");
            }
          }
        },
        null);
  }

  private void show6() throws Exception {

    IProgressService progressService = PlatformUI.getWorkbench().getProgressService();
    progressService.runInUI(
        PlatformUI.getWorkbench().getProgressService(),
        new IRunnableWithProgress() {
          public void run(IProgressMonitor monitor) {

            Job job =
                new Job("bla1") {

                  @Override
                  protected IStatus run(IProgressMonitor monitor) {
                    monitor.beginTask("Dummy 2", 100);

                    setProperty(IProgressConstants.PROPERTY_IN_DIALOG, Boolean.TRUE);

                    //		        		            Boolean inDialog =
                    // (Boolean)getProperty(IProgressConstants.PROPERTY_IN_DIALOG);
                    //		        		            if(!inDialog.booleanValue())
                    //		        		               setProperty(IProgressConstants.KEEP_PROPERTY,
                    // Boolean.TRUE);

                    setProperty(IProgressConstants.KEEP_PROPERTY, Boolean.TRUE);

                    for (int i = 0; i < 10; i++) {
                      if (monitor.isCanceled()) {
                        return Status.CANCEL_STATUS;
                      }
                      System.out.println(i);
                      try {
                        TimeUnit.SECONDS.sleep(1);
                      } catch (InterruptedException e) {

                        e.printStackTrace();
                      }
                      monitor.worked(1);
                    }
                    monitor.done();
                    return Status.OK_STATUS;
                  }
                };

            job.setProperty(
                IProgressConstants.ICON_PROPERTY,
                Activator.getImageDescriptor("/icons/sample2.gif"));

            job.setUser(true);
            job.schedule();
          }
        },
        null);
  }

  private void show5() {
    IRunnableWithProgress op = new ValThread1(10);
    ProgressDialog pd = new ProgressDialog(new Shell());
    try {
      pd.run(true, true, op);
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void show4() {
    Job job =
        new Job("Use initiated job") {
          protected IStatus run(IProgressMonitor monitor) {
            setProperty(IProgressConstants.KEEP_PROPERTY, Boolean.TRUE);
            //        	  setTitle("Hati");
            setPartName("Hati");
            for (int i = 0; i < 10; i++) {
              try {
                TimeUnit.SECONDS.sleep(1);
                monitor.subTask("working on " + (i * 10) + "% of work");
              } catch (Exception e) {
                e.printStackTrace();
              }
            }
            monitor.done();

            return Status.OK_STATUS;
          }
        };
    job.setUser(true);
    job.schedule();
  }

  private void show3() {

    Job job =
        new Job("Sending a test message via JMS...") {
          protected IStatus run(IProgressMonitor monitor) {
            monitor.beginTask("Creating JMS objects...", 5);
            monitor.worked(0);

            try {
              TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }

            monitor.setTaskName("Connecting to the micro broker and creating a session...");
            monitor.worked(1);
            if (monitor.isCanceled()) {
              return Status.CANCEL_STATUS;
            } else {
              return Status.OK_STATUS;
            }
          }
        };

    //    PlatformUI.getWorkbench()
    //        .getProgressService()
    //        .showInDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), job);

    PlatformUI.getWorkbench()
        .getProgressService()
        .showInDialog(Display.getCurrent().getActiveShell(), job);

    job.schedule();
  }

  private void show1() {
    IProgressService progressService = PlatformUI.getWorkbench().getProgressService();

    IRunnableWithProgress op = new ValThread1(10);
    try {
      //      progressService.run(true, true, op);

      progressService.runInUI(PlatformUI.getWorkbench().getProgressService(), op, null);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void showProgress5(int workload) {

    WorkspaceJob job =
        new WorkspaceJob("Reevaluate Typing") {

          @Override
          public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {

            // Tell the user what you are doing
            monitor.beginTask("Copying files", workload);

            //              SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
            // Do your work
            for (int i = 0; i < workload; i++) {
              // Optionally add subtasks
              monitor.subTask("Copying file " + (i + 1) + " of " + workload + "...");

              try {
                Thread.sleep(2000);
              } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }

              //                  subMonitor.setWorkRemaining(70);

              // Tell the monitor that you successfully finished one item of "workload"-many
              monitor.worked(1);

              // Check if the user pressed "cancel"
              if (monitor.isCanceled()) {
                monitor.done();
                return Status.CANCEL_STATUS;
              }
            }

            // You are done
            monitor.done();

            return Status.OK_STATUS;
          }
        };

    //        job.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
    //        job.setPriority(Job.INTERACTIVE);

    //        job.setSystem(true);
    job.setUser(true);
    job.schedule();
  }

  private void showProgress1() {
    IRunnableWithProgress op = new ValThread1(10);
    try {

      ProgressMonitorDialog pmd = new ProgressMonitorDialog(new Shell());
      pmd.run(true, true, op);

    } catch (InvocationTargetException e1) {
      e1.printStackTrace();
    } catch (InterruptedException e1) {
      e1.printStackTrace();
    }
  }

  private void showProgress3() {

    Job job =
        new Job("My new job") {

          @Override
          protected IStatus run(IProgressMonitor monitor) {
            monitor.beginTask("Some nice progress message here ...", 100);
            // execute the task ...

            try {
              TimeUnit.SECONDS.sleep(10);
              monitor.setTaskName("asds");
              monitor.worked(30);
              TimeUnit.SECONDS.sleep(5);
              monitor.setTaskName("qqqqqqqqqqqqqqqqq");
              monitor.worked(80);
              TimeUnit.SECONDS.sleep(2);
              monitor.worked(90);
              TimeUnit.SECONDS.sleep(7);
            } catch (InterruptedException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }

            monitor.done();
            return Status.OK_STATUS;
          }
        };
    job.schedule();
    //		job.setUser(true);

  }

  private void showProgress2() {
    Display.getDefault()
        .asyncExec(
            new Runnable() {
              public void run() {
                Job job =
                    new Job("Reload model..") {
                      public IStatus run(IProgressMonitor monitor) {
                        try {

                          monitor.beginTask("Reloading....", 100);
                          // Do some stuff here
                          monitor.worked(20);
                          monitor.subTask("Cleaning...");
                          // Do some stuff here
                          monitor.worked(50);
                          // Do some stuff here
                          monitor.subTask("Re-drawing...");
                          // Do some stuff here
                          if (monitor.isCanceled()) {
                            return Status.CANCEL_STATUS;
                          }
                          monitor.worked(80);
                          monitor.subTask("Reload-Layout...");
                          // Do some stuff here
                          monitor.worked(100);
                        } catch (Exception e) {
                        } finally {
                          monitor.done();
                        }
                        return Status.OK_STATUS;
                      }
                    };
                job.setUser(true);
                job.schedule();
              }
            });
  }

  private void showProgress() {
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

    job.setUser(true);
    job.schedule();
  }

  @Override
  public void setFocus() {}
}
