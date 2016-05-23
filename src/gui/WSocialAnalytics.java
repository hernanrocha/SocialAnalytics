package gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.apache.log4j.Logger;

import algorithm.CelfAlgorithm;
import algorithm.CelfPlusPlusAlgorithm;
import algorithm.GreedyAlgorithm;
import algorithm.MaximizationAlgorithm;
import algorithm.RandomAlgorithm;
import algorithm.spread.MontecarloCalculator;
import algorithm.spread.SpreadCalculator;
import parser.CelfFileParser;
import parser.FbCircleLineParser;
import parser.FileParser;
import parser.LineParser;
import parser.SimpleFileParser;
import parser.SimpleLineParser;
import propagation.IndependentCascadeModel;
import propagation.LinearThresholdModel;
import propagation.PropagationModel;
import struct.SocialNetwork;
import struct.Vertex;

public class WSocialAnalytics {	

	static Logger log = Logger.getLogger(WSocialAnalytics.class.getName());

	private JFrame frmSocialAnalytics;
	
	private File file;
	private Vector<FileParser> fileParsers;
	private Vector<LineParser> lineParsers;
	private SocialNetwork sn;

	private JLabel lblFile;
	private JComboBox comboFileParser;
	private JComboBox comboLineParser;
	private JButton btnParse;
	private JPanel panelFile;
	private JLabel lblFileParser;
	private JLabel lblLineParser;
	private JPanel panelPropagation;
	private JLabel lblPropagationModel;
	private JComboBox comboPropagationModel;
	private JLabel lblSpreadCalculator;
	private JComboBox comboSpreadCalculator;
	private JButton btnNewButton;
	
	// Propagacion
	private Vector<PropagationModel> propagationModels;
	private Vector<SpreadCalculator> spreadCalculators;
	private Vector<MaximizationAlgorithm> maximizationAlgorithms;
	private JLabel lblMaximizationAlgorithm;
	private JComboBox comboMaximizationAlgorithm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					SocialNetwork sn = new SocialNetwork();
					File file = new File("dataset/celf/hep_WC.inf");
					FileParser fileParser = new SimpleFileParser();
					LineParser lineParser = new SimpleLineParser();
					
					fileParser.parseFile(file, lineParser, sn);
					
					// Armar un SeedSet inicial
					Set<Vertex> seedSet = new HashSet<Vertex>();					
					seedSet.add(sn.getVertex(172));
					seedSet.add(sn.getVertex(223));
					seedSet.add(sn.getVertex(213));
					seedSet.add(sn.getVertex(230));
					
					// Realizar propagacion
					//PropagationModel propModel = new LinearThresholdModel();
					PropagationModel propModel = new IndependentCascadeModel();
					//propModel.propagate(sn, seedSet);
					
					SpreadCalculator spread = new MontecarloCalculator();
					//System.out.println("SPREAD: " + spread.calculateSpread(sn, seedSet, propModel));

					MaximizationAlgorithm algorithm;
					//algorithm = new RandomAlgorithm();
					algorithm = new CelfAlgorithm();
					//algorithm.maximize(sn, spread, propModel, 5);
					
					WSocialAnalytics window = new WSocialAnalytics();
					window.frmSocialAnalytics.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WSocialAnalytics() {	
		fileParsers = new Vector<FileParser>();
		fileParsers.add(new SimpleFileParser());
		fileParsers.add(new CelfFileParser());
		
		lineParsers = new Vector<LineParser>();
		lineParsers.add(new SimpleLineParser());
		lineParsers.add(new FbCircleLineParser());		

		propagationModels = new Vector<PropagationModel>();
		propagationModels.add(new LinearThresholdModel());
		propagationModels.add(new IndependentCascadeModel());
		
		spreadCalculators = new Vector<SpreadCalculator>();
		spreadCalculators.add(new MontecarloCalculator());
		
		maximizationAlgorithms = new Vector<MaximizationAlgorithm>();
		maximizationAlgorithms.add(new RandomAlgorithm());
		maximizationAlgorithms.add(new GreedyAlgorithm());
		maximizationAlgorithms.add(new CelfAlgorithm());
		maximizationAlgorithms.add(new CelfPlusPlusAlgorithm());
		
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		initialize();
		

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSocialAnalytics = new JFrame();
		frmSocialAnalytics.setTitle("Social Analytics");
		frmSocialAnalytics.setBounds(100, 100, 450, 425);
		frmSocialAnalytics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmSocialAnalytics.setJMenuBar(menuBar);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
		frmSocialAnalytics.getContentPane().setLayout(gridBagLayout);
		
		panelFile = new JPanel();
		panelFile.setBorder(new TitledBorder(null, "Archivo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelFile = new GridBagConstraints();
		gbc_panelFile.insets = new Insets(0, 0, 5, 0);
		gbc_panelFile.fill = GridBagConstraints.BOTH;
		gbc_panelFile.gridx = 0;
		gbc_panelFile.gridy = 0;
		frmSocialAnalytics.getContentPane().add(panelFile, gbc_panelFile);
		GridBagLayout gbl_panelFile = new GridBagLayout();
		gbl_panelFile.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panelFile.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panelFile.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelFile.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelFile.setLayout(gbl_panelFile);
		
		lblFile = new JLabel("Ningun archivo seleccionado");
		GridBagConstraints gbc_lblFile = new GridBagConstraints();
		gbc_lblFile.anchor = GridBagConstraints.EAST;
		gbc_lblFile.gridwidth = 2;
		gbc_lblFile.insets = new Insets(0, 0, 5, 5);
		gbc_lblFile.gridx = 0;
		gbc_lblFile.gridy = 0;
		panelFile.add(lblFile, gbc_lblFile);
		
		JButton btnSelectFile = new JButton("...");
		GridBagConstraints gbc_btnSelectFile = new GridBagConstraints();
		gbc_btnSelectFile.insets = new Insets(0, 0, 5, 0);
		gbc_btnSelectFile.gridx = 2;
		gbc_btnSelectFile.gridy = 0;
		panelFile.add(btnSelectFile, gbc_btnSelectFile);
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFile();
			}
		});
		
