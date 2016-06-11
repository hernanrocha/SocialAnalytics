package thread;

import java.util.List;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

public class MaximizerWorker extends SwingWorker<String, Integer> {

	static Logger log = Logger.getLogger(MaximizerWorker.class.getName());
	
	JProgressBar progressBar;
	
	public MaximizerWorker(JProgressBar progressBar) {
		super();
		this.progressBar = progressBar;
	}

	@Override
	protected String doInBackground() throws Exception {
		for(int i = 0; i <= 100; i++){
			log.info("Progress: " + i);
			publish(i);
			Thread.sleep(100);
		}
		
		return "Finished";
	}
	
	protected void process(List<Integer> chunks){
		int n = chunks.get(0);
		
		log.info("Value: " + n);
		
		progressBar.setValue(n);
		progressBar.updateUI();
	}
}
