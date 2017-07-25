/**
 * @author Encoded
 */

package scripts.api.framework;


public interface Task {

    int priority();

    boolean validate();

    void execute();

}