		lblFileParser = new JLabel("File Parser");
		GridBagConstraints gbc_lblFileParser = new GridBagConstraints();
		gbc_lblFileParser.insets = new Insets(0, 0, 5, 5);
		gbc_lblFileParser.anchor = GridBagConstraints.EAST;
		gbc_lblFileParser.gridx = 0;
		gbc_lblFileParser.gridy = 1;
		panelFile.add(lblFileParser, gbc_lblFileParser);
		
		comboFileParser = new JComboBox();
		comboFileParser.setModel(new DefaultComboBoxModel<String>(new String[] {"Simple", "CELF"}));
		GridBagConstraints gbc_comboFileParser = new GridBagConstraints();
		gbc_comboFileParser.gridwidth = 2;
		gbc_comboFileParser.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFileParser.insets = new Insets(0, 0, 5, 0);
		gbc_comboFileParser.gridx = 1;
		gbc_comboFileParser.gridy = 1;
		panelFile.add(comboFileParser, gbc_comboFileParser);
		comboFileParser.setSelectedIndex(0);
		
		lblLineParser = new JLabel("Line Parser");
		GridBagConstraints gbc_lblLineParser = new GridBagConstraints();
		gbc_lblLineParser.insets = new Insets(0, 0, 5, 5);
		gbc_lblLineParser.anchor = GridBagConstraints.EAST;
		gbc_lblLineParser.gridx = 0;
		gbc_lblLineParser.gridy = 2;
		panelFile.add(lblLineParser, gbc_lblLineParser);
		
		comboLineParser = new JComboBox();
		comboLineParser.setModel(new DefaultComboBoxModel<String>(new String[] {"Simple", "Facebook Circles"}));
		GridBagConstraints gbc_comboLineParser = new GridBagConstraints();
		gbc_comboLineParser.gridwidth = 2;
		gbc_comboLineParser.insets = new Insets(0, 0, 5, 0);
		gbc_comboLineParser.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboLineParser.gridx = 1;
		gbc_comboLineParser.gridy = 2;
		panelFile.add(comboLineParser, gbc_comboLineParser);
		comboLineParser.setSelectedIndex(0);
		
		btnParse = new JButton("Parsear");
		GridBagConstraints gbc_btnParse = new GridBagConstraints();
		gbc_btnParse.gridwidth = 3;
		gbc_btnParse.gridx = 0;
		gbc_btnParse.gridy = 3;
		panelFile.add(btnParse, gbc_btnParse);
		
