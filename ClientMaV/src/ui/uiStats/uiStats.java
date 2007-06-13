package ui.uiStats;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;

import src.util.UtilORB;

public class uiStats extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanel;
	private static uiStats instance = null;
	private ChartPanel cPanel = null;
	
	/**
	 * This method initializes 
	 * 
	 */
	private uiStats() {
		super();
		initialize();
		instance = this;
	}
	
	public static uiStats getInstance(){
		if(instance == null)
			instance = new uiStats();
		return instance;
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(800, 700));
		this.setContentPane(getJPanel());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowListener(){

			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				UtilORB.unregisterStats();
			}

			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		}
		);
		this.setName("stats");
		this.setTitle("Statistiques");
		
		createStats();
		this.setVisible(true);

	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if(jPanel == null){
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());
		}
		return jPanel;
	}

	
	public JFreeChart createChart(DefaultCategoryDataset data)
	{
		
		JFreeChart chart = ChartFactory.createBarChart("Résultats des élections", "Candidats", "Votes", data, PlotOrientation.VERTICAL, true, true, false);

		chart.setBackgroundPaint(Color.white);

		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);


		// disable bar outlines...
		BarRenderer renderer = (BarRenderer) plot.getRenderer();

		renderer.setDrawBarOutline(false);
		renderer.setItemMargin(0.10);


		renderer.setItemLabelsVisible(true);
		ItemLabelPosition p = new ItemLabelPosition(
				ItemLabelAnchor.INSIDE12, TextAnchor.CENTER_RIGHT, 
				TextAnchor.CENTER_RIGHT, -Math.PI / 2.0
		);
		renderer.setPositiveItemLabelPosition(p);

		ItemLabelPosition p2 = new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.CENTER_LEFT, 
				TextAnchor.CENTER_LEFT, -Math.PI / 2.0
		);
		renderer.setPositiveItemLabelPositionFallback(p2);
		CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		
		return chart;
	}

	public void createStats(){
		//if(cPanel == null)
		
		getJPanel().removeAll();
		
		cPanel = new ChartPanel(createChart(DataSet.getInstance().getDataSet()));
		
		getJPanel().add(cPanel);
		
		getJPanel().updateUI();
		
		cPanel.setPreferredSize(new Dimension(600,600));
		
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
