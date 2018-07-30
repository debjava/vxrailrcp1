package com.ddlab.rcp.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;

import com.ddlab.rcp.actions.ActionUtil;
import com.ddlab.rcp.actions.SysInfoNextHandlerImpl;
import com.ddlab.rcp.core.Activator;
import com.ddlab.rcp.messages.Messages;
import com.ddlab.rcp.ui.util.ImageUtil;

public class NetworkServiceView extends ViewPart {

  public static final String ID = "networkServiceView";
  private Form form;

  private void setFormAction(Form form) {
    String helpDesc = "Help message for this panel";
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
            MessageDialog.openInformation(
                new Shell(),
                Messages.SystemInformationView_titleMsg,
                Messages.SystemInformationView_dialogMsg);
          }
        });
  }

  @Override
  public void createPartControl(Composite parent) {
    FormToolkit toolkit = new FormToolkit(parent.getDisplay());
    form = toolkit.createForm(parent);
    form.setText("Network Services");
    toolkit.decorateFormHeading(form);

    setFormAction(form);

    Section section1 = createSection1(form, toolkit);
    ToolBar bar = new ToolBar(section1, SWT.FLAT | SWT.HORIZONTAL);
    createResetToolItem(bar);
    section1.setTextClient(bar);

    Section section2 = createSection2(form, toolkit);
    ToolBar bar1 = new ToolBar(section2, SWT.FLAT | SWT.HORIZONTAL);
    createResetToolItem(bar1);
    section2.setTextClient(bar1);
  }

  @Override
  public void setFocus() {
    form.setFocus();
  }

  @Override
  public void dispose() {}

  private Section createSection1(Form form, FormToolkit toolkit) {
    Section section =
        toolkit.createSection(form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
    section.setVisible(true);
    section.setEnabled(true);
    section.setBounds(1, 10, 365, 330);

    section.setText("DNS");
    section.setDescription("You can add list of DNS by clicking add and remove buttons");
    section.setToolTipText("DNS Entries");
    toolkit.createCompositeSeparator(section);

    Composite dnsComposite = toolkit.createComposite(section, SWT.None);
    // Create a layout of 3 columns
    GridLayout hl = new GridLayout(3, false);
    hl.horizontalSpacing = 10;
    hl.verticalSpacing = 10;
    dnsComposite.setLayout(hl);

    Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
    Label l1 = new Label(dnsComposite, SWT.NONE);
    l1.setText("Add DNS Server :");
    l1.setFont(boldFont);
    l1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

    Text txt = new Text(dnsComposite, SWT.BORDER); // SWT.BORDER
    txt.setText("255.255.255.255");

    //		txt.setFont(FontUtil.getFontBySize(12));

    GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
    gd.heightHint = 15;
    gd.widthHint = 120;
    gd.horizontalAlignment = SWT.FILL;
    gd.verticalAlignment = SWT.FILL;
    txt.setLayoutData(gd);

    Button btn = new Button(dnsComposite, SWT.PUSH);
    btn.setText("+");

    GridData btnGrid = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
    btn.setLayoutData(btnGrid);

    Table table = new Table(dnsComposite, SWT.MULTI | SWT.FULL_SELECTION);
    table.setHeaderVisible(false);
    table.setLinesVisible(false);

    GridData tableGrid = new GridData(SWT.FILL, SWT.TOP, true, false);
    tableGrid.horizontalSpan = 2;
    tableGrid.heightHint = 80;

    table.setLayoutData(tableGrid);

    addMenuToTable(dnsComposite, table);

    final TableColumn tc1 = new TableColumn(table, SWT.None);
    final TableColumn tc2 = new TableColumn(table, SWT.None);
    //        final TableColumn tc3 = new TableColumn(table, SWT.None);
    tc1.setText("DNS Server Name");
    tc2.setText("DNS IP Address");
    //        tc3.setText("Address");

    final TableItem item1 = new TableItem(table, SWT.NONE);
    item1.setText(new String[] {"DNS Server 1", "255.255.255.255"});
    final TableItem item2 = new TableItem(table, SWT.NONE);
    item2.setText(new String[] {"DNS Server 2", "255.255.255.255"});
    final TableItem item3 = new TableItem(table, SWT.NONE);
    item3.setText(new String[] {"DNS Server 3", "255.255.255.255"});

    btn.addSelectionListener(
        new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
            TableItem item = new TableItem(table, SWT.NONE);
            item.setText(new String[] {"DNS Server 5", "255.255.255.255"});
          }
        });

    for (int i = 0, n = table.getColumnCount(); i < n; i++) {
      table.getColumn(i).pack();
    }

    section.setClient(dnsComposite); // Important
    section.requestLayout();

    return section;
  }

  private void addMenuToTable(Composite dnsComposite, Table table) {
    Menu menu = new Menu(dnsComposite.getShell(), SWT.POP_UP);
    table.setMenu(menu);
    MenuItem item = new MenuItem(menu, SWT.PUSH);
    item.setText("Delete");
    item.setImage(ImageUtil.getImage("delete16.png"));
    item.addListener(SWT.Selection, event -> table.remove(table.getSelectionIndices()));
  }

  private Section createSection2(Form form, FormToolkit toolkit) {
    Section section =
        toolkit.createSection(form.getBody(), Section.DESCRIPTION | Section.TITLE_BAR);
    section.setVisible(true);
    section.setEnabled(true);
    section.setBounds(375, 10, 365, 230);

    section.setText("NTP");
    section.setDescription("Add the IP addresses for NTP");
    section.setToolTipText("NTP");
    toolkit.createCompositeSeparator(section);

    GridLayout layout = new GridLayout(1, true);
    GridData gd_composite = new GridData(SWT.LEFT, SWT.BEGINNING, true, false, 1, 1);
    section.setLayoutData(gd_composite);
    section.setLayout(layout);

    Composite ntpComposite = toolkit.createComposite(section, SWT.None);
    // Create a layout of 3 columns
    GridLayout hl = new GridLayout(3, false);
    hl.horizontalSpacing = 10;
    hl.verticalSpacing = 10;
    ntpComposite.setLayout(hl);

    Font boldFont = JFaceResources.getFontRegistry().getBold(JFaceResources.DEFAULT_FONT);
    Label l1 = new Label(ntpComposite, SWT.NONE);
    l1.setText("Add NTP Server :");
    l1.setFont(boldFont);
    l1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));

    Text txt = new Text(ntpComposite, SWT.BORDER); // SWT.BORDER
    txt.setText("255.255.255.255");

    //		txt.setFont(FontUtil.getFontBySize(12));

    GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
    gd.heightHint = 15;
    gd.widthHint = 120;
    gd.horizontalAlignment = SWT.FILL;
    gd.verticalAlignment = SWT.FILL;
    txt.setLayoutData(gd);

    Button btn = new Button(ntpComposite, SWT.PUSH);
    btn.setText("+");

    GridData btnGrid = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
    btn.setLayoutData(btnGrid);

    //		final List list = new List(ntpComposite, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
    List list = new List(ntpComposite, SWT.MULTI | SWT.V_SCROLL);
    addMenuToList(list, ntpComposite);

    //		GridData listGrid = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
    GridData listGrid = new GridData(SWT.FILL, SWT.TOP, true, true);
    listGrid.horizontalSpan = 2;
    listGrid.heightHint = 100;
    list.setLayoutData(listGrid);

    addButtonListenerForList(btn, txt, list);

    section.setClient(ntpComposite);
    section.requestLayout();

    return section;
  }

  private void addButtonListenerForList(Button btn, Text txt, List list) {
    btn.addSelectionListener(
        new SelectionAdapter() {
          @Override
          public void widgetSelected(SelectionEvent e) {
            list.add("NTP Server - " + " - " + "    255.255.255.255 ");
          }
        });
  }

  private void addMenuToList(List list, Composite ntpComposite) {
    Menu menu = new Menu(ntpComposite.getShell(), SWT.POP_UP);
    list.setMenu(menu);
    MenuItem item = new MenuItem(menu, SWT.PUSH);
    item.setText("Delete");
    item.setImage(ImageUtil.getImage("delete16.png"));
    item.addListener(
        SWT.Selection,
        new Listener() {
          public void handleEvent(Event event) {
            list.remove(list.getSelectionIndices());
          }
        });
  }
}
