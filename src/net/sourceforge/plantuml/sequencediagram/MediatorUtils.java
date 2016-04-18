package net.sourceforge.plantuml.sequencediagram;

import net.sourceforge.plantuml.CharSequence2;
import net.sourceforge.plantuml.command.BlocLines;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility method for mediator parsing
 */
public class MediatorUtils {

    /**
     * Extract mediator name from format process_message("<mediator_name>", <parameters>)
     * @param strings
     * @return mediator name
     */
    public static BlocLines extractMediatorNameProcessMessage(BlocLines strings) {
        BlocLines newBlocLines = new BlocLines();
        for (CharSequence chs : strings.getLines()) {
            int index = chs.toString().indexOf(',');
            if (index != -1) {
                newBlocLines = BlocLines.single(chs.subSequence(0,index));
            } else {
                newBlocLines = BlocLines.single(chs);
            }
        }
        return newBlocLines;
    }

    /**
     * Extract mediator name from format <pipelinename>::<mediator_name>(<parameters>)
     * @param strings
     * @return mediator name
     */
    public static BlocLines extractMediatorNameFromPipelineDef(BlocLines strings) {
        BlocLines newBlocLines = new BlocLines();
        for (CharSequence chs : strings.getLines()) {
            int index = chs.toString().indexOf(',');
            if (index != -1) {
                newBlocLines = BlocLines.single(chs.subSequence(0,index));
            } else {
                newBlocLines = BlocLines.single(chs);
            }
        }
        return newBlocLines;
    }

    /**
     * Extract mediator name from format <mediator_name>(<parameters>)
     * @param strings
     * @return
     */
    public static BlocLines extractMediatorName(BlocLines strings) {
        BlocLines newBlocLines = new BlocLines();
        for (CharSequence chs : strings.getLines()) {
            int index = chs.toString().indexOf(',');
            if (index != -1) {
                newBlocLines = BlocLines.single(chs.subSequence(0,index));
            } else {
                newBlocLines = BlocLines.single(chs);
            }
        }
        return newBlocLines;
    }
}
