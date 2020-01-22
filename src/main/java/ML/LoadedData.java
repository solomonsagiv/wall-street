package ML;

public class LoadedData {

	private double index = 0;
	private double future = 0;
	private double option = 0;

	public LoadedData () {
	}

	public double getIndex () {
		return index;
	}

	public void setIndex ( double index ) {
		this.index = index;
	}

	public double getFuture () {
		return future;
	}

	public void setFuture ( double future ) {
		this.future = future;
	}

	public double getOption () {
		return option;
	}

	public void setOption ( double option ) {
		this.option = option;
	}
}
