package command;

import controller.Controller;

public class CommandLoad implements Command {
	
	private String _newFileName;
	private String _oldFileName;
	
	private static final String FEEDBACK_LOADED_FILE_NAME = "Loaded/created \" %1$s \"";
	
	public CommandLoad(String fileName) {
		_oldFileName = Controller.getDBStorage().getFileStorage().getFile().getName();
		_newFileName = fileName;
	}
	@Override
	public String execute() {
		Controller.init(_newFileName);
		 // set focus task to change UI's page
		Controller.setFocusTask( null );
		return String.format(FEEDBACK_LOADED_FILE_NAME, _newFileName); 
	}

	@Override
	public String undo() {
		Controller.init(_oldFileName);
		// set focus task to change UI's page
		Controller.setFocusTask( null ); 
		return String.format(FEEDBACK_LOADED_FILE_NAME, _oldFileName); 
	}

	@Override
	public boolean isUndoable() {
		assert isUndoable();
		return true;
	}

}
