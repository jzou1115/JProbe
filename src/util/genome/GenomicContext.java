package util.genome;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public interface GenomicContext extends Serializable{
	
	public Comparator<Chromosome> getChrAscendingComparator();
	public Comparator<Chromosome> getChrDescendingComparator();
	
	public Comparator<GenomicCoordinate> getLocationAscendingComparator();
	public Comparator<GenomicCoordinate> getLocationDescendingComparator();
	
	public Comparator<GenomicRegion> getStartAscendingComparator();
	public Comparator<GenomicRegion> getStartDescendingComparator();
	public Comparator<GenomicRegion> getEndAscendingComparator();
	public Comparator<GenomicRegion> getEndDescendingComparator();
	
	public int getNumChrs();
	public int indexOf(Chromosome chr);
	public Chromosome getChr(int index);
	public Chromosome getFirstChr();
	public Chromosome getLastChr();
	public List<Chromosome> getChrs();
	public boolean hasChr(Chromosome chr);
	public boolean hasChr(String id);
	public Chromosome getChr(String id);
	
	public Chromosome nextChr(Chromosome cur);
	public Chromosome prevChr(Chromosome cur);
	
	public GenomicCoordinate newGenomicCoordinate(Chromosome chr, long baseIndex);
	public GenomicCoordinate newGenomicCoordinate(String chrId, long baseIndex);
	public GenomicRegion newGenomicRegion(GenomicCoordinate start, GenomicCoordinate end);
	
}
