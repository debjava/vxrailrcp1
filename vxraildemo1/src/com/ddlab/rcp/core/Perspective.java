package com.ddlab.rcp.core;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.ddlab.rcp.views.NavigationView;

public class Perspective implements IPerspectiveFactory {

  /** The ID of the perspective as specified in the extension. */
  public static final String ID = "vxraildemo1.perspective";
  
//  @Override
//  public void createInitialLayout(IPageLayout layout) {
//	  String editorArea = layout.getEditorArea();
//	    layout.setEditorAreaVisible(false);
//	    
//	    layout.addStandaloneView(NavigationView.ID, false, IPageLayout.LEFT, 0.20f, editorArea);
//	    IFolderLayout folder = layout.createFolder("messages", IPageLayout.LEFT, 0.5f, editorArea);
////	    folder.addPlaceholder(View.ID + ":*");
//	    
//	    folder.addPlaceholder(IPageLayout.ID_EDITOR_AREA + ":*");  
//	    
////	    folder.addView(View.ID);
//
//	    layout.getViewLayout(NavigationView.ID).setCloseable(false);
//  }
  
  

  @Override
  public void createInitialLayout(IPageLayout layout) {
    String editorArea = layout.getEditorArea();
    layout.setEditorAreaVisible(false);

    layout.addStandaloneView(NavigationView.ID, false, IPageLayout.LEFT, 0.20f, editorArea);
    IFolderLayout folder = layout.createFolder("messages", IPageLayout.TOP, 0.5f, editorArea);
    folder.addPlaceholder(View.ID + ":*");
    
    folder.addView(View.ID);

    layout.getViewLayout(NavigationView.ID).setCloseable(false);
  }
}
