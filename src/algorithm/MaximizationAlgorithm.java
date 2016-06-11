package algorithm;

import java.util.List;
import java.util.Set;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import algorithm.spread.SpreadCalculator;
import propagation.PropagationModel;
import struct.SocialNetwork;
import struct.Vertex;

public abstract class MaximizationAlgorithm extends SwingWorker<Set<Vertex>, Integer> {
	
	static Logger log = Logger.getLogger(MaximizationAlgorithm.class.getName());

	private SocialNetwork sn;
	private SpreadCalculator spread;
	private PropagationModel model;
	private Integer n;
	
	protected int progress = 0;
	
	// GUI
	private JProgressBar progressBar;
	
	public void setSn(SocialNetwork sn) {
		this.sn = sn;
	}

	public void setSpread(SpreadCalculator spread) {
		this.spread = spread;
	}

	public void setModel(PropagationModel model) {
		this.model = model;
	}

	public void setN(Integer n) {
		this.n = n;
	}
	
	public void setProgressBar(JProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	@Override
	protected Set<Vertex> doInBackground() throws Exception {
		return maximize(sn, spread, model, n);
	}
	
	protected void process(List<Integer> chunks){
		int n = chunks.get(0);
		
		log.info("Value: " + n);
		
		progressBar.setValue(n);
		progressBar.updateUI();
	}
	
	protected void updateProgress(int newProgress) {		
		if (progress != newProgress) {
			progress = newProgress;
			publish(progress);
		}
	}
	
	// Dada una red social y un entero n, encontrar las n semillas
	// que maximicen la influencia sobre la red
	public abstract Set<Vertex> maximize(SocialNetwork sn, SpreadCalculator spread, PropagationModel model, Integer n);
	
	public Double getMarginal(SocialNetwork sn, SpreadCalculator spread, PropagationModel model, Set<Vertex> vertices, Vertex vertex){
		Double preSpread = spread.calculateSpread(sn, vertices, model);
		
		vertices.add(vertex);
		Double posSpread = spread.calculateSpread(sn, vertices, model);
		vertices.remove(vertex);
		
		return posSpread - preSpread;
	}
	
}
