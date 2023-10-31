package profplan.model.task.predicates;

import java.util.ArrayList;
import java.util.function.Predicate;

import profplan.commons.util.ToStringBuilder;
import profplan.model.task.Task;

/**
 * A predicate that is the AND of several predicates
 */
public class CombinedPredicate implements Predicate<Task> {
    private ArrayList<Predicate<Task>> predicateList;

    public CombinedPredicate(ArrayList<Predicate<Task>> predicates) {
        this.predicateList = predicates;
    }

    @Override
    public boolean test(Task task) {
        for (Predicate<Task> predicate : predicateList) {
            if (!predicate.test(task)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CombinedPredicate)) {
            return false;
        }

        CombinedPredicate otherPredicate = (CombinedPredicate) other;
        return predicateList.equals(otherPredicate.predicateList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add(
                "predicates", predicateList).toString();
    }
}
