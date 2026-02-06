package calendarsystem;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Comparator for comparing events based on the number of matching topics.
 */
public class EventComparator implements Comparator<Event> {

    private ArrayList<String> topics;

    /**
     * Initializes the EventComparator with a list of topics to compare.
     * 
     * @param topics The list of topics to use for comparison.
     */
    public EventComparator(ArrayList<String> topics) {
        this.topics = topics;
    }

    /**
     * Compares two events based on the number of matching topics, the event name, 
     * and the promoter name.
     * 
     * @param event1 The first event to compare.
     * @param event2 The second event to compare.
     * @return A negative integer, zero, or a positive integer as the first event is 
     *         less than, equal to, or greater than the second event.
     */
    @Override
    public int compare(Event event1, Event event2) {
        int event1TopicCount = event1.countMatchingTopics(topics);
        int event2TopicCount = event2.countMatchingTopics(topics);
        
        if (event1TopicCount == event2TopicCount) {
            int nameComparison = event1.getName().compareTo(event2.getName());
            if (nameComparison == 0) {
                return event1.getPromoterName().compareTo(event2.getPromoterName());
            } else {
                return nameComparison;
            }
        } else {
            return Integer.compare(event2TopicCount, event1TopicCount);
        }
    }
}
