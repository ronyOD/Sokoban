package interfaces;

import protocol.Command;

public interface Commandable {
	void command(Command command);
}