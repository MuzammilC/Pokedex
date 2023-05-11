package ui;

import exceptions.LogException;
import model.EventLog;

/**
 * Defines behaviours that event log printers must support.
 */
public interface LogPrinter {

    // Effects: prints the log
    void printLog(EventLog el) throws LogException;
}
