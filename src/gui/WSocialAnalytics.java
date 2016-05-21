package gui;

import java.awt.EventQueue;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFrame;

import algorithm.CelfAlgorithm;
import algorithm.MaximizationAlgorithm;
import algorithm.spread.MontecarloCalculator;
import algorithm.spread.SpreadCalculator;
import parser.CelfFileParser;
import parser.FileParser;
import parser.SimpleFileParser;
import propagation.PropagationModel;
import propagation.icm.IndependentCascadeModel;
import struct.SocialNetwork;
import struct.Vertex;
import javax.swing.JMenuBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import org.apache.log4j.Logger;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class WSocialAnalytics {	

	static Logger log = Logger.getLogger(WSocialAnalytics.class.getName());

	private JFrame frame;
	
	private File file;
	private FileParser fileParser;
	private Vector<FileParser> fileParsers;
	private SocialNetwork sn;

	private JLabel lblFile;
	private JComboBox comboFileParser;
	private JButton btnParse;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					log.error("HOLA");
					
					SocialNetwork sn = new SocialNetwork();
					File file = new File("dataset/celf/hep_WC.inf");
					FileParser fileParser = new SimpleFileParser();
					
					fileParser.parseFile(file, sn);
					
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
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 59, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		lblFile = new JLabel("Ningun archivo.");
		GridBagConstraints gbc_lblFile = new GridBagConstraints();
		gbc_lblFile.insets = new Insets(0, 0, 5, 5);
		gbc_lblFile.gridx = 0;
		gbc_lblFile.gridy = 0;
		frame.getContentPane().add(lblFile, gbc_lblFile);
		
		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openFile();
			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 0);
		gbc_button.gridx = 1;
		gbc_button.gridy = 0;
		frame.getContentPane().add(button, gbc_button);
		
		comboFileParser = new JComboBox();
		comboFileParser.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent item) {
				if (item.getStateChange() == ItemEvent.SELECTED){
					System.out.println("Seleccionado: " + comboFileParser.getSelectedIndex() + " - " + (String) item.getItem());
					fileParser = fileParsers.elementAt(comboFileParser.getSelectedIndex());
				}
			}
		});
		comboFileParser.setModel(new DefaultComboBoxModel<String>(new String[] {"Simple", "CELF"}));
		comboFileParser.setSelectedIndex(-1);
		comboFileParser.setSelectedIndex(0);
		GridBagConstraints gbc_comboFileParser = new GridBagConstraints();
		gbc_comboFileParser.insets = new Insets(0, 0, 5, 5);
		gbc_comboFileParser.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboFileParser.gridx = 0;
		gbc_comboFileParser.gridy = 1;
		frame.getContentPane().add(comboFileParser, gbc_comboFileParser);
		
		btnParse = new JButton("Parsear");
		btnParse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parseSocialNetwork();
			}
		});
		GridBagConstraints gbc_btnParse = new GridBagConstraints();
		gbc_btnParse.insets = new Insets(0, 0, 5, 0);
		gbc_btnParse.gridx = 1;
		gbc_btnParse.gridy = 1;
		frame.getContentPane().add(btnParse, gbc_btnParse);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Archivo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		frame.getContentPane().add(panel, gbc_panel);
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
		// Crear RedSocial (dirigida o no dirigida)
		sn = new SocialNetwork();
		fileParser.parseFile(file, sn);
	}

}
