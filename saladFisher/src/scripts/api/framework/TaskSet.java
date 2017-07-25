package scripts.api.framework;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/TreeSet.html">java.util.TreeSet</a>
 *
 * @author Encoded
 */
public class TaskSet extends TreeSet<Task> {

    private Condition07 stopCondition;

    /**
     * Default constructor that initializes the comparator and sets the stop condition to none.
     */
    public TaskSet() {
        super((o1, o2) -> Integer.compare(o2.priority(), o1.priority()) < 0 ? -1 : 1);
        stopCondition = () -> false;
    }

    /**
     * Constructs a task set and adds all the tasks in the array to the set.
     * Tasks are sorted by priority in descending order.
     * @param tasks The tasks to be added.
     */
    public TaskSet(Task... tasks) {
        super((o1, o2) -> Integer.compare(o2.priority(), o1.priority()) < 0 ? -1 : 1);
        addAll(tasks);
        stopCondition = () -> false;
    }

    /**
     * Constructs a task set and adds all the tasks in the array to the set.
     * Tasks are sorted by priority in descending order.
     * @param tasks The tasks to be added.
     */
    public TaskSet(Condition07 stopCondition, Task... tasks) {
        super((o1, o2) -> Integer.compare(o2.priority(), o1.priority()) < 0 ? -1 : 1);
        addAll(tasks);
        this.stopCondition = stopCondition;
    }

    /**
     * Adds all the tasks in the array to the set.
     * @param tasks The tasks to be added.
     * @return True if the set changed, false otherwise.
     */
    public boolean addAll(Task... tasks) {
        return super.addAll(Arrays.asList(tasks));
    }

    public void setStopCondition(Condition07 stopCondition) {
        this.stopCondition = stopCondition;
    }

    /**
     * Iterates through all the tasks in the set and returns
     * the highest priority valid task.
     * @return The first valid task from the task list or null if no valid task.
     */
    public Task getValidTask() {
        for (Task task : this)
            if (task.validate())
                return task;
        return null;
    }

    /**
     * Determines if the stop condition has been met.
     * @return true if stop condition is true, false otherwise
     */
    public boolean isStopConditionMet() {
        return stopCondition.accept();
    }

}