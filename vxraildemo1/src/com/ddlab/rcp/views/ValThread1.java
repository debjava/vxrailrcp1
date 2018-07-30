package com.ddlab.rcp.views;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

public class ValThread1 implements IRunnableWithProgress {
	

    private int workload;

    public ValThread1(int workload)
    {
        this.workload = workload;
    }

    @Override
    public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException
    {
        // Tell the user what you are doing
        monitor.beginTask("Copying files", workload);
        
        
//        SubMonitor subMonitor = SubMonitor.convert(monitor, 100);
        // Do your work
        for(int i = 0; i < workload; i++)
        {
            // Optionally add subtasks
            monitor.subTask("Copying file " + (i+1) + " of "+ workload + "...");

            Thread.sleep(1000);
            
//            subMonitor.setWorkRemaining(70);

            // Tell the monitor that you successfully finished one item of "workload"-many
            monitor.worked(10);
            
            Thread.sleep(2000);

            // Check if the user pressed "cancel"
            if(monitor.isCanceled())
            {
                monitor.done();
                return;
            }
        }
        monitor.worked(9);

        // You are done
        monitor.done();
    }


	
}
