package com.ddlab.rcp.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
  private static final String BUNDLE_NAME = "com.ddlab.rcp.messages.messages"; // $NON-NLS-1$
  public static String SystemInformationView_dialogMsg;
  public static String SystemInformationView_firstMsg;
  public static String SystemInformationView_formMsg;
  public static String SystemInformationView_refreshIcon16;
  public static String SystemInformationView_resetFieldMsg;
  public static String SystemInformationView_sectionDescMsg;
  public static String SystemInformationView_sectionHeaderMsg;
  public static String SystemInformationView_sectionToolTipMsg;
  public static String SystemInformationView_titleMsg;

  static {
    // initialize resource bundle
    NLS.initializeMessages(BUNDLE_NAME, Messages.class);
  }

  private Messages() {}
}
