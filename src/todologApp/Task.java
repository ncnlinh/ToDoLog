package todologApp;

public class Task {

	// MESSAGE
	private static String INVALID_MESSAGE = "Invalid Input!";

	// MISC
	private static String EMPTY_STRING = "";
	private static String SINGLE_SPACE = " ";
	private static String DATE_SEPARATOR = "/";
	private static String SYMBOL_DASH = "-";
	private static String SYMBOL_AT = "@";
	private static String QUOTATION_MARK = "\"";

	// KEYWORDS
	private static String KEYWORD_DAY_STARTING = "from";
	private static String KEYWORD_DAY_ENDING = "to";
	private static String KEYWORD_DEADLINE = "by";
	private static String KEYWORD_RECURRING = "every";

	// DAY KEYWORDS
	private static String DAY_KEYWORD_TODAY = "Today";
	private static String DAY_KEYWORD_TOMORROW = "Tomorrow";
	private static String DAY_KEYWORD_TMR = "tmr";
	private static String DAY_KEYWORD_MONDAY = "Monday";
	private static String DAY_KEYWORD_MON = "mon";
	private static String DAY_KEYWORD_TUESDAY = "Tuesday";
	private static String DAY_KEYWORD_TUES = "tues";
	private static String DAY_KEYWORD_TUE = "tue";
	private static String DAY_KEYWORD_WEDNESDAY = "Wednesday";
	private static String DAY_KEYWORD_WED = "wed";
	private static String DAY_KEYWORD_THURSDAY = "Thursday";
	private static String DAY_KEYWORD_THURS = "thurs";
	private static String DAY_KEYWORD_THUR = "thur";
	private static String DAY_KEYWORD_THU = "thu";
	private static String DAY_KEYWORD_FRIDAY = "Friday";
	private static String DAY_KEYWORD_FRI = "fri";
	private static String DAY_KEYWORD_SATURDAY = "Saturday";
	private static String DAY_KEYWORD_SAT = "sat";
	private static String DAY_KEYWORD_SUNDAY = "Sunday";
	private static String DAY_KEYWORD_SUN = "sun";

	// Key Variables
	private String _taskName;
	private TaskType _taskType;
	private String _taskStartDay;
	private String _taskEndDay;
	private int _taskStartTime;
	private int _taskEndTime;

	private boolean _taskStatus;

	public Task(String parameter) throws Exception {
		_taskName = parseTaskName(parameter);
		_taskType = parseTaskType(parameter);
		_taskStartDay = parseTaskStartDay(parameter);
		_taskEndDay = parseTaskEndDay(parameter);
		_taskStartTime = parseTaskStartTime(parameter);
		_taskEndTime = parseTaskEndTime(parameter);
	}

	public Task(TaskType floating, String name, boolean status) {
		_taskType = floating;
		_taskName = name;
		_taskStatus = status;
	}

	public Task(TaskType deadline, String name, String endDay, int endTime,
			boolean status) {
		_taskType = deadline;
		_taskName = name;
		_taskEndDay = endDay;
		_taskEndTime = endTime;
		_taskStatus = status;
	}

	public Task(TaskType timed, String name, String startDay, String endDay,
			int startTime, int endTime, boolean status) {
		_taskType = timed;
		_taskName = name;
		_taskStartDay = startDay;
		_taskEndDay = endDay;
		_taskStartTime = startTime;
		_taskEndTime = endTime;
		_taskStatus = status;
	}

	private String[] generateArray(String parameter) {
		parameter = parameter.trim();
		String[] array = parameter.split(SINGLE_SPACE);
		return array;
	}

