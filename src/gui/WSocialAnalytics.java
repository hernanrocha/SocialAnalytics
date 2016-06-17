package gui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
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
import parser.SubsetLineParser;
import propagation.IndependentCascadeModel;
import propagation.LinearThresholdModel;
import propagation.PropagationModel;
import struct.SocialNetwork;
import struct.Vertex;
import thread.MaximizerWorker;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class WSocialAnalytics implements PropertyChangeListener {	

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
	private JButton btnPropagate;
	
	// Propagacion
	private Vector<PropagationModel> propagationModels;
	private Vector<SpreadCalculator> spreadCalculators;
	private Vector<MaximizationAlgorithm> maximizationAlgorithms;
	private JLabel lblAlgorithm;
	private JComboBox comboMaximizationAlgorithm;
	private JLabel lblState;
	private JLabel lblSnstate;
	private JTextField lblSeedSet;
	private JSpinner spinnerSeedSize;
	private JPanel panel;
	private JPanel panelMaximizacion;
	private JButton btnMaximize;
	private JProgressBar progressGreedy;
	private JSpinner spinnerRuns;
	private JLabel lblRuns;
	private JLabel labelSeedSize;
	private JLabel lblImage;
	private JScrollPane scrollPane;
	private JLabel lblTiempoDeCalculo;

	private long beginAlgorithm;
	private JLabel labelPropagacion;
	private JMenu mnArchivo;
	private JMenuItem mntmAutomatizar;
	private JSeparator separator;
	private JMenuItem mntmSalir;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
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
		lineParsers.add(new SubsetLineParser());

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

	private void automate() {
		// Load dataset
		openFile("dataset/celf/hep_LT2.inf");
		parseSocialNetwork();
		
		// Maximize
		maximize();		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSocialAnalytics = new JFrame();
		frmSocialAnalytics.setTitle("Social Analytics");
		frmSocialAnalytics.setBounds(100, 100, 776, 550);
		frmSocialAnalytics.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSocialAnalytics.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JMenuBar menuBar = new JMenuBar();
		frmSocialAnalytics.setJMenuBar(menuBar);
		
		mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		mntmAutomatizar = new JMenuItem("Automatizar");
		mntmAutomatizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				automate();
			}
		});
		mntmAutomatizar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mnArchivo.add(mntmAutomatizar);
		
		separator = new JSeparator();
		mnArchivo.add(separator);
		
		mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmSocialAnalytics.dispose();
			}
		});
		mntmSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		mnArchivo.add(mntmSalir);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frmSocialAnalytics.getContentPane().setLayout(gridBagLayout);
		
		panelFile = new JPanel();
		panelFile.setBorder(new TitledBorder(null, "Archivo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelFile = new GridBagConstraints();
		gbc_panelFile.anchor = GridBagConstraints.NORTH;
		gbc_panelFile.insets = new Insets(0, 0, 5, 5);
		gbc_panelFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelFile.gridx = 0;
		gbc_panelFile.gridy = 0;
		frmSocialAnalytics.getContentPane().add(panelFile, gbc_panelFile);
		GridBagLayout gbl_panelFile = new GridBagLayout();
		gbl_panelFile.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panelFile.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panelFile.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelFile.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelFile.setLayout(gbl_panelFile);
		
		lblFile = new JLabel("Ningun archivo seleccionado");
		lblFile.setForeground(Color.DARK_GRAY);
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
		comboLineParser.setModel(new DefaultComboBoxModel(new String[] {"Simple", "Facebook Circles", "Subset (2000)"}));
		GridBagConstraints gbc_comboLineParser = new GridBagConstraints();
		gbc_comboLineParser.gridwidth = 2;
		gbc_comboLineParser.insets = new Insets(0, 0, 5, 0);
		gbc_comboLineParser.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboLineParser.gridx = 1;
		gbc_comboLineParser.gridy = 2;
		panelFile.add(comboLineParser, gbc_comboLineParser);
		comboLineParser.setSelectedIndex(2);
		
		btnParse = new JButton("Parsear");
		GridBagConstraints gbc_btnParse = new GridBagConstraints();
		gbc_btnParse.insets = new Insets(0, 0, 5, 0);
		gbc_btnParse.gridwidth = 3;
		gbc_btnParse.gridx = 0;
		gbc_btnParse.gridy = 3;
		panelFile.add(btnParse, gbc_btnParse);
		
		lblState = new JLabel("<html>Ningun archivo parseado</html>");
		lblState.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_lblState = new GridBagConstraints();
		gbc_lblState.insets = new Insets(0, 0, 5, 0);
		gbc_lblState.anchor = GridBagConstraints.WEST;
		gbc_lblState.gridwidth = 3;
		gbc_lblState.gridx = 0;
		gbc_lblState.gridy = 4;
		panelFile.add(lblState, gbc_lblState);
		
		lblSnstate = new JLabel("<html> * Vertices: 0<br> * Aristas: 0</html>");
		lblSnstate.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblSnstate = new GridBagConstraints();
		gbc_lblSnstate.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSnstate.gridwidth = 3;
		gbc_lblSnstate.insets = new Insets(0, 0, 0, 5);
		gbc_lblSnstate.gridx = 0;
		gbc_lblSnstate.gridy = 5;
		panelFile.add(lblSnstate, gbc_lblSnstate);
		
		panelPropagation = new JPanel();
		panelPropagation.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Propagacion", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panelPropagation = new GridBagConstraints();
		gbc_panelPropagation.anchor = GridBagConstraints.NORTH;
		gbc_panelPropagation.insets = new Insets(0, 0, 5, 5);
		gbc_panelPropagation.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelPropagation.gridx = 0;
		gbc_panelPropagation.gridy = 1;
		frmSocialAnalytics.getContentPane().add(panelPropagation, gbc_panelPropagation);
		GridBagLayout gbl_panelPropagation = new GridBagLayout();
		gbl_panelPropagation.columnWidths = new int[]{0, 0, 0};
		gbl_panelPropagation.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panelPropagation.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_panelPropagation.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
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
		
		lblRuns = new JLabel("Runs");
		GridBagConstraints gbc_lblRuns = new GridBagConstraints();
		gbc_lblRuns.anchor = GridBagConstraints.EAST;
		gbc_lblRuns.insets = new Insets(0, 0, 0, 5);
		gbc_lblRuns.gridx = 0;
		gbc_lblRuns.gridy = 2;
		panelPropagation.add(lblRuns, gbc_lblRuns);
		
		spinnerRuns = new JSpinner();
		spinnerRuns.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		GridBagConstraints gbc_spinnerRuns = new GridBagConstraints();
		gbc_spinnerRuns.anchor = GridBagConstraints.WEST;
		gbc_spinnerRuns.gridx = 1;
		gbc_spinnerRuns.gridy = 2;
		panelPropagation.add(spinnerRuns, gbc_spinnerRuns);
		
		panelMaximizacion = new JPanel();
		panelMaximizacion.setBorder(new TitledBorder(null, "Maximizacion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panelMaximizacion = new GridBagConstraints();
		gbc_panelMaximizacion.anchor = GridBagConstraints.NORTH;
		gbc_panelMaximizacion.insets = new Insets(0, 0, 5, 5);
		gbc_panelMaximizacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_panelMaximizacion.gridx = 0;
		gbc_panelMaximizacion.gridy = 2;
		frmSocialAnalytics.getContentPane().add(panelMaximizacion, gbc_panelMaximizacion);
		GridBagLayout gbl_panelMaximizacion = new GridBagLayout();
		gbl_panelMaximizacion.columnWidths = new int[]{100, 100, 100, 0, 0};
		gbl_panelMaximizacion.rowHeights = new int[]{14, 0, 0, 0, 0};
		gbl_panelMaximizacion.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panelMaximizacion.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelMaximizacion.setLayout(gbl_panelMaximizacion);
		
		btnMaximize = new JButton("Maximizar");
		btnMaximize.setEnabled(false);
		btnMaximize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				maximize();
			}
		});
		
		labelSeedSize = new JLabel("Seeds");
		GridBagConstraints gbc_labelSeedSize = new GridBagConstraints();
		gbc_labelSeedSize.anchor = GridBagConstraints.EAST;
		gbc_labelSeedSize.insets = new Insets(0, 0, 5, 5);
		gbc_labelSeedSize.gridx = 0;
		gbc_labelSeedSize.gridy = 0;
		panelMaximizacion.add(labelSeedSize, gbc_labelSeedSize);
		
		spinnerSeedSize = new JSpinner();
		GridBagConstraints gbc_spinnerSeedSize = new GridBagConstraints();
		gbc_spinnerSeedSize.anchor = GridBagConstraints.WEST;
		gbc_spinnerSeedSize.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerSeedSize.gridx = 1;
		gbc_spinnerSeedSize.gridy = 0;
		panelMaximizacion.add(spinnerSeedSize, gbc_spinnerSeedSize);
		spinnerSeedSize.setModel(new SpinnerNumberModel(new Integer(5), new Integer(1), null, new Integer(1)));
		
		lblAlgorithm = new JLabel("Algorithm");
		GridBagConstraints gbc_lblAlgorithm = new GridBagConstraints();
		gbc_lblAlgorithm.anchor = GridBagConstraints.EAST;
		gbc_lblAlgorithm.insets = new Insets(0, 0, 5, 5);
		gbc_lblAlgorithm.gridx = 2;
		gbc_lblAlgorithm.gridy = 0;
		panelMaximizacion.add(lblAlgorithm, gbc_lblAlgorithm);
		
		comboMaximizationAlgorithm = new JComboBox();
		GridBagConstraints gbc_comboMaximizationAlgorithm = new GridBagConstraints();
		gbc_comboMaximizationAlgorithm.anchor = GridBagConstraints.WEST;
		gbc_comboMaximizationAlgorithm.insets = new Insets(0, 0, 5, 0);
		gbc_comboMaximizationAlgorithm.gridx = 3;
		gbc_comboMaximizationAlgorithm.gridy = 0;
		panelMaximizacion.add(comboMaximizationAlgorithm, gbc_comboMaximizationAlgorithm);
		comboMaximizationAlgorithm.setModel(new DefaultComboBoxModel<String>(new String[] {"Random", "Greedy", "CELF", "CELF ++"}));
		comboMaximizationAlgorithm.setSelectedIndex(1);
		
		progressGreedy = new JProgressBar();
		progressGreedy.setStringPainted(true);
		GridBagConstraints gbc_progressGreedy = new GridBagConstraints();
		gbc_progressGreedy.gridwidth = 4;
		gbc_progressGreedy.fill = GridBagConstraints.HORIZONTAL;
		gbc_progressGreedy.insets = new Insets(0, 0, 5, 0);
		gbc_progressGreedy.gridx = 0;
		gbc_progressGreedy.gridy = 1;
		panelMaximizacion.add(progressGreedy, gbc_progressGreedy);
		GridBagConstraints gbc_btnMaximize = new GridBagConstraints();
		gbc_btnMaximize.insets = new Insets(0, 0, 5, 0);
		gbc_btnMaximize.gridwidth = 4;
		gbc_btnMaximize.gridx = 0;
		gbc_btnMaximize.gridy = 2;
		panelMaximizacion.add(btnMaximize, gbc_btnMaximize);
		
		lblTiempoDeCalculo = new JLabel("");
		lblTiempoDeCalculo.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblTiempoDeCalculo = new GridBagConstraints();
		gbc_lblTiempoDeCalculo.gridwidth = 4;
		gbc_lblTiempoDeCalculo.anchor = GridBagConstraints.WEST;
		gbc_lblTiempoDeCalculo.gridx = 0;
		gbc_lblTiempoDeCalculo.gridy = 3;
		panelMaximizacion.add(lblTiempoDeCalculo, gbc_lblTiempoDeCalculo);
		
		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 3;
		frmSocialAnalytics.getContentPane().add(panel, gbc_panel);
		panel.setBorder(new TitledBorder(null, "Seed Set", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		lblSeedSet = new JTextField();
		GridBagConstraints gbc_lblSeedSet = new GridBagConstraints();
		gbc_lblSeedSet.insets = new Insets(0, 0, 5, 5);
		gbc_lblSeedSet.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSeedSet.gridx = 0;
		gbc_lblSeedSet.gridy = 0;
		panel.add(lblSeedSet, gbc_lblSeedSet);
		lblSeedSet.setColumns(10);
		
		btnPropagate = new JButton("Propagar");
		GridBagConstraints gbc_btnPropagate = new GridBagConstraints();
		gbc_btnPropagate.insets = new Insets(0, 0, 5, 0);
		gbc_btnPropagate.gridx = 1;
		gbc_btnPropagate.gridy = 0;
		panel.add(btnPropagate, gbc_btnPropagate);
		btnPropagate.setEnabled(false);
		
		labelPropagacion = new JLabel("");
		labelPropagacion.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_labelPropagacion = new GridBagConstraints();
		gbc_labelPropagacion.anchor = GridBagConstraints.WEST;
		gbc_labelPropagacion.gridwidth = 2;
		gbc_labelPropagacion.insets = new Insets(0, 0, 0, 5);
		gbc_labelPropagacion.gridx = 0;
		gbc_labelPropagacion.gridy = 1;
		panel.add(labelPropagacion, gbc_labelPropagacion);
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.getViewport().setBackground(Color.WHITE);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 5;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 0;
		frmSocialAnalytics.getContentPane().add(scrollPane, gbc_scrollPane);
		
		lblImage = new JLabel("");
		lblImage.setBackground(Color.WHITE);
		scrollPane.setViewportView(lblImage);
		lblImage.setHorizontalAlignment(SwingConstants.CENTER);
		btnPropagate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				propagate();
			}
		});
		btnParse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parseSocialNetwork();
			}
		});
	}

	protected void maximize() {
		beginAlgorithm = System.currentTimeMillis();
		
		Integer n = (Integer) spinnerSeedSize.getValue();
		PropagationModel propModel = propagationModels.elementAt(comboPropagationModel.getSelectedIndex());
		SpreadCalculator spreadCalculator = spreadCalculators.elementAt(comboSpreadCalculator.getSelectedIndex());
		MontecarloCalculator montecarlo = (MontecarloCalculator) spreadCalculator;
		montecarlo.setRuns((Integer) spinnerRuns.getValue());
		MaximizationAlgorithm algorithm = maximizationAlgorithms.elementAt(comboMaximizationAlgorithm.getSelectedIndex()).instance();

		log.info("PropagationModel: " + propModel.getClass().getName());
		log.info("SpreadCalculator: " + spreadCalculator.getClass().getName());
		log.info("Maximization Algorithm: " + algorithm.getClass().getName());
		log.info("Runs: " + spinnerRuns.getValue());
		
		algorithm.setSn(sn);
		algorithm.setSpread(spreadCalculator);
		algorithm.setModel(propModel);
		algorithm.setN(n);
		algorithm.setProgressBar(progressGreedy);
		algorithm.addPropertyChangeListener(this);		
		algorithm.execute();		
	}

	protected void propagate() {
		// Armar un SeedSet inicial
		Set<Vertex> seedSet = new HashSet<Vertex>();		
		String[] txtSeedSet = lblSeedSet.getText().split(" ");
		for (int i = 0; i < txtSeedSet.length; i++) {			
			seedSet.add(sn.getVertex(Integer.parseInt(txtSeedSet[i])));
		}
		
		// Realizar propagacion
		PropagationModel propModel = propagationModels.elementAt(comboPropagationModel.getSelectedIndex());

		log.info("SeedSet(" + seedSet.size() + "): " + seedSet);
		log.info("PropagationModel: " + propModel.getClass().getName());		

		long begin = System.currentTimeMillis();
		
		GraphViz gv = GraphViz.getInstance();
		gv.reset_graph();
		gv.addln(gv.start_graph());		
		
		Integer spread = propModel.propagate(sn, seedSet, true);

		gv.addln(gv.end_graph());
		
		GraphViz.verificarDirectorio(GraphViz.TEMP_DIR);
		String nombre = GraphViz.TEMP_DIR + "/grafico." + GraphViz.IMAGE_EXT;
	    File out = new File(nombre);
	  	gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), GraphViz.IMAGE_EXT ), out );	  	

		long end = System.currentTimeMillis();
		long tiempoProcesamiento = end - begin;
		log.warn("Spread calculado: " + spread + " (" + tiempoProcesamiento + "ms)");
		
		labelPropagacion.setText("Propagacion: " + spread);
		lblImage.setIcon(new ImageIcon(GraphViz.getImagen(out.getPath())));
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
		lblFile.setForeground(Color.BLACK);
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
		
		if (fp.parseFile(file, lp, sn)) {
			log.info("Archivo parseado correctamente");
			lblState.setText("Archivo parseado correctamente");
			lblState.setForeground(new Color(50, 205, 50));

			btnPropagate.setEnabled(true);
			btnMaximize.setEnabled(true);
		} else {
			log.info("Archivo parseado con errores");	
			lblState.setText("Archivo parseado con errores");
			lblState.setForeground(Color.RED);
			
			btnPropagate.setEnabled(false);
			btnMaximize.setEnabled(false);
		}
		
		log.info(" * Vertices: " + sn.getVerticesCount());
		log.info(" * Aristas: " + sn.getEdgeCount());
		
		lblSnstate.setText("<html> * Vertices: " + sn.getVerticesCount() + "<br> * Aristas: " + sn.getEdgeCount() + "</html>");
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().toString().equals("state") && evt.getNewValue().toString().equals("DONE")) {
			try {
				Set<Vertex> optimalSeedSet = ((MaximizationAlgorithm) evt.getSource()).get();
				
				log.info("Optimal Seed Set: " + optimalSeedSet);
				
				// Mostrar el resultado
				String str = "";
				for (Vertex v : optimalSeedSet){
					str += v.toString() + " ";
				}
				lblSeedSet.setText(str);
				
				long end = System.currentTimeMillis();
				long tiempoProcesamiento = end - beginAlgorithm;
				
				String strProces = "";
				if (tiempoProcesamiento < 1000) {
					strProces += tiempoProcesamiento + "ms";
				} else {
					strProces += (tiempoProcesamiento / 1000) + "s";
				}
				lblTiempoDeCalculo.setText("Tiempo de calculo: " + strProces);
				
				propagate();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}

}