		panelPropagation = new JPanel();
		panelPropagation.setBorder(new TitledBorder(null, "Algoritmo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelPropagation = new GridBagConstraints();
		gbc_panelPropagation.insets = new Insets(0, 0, 5, 0);
		gbc_panelPropagation.fill = GridBagConstraints.BOTH;
		gbc_panelPropagation.gridx = 0;
		gbc_panelPropagation.gridy = 1;
		frmSocialAnalytics.getContentPane().add(panelPropagation, gbc_panelPropagation);
		GridBagLayout gbl_panelPropagation = new GridBagLayout();
		gbl_panelPropagation.columnWidths = new int[]{0, 0, 0};
		gbl_panelPropagation.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panelPropagation.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelPropagation.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelPropagation.setLayout(gbl_panelPropagation);
		
		lblPropagationModel = new JLabel("Propagation Model");
		GridBagConstraints gbc_lblPropagationModel = new GridBagConstraints();
		gbc_lblPropagationModel.insets = new Insets(0, 0, 5, 5);
		gbc_lblPropagationModel.anchor = GridBagConstraints.EAST;
		gbc_lblPropagationModel.gridx = 0;
		gbc_lblPropagationModel.gridy = 0;
		panelPropagation.add(lblPropagationModel, gbc_lblPropagationModel);
		
		comboPropagationModel = new JComboBox();
		comboPropagationModel.setModel(new DefaultComboBoxModel<String>(new String[] {"Linear Threshold", "Independent Cascade"}));
		GridBagConstraints gbc_comboPropagationModel = new GridBagConstraints();
		gbc_comboPropagationModel.insets = new Insets(0, 0, 5, 0);
		gbc_comboPropagationModel.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboPropagationModel.gridx = 1;
		gbc_comboPropagationModel.gridy = 0;
		panelPropagation.add(comboPropagationModel, gbc_comboPropagationModel);
		
		lblSpreadCalculator = new JLabel("Spread Calculator");
		GridBagConstraints gbc_lblSpreadCalculator = new GridBagConstraints();
		gbc_lblSpreadCalculator.anchor = GridBagConstraints.EAST;
		gbc_lblSpreadCalculator.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpreadCalculator.gridx = 0;
		gbc_lblSpreadCalculator.gridy = 1;
		panelPropagation.add(lblSpreadCalculator, gbc_lblSpreadCalculator);
		
		comboSpreadCalculator = new JComboBox();
		comboSpreadCalculator.setModel(new DefaultComboBoxModel<String>(new String[] {"Montecarlo"}));
		GridBagConstraints gbc_comboSpreadCalculator = new GridBagConstraints();
		gbc_comboSpreadCalculator.insets = new Insets(0, 0, 5, 0);
		gbc_comboSpreadCalculator.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboSpreadCalculator.gridx = 1;
		gbc_comboSpreadCalculator.gridy = 1;
		panelPropagation.add(comboSpreadCalculator, gbc_comboSpreadCalculator);
		
		lblMaximizationAlgorithm = new JLabel("Maximization Algorithm");
		GridBagConstraints gbc_lblMaximizationAlgorithm = new GridBagConstraints();
		gbc_lblMaximizationAlgorithm.anchor = GridBagConstraints.EAST;
		gbc_lblMaximizationAlgorithm.insets = new Insets(0, 0, 5, 5);
		gbc_lblMaximizationAlgorithm.gridx = 0;
		gbc_lblMaximizationAlgorithm.gridy = 2;
		panelPropagation.add(lblMaximizationAlgorithm, gbc_lblMaximizationAlgorithm);
		
		comboMaximizationAlgorithm = new JComboBox();
		comboMaximizationAlgorithm.setModel(new DefaultComboBoxModel<String>(new String[] {"Random", "Greedy", "CELF", "CELF ++"}));
		GridBagConstraints gbc_comboMaximizationAlgorithm = new GridBagConstraints();
		gbc_comboMaximizationAlgorithm.insets = new Insets(0, 0, 5, 0);
		gbc_comboMaximizationAlgorithm.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboMaximizationAlgorithm.gridx = 1;
		gbc_comboMaximizationAlgorithm.gridy = 2;
		panelPropagation.add(comboMaximizationAlgorithm, gbc_comboMaximizationAlgorithm);
		
		btnNewButton = new JButton("New button");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 3;
		panelPropagation.add(btnNewButton, gbc_btnNewButton);
		btnParse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parseSocialNetwork();
			}
		});
	}

	protected void openFile() {
		JFileChooser fc = new JFileChooser(".");

        // Mostrar la ventana para abrir archivo y recoger la respuesta
        int response = fc.showOpenDialog(null);

        // Comprobar si se ha pulsado Aceptar
        if (response == JFileChooser.APPROVE_OPTION){
        	String path = fc.getSelectedFile().getAbsolutePath();
        	
        	openFile(path);
        }		
	}

	private void openFile(String path) {
        file = new File(path);
        lblFile.setText(file.getAbsolutePath());
        System.out.println("Archivo: " + file.getName());		
	}
	
	protected void parseSocialNetwork() {
		// Crear RedSocial
		sn = new SocialNetwork();
		FileParser fp = fileParsers.elementAt(comboFileParser.getSelectedIndex());
		LineParser lp = lineParsers.elementAt(comboLineParser.getSelectedIndex());
		
		if (file == null) {
			openFile();
		}
		
		log.info("File: " + file.getName());
		log.info("FileParser: " + fp.getClass().getName());
		log.info("LineParser: " + lp.getClass().getName());
		
		fp.parseFile(file, lp, sn);
	}

}
