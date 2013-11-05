package readwrite;

import java.io.BufferedWriter;
import java.util.Scanner;

import org.w3c.dom.Element;

import core.Constants;
import datatypes.DataType;
import datatypes.Peak;
import datatypes.location.GenomeLocation;
import exceptions.FileReadException;
import exceptions.FormatNotSupportedException;

public class PeakReaderWriter implements DataReader, DataWriter{
	
	public static final String[] PEAK_READ_FORMATS = new String[]{"bed", "encode", "xml"}; 
	public static final String[] PEAK_WRITE_FORMATS = new String[]{"bed", "encode", "xml"};
	public static final String BED_ENCODE_LINE_FORMAT_REGEX = "^(chr).+\\s+\\d+\\s+\\d+.*$";
	
	@Override
	public String[] getValidReadFormats() {
		return PEAK_READ_FORMATS;
	}

	@Override
	public DataType read(String format, Scanner s) throws FormatNotSupportedException, FileReadException {
		if(format.equalsIgnoreCase("bed")||format.equalsIgnoreCase("encode")){
			if(s.hasNextLine()){
				String line = s.nextLine();
				if(line.matches(BED_ENCODE_LINE_FORMAT_REGEX)){
					return readPeak(line);
				}
			}
		}
		throw new FormatNotSupportedException(format+" read not supported by this DataType.");
	}
	
	private Peak readPeak(String line) throws FileReadException{
		return readPeak(line.split(Constants.WHITESPACE_REGEX));
	}

	private Peak readPeak(String[] entries) throws FileReadException{
		try{
			GenomeLocation loc = new GenomeLocation(entries[0], Integer.parseInt(entries[1]), Integer.parseInt(entries[2]));
			return new Peak(loc);
		}catch(Exception e){
			throw new FileReadException("Invalid entry format");
		}
	}

	@Override
	public DataType readXML(Element e) throws FormatNotSupportedException, FileReadException {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public String[] getValidWriteFormats() {
		return PEAK_WRITE_FORMATS;
	}

	@Override
	public void write(String format, BufferedWriter out)
			throws FormatNotSupportedException {
		// TODO Auto-generated method stub
		
	}
	
}