	private int parseTaskEndTime(String parameter) throws Exception {
		String[] messageArray = generateArray(parameter);

		for (int i = 0; i + 3 <= messageArray.length; i++) {
			if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_ENDING)
					&& messageArray[i + 2].equalsIgnoreCase(SYMBOL_AT)) {
				try {
					int endTime = Integer.parseInt(messageArray[i + 3]);
					if (endTime >= 0000 && endTime <= 2359) {
						return endTime;
					} else {
						return 2359;
					}
				} catch (NumberFormatException nfe) {
					throw new Exception("Invalid time format");
				}
			}
		}
		return 2359;
	}

	private int parseTaskStartTime(String parameter) throws Exception {
		String[] messageArray = generateArray(parameter);

		for (int i = 0; i + 3 <= messageArray.length; i++) {
			if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING)
					&& messageArray[i + 2].equalsIgnoreCase(SYMBOL_AT)) {
				try {
					int startTime = Integer.parseInt(messageArray[i + 3]);
					if (startTime >= 0000 && startTime <= 2359) {
						return startTime;
					} else {
						return 0000;
					}
				} catch (NumberFormatException nfe) {
					throw new Exception("Invalid time format");
				}
			}
		}
		return 0000;
	}

	private String parseTaskEndDay(String parameter) {
		String[] messageArray = generateArray(parameter);

		for (int i = 0; i + 1 <= messageArray.length; i++) {
			if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_ENDING)) {
				String endDay = parseDay(messageArray[i + 1]);
				return endDay;
			} else {
				if (messageArray[i].equalsIgnoreCase(KEYWORD_DEADLINE)) {
					String endDay = parseDay(messageArray[i + 1]);
					return endDay;
				}
			}
		}
		return getStartDay();
	}

	private String parseTaskStartDay(String parameter) {
		String[] messageArray = generateArray(parameter);

		for (int i = 0; i + 1 <= messageArray.length - 1; i++) {
			if (messageArray[i].equalsIgnoreCase(KEYWORD_DAY_STARTING)) {
				String startDay = parseDay(messageArray[i + 1]);
				return startDay;
			} else {
				if (messageArray[i].equalsIgnoreCase(KEYWORD_DEADLINE)) {
					return DAY_KEYWORD_TODAY;
				}
			}
		}
		return DAY_KEYWORD_TODAY;
	}

	private String parseDay(String parameter) {
		String day = parameter;

		if (day.equalsIgnoreCase(DAY_KEYWORD_TODAY)) {
			return DAY_KEYWORD_TODAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_TOMORROW)
				|| day.equalsIgnoreCase(DAY_KEYWORD_TMR)) {
			return DAY_KEYWORD_TOMORROW;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_MONDAY)
				|| day.equalsIgnoreCase(DAY_KEYWORD_MON)) {
			return DAY_KEYWORD_MONDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_TUESDAY)
				|| day.equalsIgnoreCase(DAY_KEYWORD_TUE)
				|| day.equalsIgnoreCase(DAY_KEYWORD_TUES)) {
			return DAY_KEYWORD_TUESDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_WEDNESDAY)
				|| day.equalsIgnoreCase(DAY_KEYWORD_WED)) {
			return DAY_KEYWORD_WEDNESDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_THURSDAY)
				|| day.equalsIgnoreCase(DAY_KEYWORD_THURS)
				|| day.equalsIgnoreCase(DAY_KEYWORD_THUR)
				|| day.equalsIgnoreCase(DAY_KEYWORD_THU)) {
			return DAY_KEYWORD_THURSDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_FRIDAY)
				|| day.equalsIgnoreCase(DAY_KEYWORD_FRI)) {
			return DAY_KEYWORD_FRIDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_SATURDAY)
				|| day.equalsIgnoreCase(DAY_KEYWORD_SAT)) {
			return DAY_KEYWORD_SATURDAY;
		} else if (day.equalsIgnoreCase(DAY_KEYWORD_SUNDAY)
				|| day.equalsIgnoreCase(DAY_KEYWORD_SUN)) {
			return DAY_KEYWORD_SUNDAY;
		} else {
			return DAY_KEYWORD_TODAY;
		}
	}

	private TaskType parseTaskType(String parameter) {
		String taskDateTime = parameter.substring(parameter
				.lastIndexOf(QUOTATION_MARK) + 1);
		taskDateTime = taskDateTime.trim();
		// System.out.println(parameter);
		// System.out.println(taskDateTime);
		String[] analyseTask = taskDateTime.split(SINGLE_SPACE);
		if (taskDateTime.length() == 0) {
			return (TaskType.FLOATING);
		} else if (analyseTask[0].equalsIgnoreCase(KEYWORD_DAY_STARTING)
				|| analyseTask[0].equalsIgnoreCase(KEYWORD_DAY_ENDING)) {
			return (TaskType.TIMED);
		} else if (analyseTask[0].equalsIgnoreCase(KEYWORD_DEADLINE)) {
			return (TaskType.DEADLINE);
		} else if (analyseTask[0].equalsIgnoreCase(KEYWORD_RECURRING)) {
			return (TaskType.RECURRING);
		} else {
			return (TaskType.INVALID);
		}

	}

	private static String parseTaskName(String parameter) throws Exception {
		int firstIndex = parameter.indexOf(QUOTATION_MARK);
		int lastIndex = parameter.lastIndexOf(QUOTATION_MARK);

		if (lastIndex > firstIndex) {
			String taskName = parameter.substring(firstIndex + 1, lastIndex);
			return taskName;
		} else if (lastIndex == firstIndex) {
			throw new Exception(
					"Invalid command. Missing task name.\nTask name must be inside quotation marks.");
		} else if (lastIndex < firstIndex) {
			throw new Error(); // never occurs
		} else {
			return null;
		}
	}

	public void setTaskName(String name) {
		_taskName = name;
	}

	public String getTaskName() {
		return _taskName;
	}

	public TaskType getTaskType() {
		return _taskType;
	}

	public String getStartDay() {
		return _taskStartDay;
	}

	public String getEndDay() {
		return _taskEndDay;
	}

	public int getStartTime() {
		return _taskStartTime;
	}

	public int getEndTime() {
		return _taskEndTime;
	}

	public static void showToUser(String message) {
		System.out.println(message);
	}

	public boolean getTaskStatus() {
		return _taskStatus;
	}

	public void toggleTaskStatus() {
		if (_taskStatus) {
			_taskStatus = false;
		} else {
			_taskStatus = true;
		}

	}
}
