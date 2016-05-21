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
import algorithm.MaximizationAlgorithm;
import algorithm.spread.MontecarloCalculator;
import algorithm.spread.SpreadCalculator;
import parser.CelfFileParser;
import parser.FbCircleLineParser;
import parser.FileParser;
import parser.LineParser;
import parser.SimpleFileParser;
import parser.SimpleLineParser;
import propagation.IndependentCascadeModel;
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
	private JPanel panel;
	private JLabel lblFileParser;
	private JLabel lblLineParser;

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
		frmSocialAnalytics.setBounds(100, 100, 450, 300);
		frmSocialAnalytics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmSocialAnalytics.setJMenuBar(menuBar);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		frmSocialAnalytics.getContentPane().setLayout(gridBagLayout);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Archivo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		frmSocialAnalytics.getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblFile = new JLabel("Ningun archivo seleccionado");
		GridBagConstraints gbc_lblFile = new GridBagConstraints();
		gbc_lblFile.anchor = GridBagConstraints.EAST;
		gbc_lblFile.gridwidth = 2;
		gbc_lblFile.insets = new Insets(0, 0, 5, 5);
		gbc_lblFile.gridx = 0;
		gbc_lblFile.gridy = 0;
		panel.add(lblFile, gbc_lblFile);
		
		JButton btnSelectFile = new JButton("...");
		GridBagConstraints gbc_btnSelectFile = new GridBagConstraints();
		gbc_btnSelectFile.insets = new Insets(0, 0, 5, 0);
		gbc_btnSelectFile.gridx = 2;
		gbc_btnSelectFile.gridy = 0;
		panel.add(btnSelectFile, gbc_btnSelectFile);
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
		panel.add(lblFileParser, gbc_lblFileParser);
		
		comboFileParser = new JComboBox();
		comboFileParser.setModel(new DefaultComboBoxModel<String>(new String[] {"Simple", "CELF"}));
		GridBagConstraints gbc_comboFileParser = new GridBagConstraints();
		gbc_comboFileParser.gridwidth = 2;
		gbc_comboFileParser.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFileParser.insets = new Insets(0, 0, 5, 0);
		gbc_comboFileParser.gridx = 1;
		gbc_comboFileParser.gridy = 1;
		panel.add(comboFileParser, gbc_comboFileParser);
		comboFileParser.setSelectedIndex(0);
		
		lblLineParser = new JLabel("Line Parser");
		GridBagConstraints gbc_lblLineParser = new GridBagConstraints();
		gbc_lblLineParser.insets = new Insets(0, 0, 5, 5);
		gbc_lblLineParser.anchor = GridBagConstraints.EAST;
		gbc_lblLineParser.gridx = 0;
		gbc_lblLineParser.gridy = 2;
		panel.add(lblLineParser, gbc_lblLineParser);
		
		comboLineParser = new JComboBox();
		comboLineParser.setModel(new DefaultComboBoxModel<String>(new String[] {"Simple", "Facebook Circles"}));
		GridBagConstraints gbc_comboLineParser = new GridBagConstraints();
		gbc_comboLineParser.gridwidth = 2;
		gbc_comboLineParser.insets = new Insets(0, 0, 5, 0);
		gbc_comboLineParser.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboLineParser.gridx = 1;
		gbc_comboLineParser.gridy = 2;
		panel.add(comboLineParser, gbc_comboLineParser);
		comboLineParser.setSelectedIndex(0);
		
		btnParse = new JButton("Parsear");
		GridBagConstraints gbc_btnParse = new GridBagConstraints();
		gbc_btnParse.gridwidth = 3;
		gbc_btnParse.gridx = 0;
		gbc_btnParse.gridy = 3;
		panel.add(btnParse, gbc_btnParse);
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
