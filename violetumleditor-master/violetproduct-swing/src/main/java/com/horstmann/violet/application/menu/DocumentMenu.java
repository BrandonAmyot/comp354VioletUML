package com.horstmann.violet.application.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import com.horstmann.violet.application.gui.MainFrame;
import com.horstmann.violet.framework.file.IGraphFile;
import com.horstmann.violet.framework.injection.resources.ResourceBundleInjector;
import com.horstmann.violet.framework.injection.resources.annotation.ResourceBundleBean;
import com.horstmann.violet.product.diagram.abstracts.IGraph;
import com.horstmann.violet.workspace.IWorkspace;

@ResourceBundleBean(resourceReference = MenuFactory.class)
public class DocumentMenu extends JMenu
{

    /**
     * Default constructor
     * 
     * @param mainFrame where is attached this menu
     * @param factory for accessing to external resources
     */
    @ResourceBundleBean(key = "document")
    public DocumentMenu(final MainFrame mainFrame)
    {
        ResourceBundleInjector.getInjector().inject(this);
        this.mainFrame = mainFrame;
        List<IWorkspace> workspaceList = this.mainFrame.getWorkspaceList();
        setEnabled(!workspaceList.isEmpty());
    }
    
    public void updateMenuItem() {
        List<IWorkspace> workspaceList = this.mainFrame.getWorkspaceList();
        setEnabled(!workspaceList.isEmpty());
        IWorkspace activeWorkspace = this.mainFrame.getActiveWorkspace();
        removeAll();
        for (final IWorkspace aWorkspace : workspaceList) 
        {
            String title = aWorkspace.getTitle();
            final JMenuItem menuItem = new JMenuItem(title);
            add(menuItem);
            menuItem.addActionListener(new ActionListener()
            {
                
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    mainFrame.setActiveWorkspace(aWorkspace);
                    menuItem.setIcon(activeWorkspaceIcon);
                    
                }
            });
            if (aWorkspace.equals(activeWorkspace)) {
                menuItem.setIcon(activeWorkspaceIcon);
            }
        }
        
        String statisticsTitle = "Generate Statistics";
        final JMenuItem statsMenuItem = new JMenuItem(statisticsTitle);
        add(statsMenuItem);
        statsMenuItem.addActionListener(new ActionListener()
        {
          
          @Override
          public void actionPerformed(ActionEvent e)
          {
        	  
        	    StatisticsAnalyzer analyzer;
        	    	analyzer = new StatisticsAnalyzer("graph.seq.violet.html");
        	    	analyzer.writeStats("statsExport.txt");   
          }
        });
        
        statsMenuItem.setEnabled(false);
        
        // Pie Chart for stats.
        String pieChartTitle = "Generate Pie Chart";
        final JMenuItem pieChartMenuItem = new JMenuItem(pieChartTitle);
        add(pieChartMenuItem);
        pieChartMenuItem.addActionListener(new ActionListener()
        {
          
          @Override
          public void actionPerformed(ActionEvent e)
          {
        	  	PieChart pieChart = new PieChart();   
          }
        });
        
        pieChartMenuItem.setEnabled(false);
        
        //Login menu item.
        String loginTitle = "Login";
        final JMenuItem loginMenuItem = new JMenuItem(loginTitle);
        
        LoginMenu login = new LoginMenu();
        login.pieChartMenuItem = pieChartMenuItem;
        login.statsMenuItem = statsMenuItem;
        
        add(loginMenuItem);
        loginMenuItem.addActionListener(new ActionListener()
        {
          
          @Override
          public void actionPerformed(ActionEvent e)
          {
             login.frame.setVisible(true);
          }
        });
    }
    
    
    /**
     * Current editor frame
     */
    private MainFrame mainFrame;
    
    @ResourceBundleBean(key = "document.active.icon")
    private ImageIcon activeWorkspaceIcon;
}
