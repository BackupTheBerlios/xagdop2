package ui.uiStats;



import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

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
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

public class uiStats extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel jPanel = null;

	/**
	 * This method initializes 
	 * 
	 */
	public uiStats() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(800, 700));
		this.setContentPane(getJPanel());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setName("stats");
		this.setTitle("Statistiques");
		this.setVisible(true);

	}

	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new GridBagLayout());

			ChartPanel cPanel = new ChartPanel(createChart());
			cPanel.setPreferredSize(new Dimension(600,600));
			jPanel.add(cPanel);
		}
		return jPanel;
	}

	public DefaultCategoryDataset createStatsDataSet()
	{
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int nbVotes = 10;

		dataset.addValue(13.0, "BAYROU", "François Bayrou");
		dataset.addValue(26.0, "ROYALE", "Ségolène Royale");   		   
		dataset.addValue(10.0, "LE PEN", "Jean Marie Le Pen");   
		dataset.addValue(5.0, "BESANCENOT", "Olivier Besancenot");   
		dataset.addValue(29.0, "SARKOZY", "Nicolas Sarkozy");
		dataset.addValue(2.0, "LAGUILLER", "Arlette Laguiller");   

		return dataset;
	}

	public JFreeChart createChart()
	{
		JFreeChart chart = ChartFactory.createBarChart("Résultats des élections", "Candidats", "Votes", createStatsDataSet(), PlotOrientation.VERTICAL, true, true, false);

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



}  //  @jve:decl-index=0:visual-constraint="10,10"
