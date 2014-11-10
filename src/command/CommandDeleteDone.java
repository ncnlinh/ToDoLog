package command;

import java.io.IOException;
import java.util.LinkedList;

import common.Task;

import controller.Controller;
import storage.DBStorage;

	public class CommandDeleteDone implements Command {
		private LinkedList<Task> _storageList;
		private DBStorage _storage;
		private boolean _validity;
		private LinkedList <Task> _undoList;
		
		private static final String FEEDBACK_INVALID_STORAGE = "Cannot store the list to ToDoLog";
		private static final String FEEDBACK_VALID_DELETE_DONE = "Deleted completed tasks";
		private static final String FEEDBACK_VALID_UNDO = "Undone the delete done command";

		@Override
		public String execute() {
			String feedback;
			_storage = Controller.getDBStorage();
			_storageList = _storage.load();
			_undoList = new LinkedList <Task> (_storage.load());
			
				for ( int i=_storageList.size()-1; i >= 0; i-- ) {
					if (_storageList.get(i).getTaskStatus() == true ) {
						_storageList.remove(i);
					}
				}
			try {
				_storage.store(_storageList);
			} catch (IOException e) {
				feedback = FEEDBACK_INVALID_STORAGE;
				_validity=false;
				return feedback;
			}
			// set focus task to change UI's page
			Controller.setFocusTask(Controller.getScheduleList().getLast());
			Controller.setFocusTask(_storageList.getLast());
			feedback = FEEDBACK_VALID_DELETE_DONE;
			_validity = true;
			return feedback;
		}
		
		public String tryExecute() {
			String feedback;
			_storage = Controller.getDBStorage();
			_storageList = _storage.load();
			feedback = FEEDBACK_VALID_DELETE_DONE;
			_validity = true;
			return feedback;
		}

		@Override
		public String undo() {
			String feedback;
			try {
				_storage.store(_undoList);
			} catch (IOException e) {
				feedback = FEEDBACK_INVALID_STORAGE;
				return feedback;
			}
			feedback = FEEDBACK_VALID_UNDO;
			return feedback;
		}
		
		@Override
		public boolean isUndoable() {
			return _validity;
		}

	}