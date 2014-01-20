package jprobe.services.function;

import jprobe.services.data.Data;

public interface DataParameter extends Parameter{
	
	public Class<? extends Data> getType();
	public boolean isValid(Data data);
	
}
