package plugins.dataviewer.gui.datalist;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import plugins.dataviewer.gui.Constants;
import plugins.dataviewer.gui.DataUtils;
import plugins.dataviewer.gui.DataviewerActivator;
import jprobe.services.CoreEvent;
import jprobe.services.CoreListener;
import jprobe.services.ErrorHandler;
import jprobe.services.JProbeCore;
import jprobe.services.data.Data;

public class DataListModel extends DefaultTableModel implements CoreListener{
	private static final long serialVersionUID = 1L;
	
	private JProbeCore m_Core;
	private Map<Data, String> m_Data = new HashMap<Data, String>();
	private Component m_Parent;
	
	public DataListModel(JProbeCore core, Component parent){
		super(new String[][]{}, Constants.DATALIST_COL_HEADERS);
		m_Core = core;
		m_Core.addCoreListener(this);
		m_Parent = parent;
		for(Data d : m_Core.getDataManager().getAllData()){
			this.add(d);
		}
	}
	
	public DataListModel(JProbeCore core){
		this(core, null);
	}
	
	public Data getData(int row){
		String name = (String) this.getValueAt(row, 0);
		return m_Core.getDataManager().getData(name);
	}
	
	public void cleanup(){
		m_Core.removeCoreListener(this);
	}
	
	@Override
	public boolean isCellEditable(int row, int col){
		return col == 0;
	}
	
	@Override
	public void setValueAt(Object aValue, int row, int col){
		try{
			String newName = (String) aValue;
			String oldName = (String) this.getValueAt(row, col);
			if(newName.equals(oldName)){
				return;
			}
			if(m_Core.getDataManager().contains(oldName)){
				//This means that the name change needs to be push to the core
				Data change = m_Core.getDataManager().getData(oldName);
				DataUtils.rename(change, newName, m_Core, m_Parent);
			}else{
				//This means that the name change is received from the core and the field should be updated accordingly
				Data changed = m_Core.getDataManager().getData(newName);
				m_Data.put(changed, newName);
				super.setValueAt(newName, row, col);
			}
		} catch (Exception e){
			ErrorHandler.getInstance().handleException(e, DataviewerActivator.getBundle());
		}
	}

	private void add(Data data){
		String name = m_Core.getDataManager().getDataName(data);
		m_Data.put(data, name);
		this.addRow(new String[]{name, data.getClass().getSimpleName()});
	}
	
	private void remove(Data data){
		String name = m_Data.get(data);
		for(int i=0; i<this.getRowCount(); i++){
			if(this.getValueAt(i, 0).equals(name)){
				this.removeRow(i);
				m_Data.remove(data);
				break;
			}
		}
	}
	
	private void rename(Data data, String oldName, String newName){
		if(oldName.equals(newName)){
			return;
		}
		for(int i=0; i<this.getRowCount(); i++){
			if(this.getValueAt(i, 0).equals(oldName)){
				this.setValueAt(newName, i, 0);
				break;
			}
		}
	}
	
	@Override
	public void update(CoreEvent event) {
		switch(event.type()){
		case DATA_ADDED:
			this.add(event.getData());
			break;
		case DATA_REMOVED:
			this.remove(event.getData());
			break;
		case DATA_NAME_CHANGE:
			this.rename(event.getData(), event.getOldName(), event.getNewName());
			break;
		default:
			//do nothing
		}
	}
	
}
