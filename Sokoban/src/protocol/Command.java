package protocol;

import java.io.Serializable;

/**
* <h1>Command</h1>
* This class defines a command object.
* This commands will be executed by the client side controller.
* @author  Ron Yaish
* @version 1.0
* @since   13-07-2017
*/
public class Command implements Comparable<Command>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8683873687023289059L;

	/**
	* This method gets the command.
	* @return String.
	*/
	public String getCmd() {
		return cmd;
	}

	/**
	* This method gets the argument.
	* @return String.
	*/
	public String getArg() {
		return arg;
	}

	/**
	* This method gets the priority.
	* @return int.
	*/
	public int getPriority() {
		return priority;
	}

	/**
	* This method sets the priority.
	* @param priority
	*/
	public void setPriority(int priority) {
		this.priority = priority;
	}

	public static final int HIGHEST = 1;
	public static final int HIGH = 3;
	public static final int NORMAL = 5;
	public static final int LOW = 7;
	public static final int LOWEST = 9;

	private int priority = NORMAL;
	private String cmd;
	private String arg;

	@Override
	public int compareTo(Command o) {
		return (priority - o.priority);
	}

	/**
	* Constructor
	* @param cmd
	* @param arg
	* @param priority
	*/
	public Command(String cmd, String arg, int priority) {
		super();
		this.cmd = cmd;
		this.arg = arg;
		this.priority = priority;
	}

	/**
	* This method gets the command hashCode.
	* @return int.
	*/
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((arg == null) ? 0 : arg.hashCode());
		result = prime * result + ((cmd == null) ? 0 : cmd.hashCode());
		return result;
	}

	/**
	* This method overrides the equals function for commands.
	* @param obj
	* @return Boolean
	*/
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Command other = (Command) obj;
		if (arg == null) {
			if (other.arg != null)
				return false;
		} else if (!arg.equals(other.arg))
			return false;
		if (cmd == null) {
			if (other.cmd != null)
				return false;
		} else if (!cmd.equals(other.cmd))
			return false;
		return true;
	}

	/**
	* This method gets the command string
	* @return String.
	*/
	@Override
	public String toString() {
		return "Command [cmd=" + cmd + ", arg=" + arg + ", priority=" + priority + "]";
	}

}
