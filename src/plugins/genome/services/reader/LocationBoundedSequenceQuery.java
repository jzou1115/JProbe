package plugins.genome.services.reader;

import java.util.Comparator;

import plugins.genome.services.utils.GenomicCoordinate;
import plugins.genome.services.utils.GenomicRegion;

public abstract class LocationBoundedSequenceQuery extends SequenceQuery{
	private static final long serialVersionUID = 1L;
	
	public static final Comparator<LocationBoundedSequenceQuery> START_COMPARATOR = new Comparator<LocationBoundedSequenceQuery>(){

		@Override
		public int compare(LocationBoundedSequenceQuery o1, LocationBoundedSequenceQuery o2) {
			if(o1.getStart().equals(o2.getStart())){
				return o1.getEnd().compareTo(o2.getEnd());
			}
			return o1.getStart().compareTo(o2.getStart());
		}
		
	};
	
	public static final Comparator<LocationBoundedSequenceQuery> END_COMPARATOR = new Comparator<LocationBoundedSequenceQuery>(){

		@Override
		public int compare(LocationBoundedSequenceQuery o1, LocationBoundedSequenceQuery o2) {
			if(o1.getEnd().equals(o2.getEnd())){
				return o1.getStart().compareTo(o2.getStart());
			}
			return o1.getEnd().compareTo(o2.getEnd());
		}
		
	};
	
	private final GenomicRegion m_Region;

	protected LocationBoundedSequenceQuery(String targetSequence, GenomicRegion searchRegion){
		super(targetSequence);
		m_Region = searchRegion;
	}
	
	public GenomicRegion getRegion(){
		return m_Region;
	}
	
	public GenomicCoordinate getStart(){
		return m_Region.getStart();
	}
	
	public GenomicCoordinate getEnd(){
		return m_Region.getEnd();
	}

}
