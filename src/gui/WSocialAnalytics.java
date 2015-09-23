package gui;

import java.awt.EventQueue;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;

import parser.FileParser;
import parser.SimpleFileParser;
import propagation.LinearThresholdModel;
import propagation.PropagationModel;
import struct.SocialNetwork;
import struct.Node;

public class WSocialAnalytics {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// Crear RedSocial (dirigida o no dirigida)
					SocialNetwork sn = new SocialNetwork();
					
					// Cargar la red social desde un archivo
					//File file = new File("dataset/wikipedia/Wiki-Vote.txt");
					//File file = new File("dataset/collaboration/CA-CondMat.txt");
					//File file = new File("dataset/collaboration/CA-AstroPh.txt");
					//File file = new File("dataset/facebook/0.edges");				
					File file = new File("dataset/prueba/prueba.txt");				
					FileParser sfp = new SimpleFileParser();
					sfp.parseFile(file, sn);
					
					// Armar un SeedSet inicial
					Set<Node> seedSet = new HashSet<Node>();
					/*seedSet.add(new Node(172));
					seedSet.add(new Node(223));
					seedSet.add(new Node(213));
					seedSet.add(new Node(230));*/
					seedSet.add(new Node(1));
					
					// Realizar propagacion
					PropagationModel propModel = new LinearThresholdModel();
					propModel.propagate(sn, seedSet);
					
					//if (sn != null)
					//	return;
					
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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
