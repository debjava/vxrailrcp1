package com.ddlab.rcp.views;

import java.util.concurrent.TimeUnit;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class MyJob extends Job {

  public MyJob(String name) {
    super(name);
  }

  @Override
  protected IStatus run(IProgressMonitor monitor) {
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
  
  
}
