package com.ddlab.rcp.ui.util;

import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.graphics.Image;

import com.ddlab.rcp.core.Activator;

public class ImageUtil {
  public static final Image PROPOSAL_IMAGE =
      FieldDecorationRegistry.getDefault()
          .getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL)
          .getImage();

  public static final Image ERROR_IMAGE =
      FieldDecorationRegistry.getDefault()
          .getFieldDecoration(FieldDecorationRegistry.DEC_ERROR)
          .getImage();
  
  public static Image getImage(String fileName) {
	  return Activator.getImageDescriptor("/icons/"+fileName).createImage();
  }
}
