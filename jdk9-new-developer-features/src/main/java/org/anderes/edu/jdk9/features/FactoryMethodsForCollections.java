package org.anderes.edu.jdk9.features;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Factory Methods for Collections (JEP 269)
 * <p/>
 * Collections provide a well understood way for you to gather
 * together groups (I was going to say sets, but that could be a
 * bit misleading) of data items in your applications and then
 * manipulate the data in a variety of useful ways.<br>
 * At the top level, there are interfaces that represent the
 * abstract concepts of a List, Set, and Map.<br>
 * The problem, until now, has been that Java doesn’t provide
 * a simple way to create a collection with predeined data.
 * If you want a collection to be structurally immutable (that is,
 * you can’t add, delete, or change references to elements), you
 * need to do more work. [Java Magazine July 2017]
 * 
 * @author René Anderes
 *
 */
public class FactoryMethodsForCollections {

    /**
     * Let’s look at a simple example using JDK 8<br>
     * 
     * It’s not terrible, admittedly, but to create an immutable
     * list of four Points required six lines of code.
     */
    public List<Point> oldStyleList() {
        
        List<Point> myList = new ArrayList<>();
        myList.add(new Point(1, 1));
        myList.add(new Point(2, 2));
        myList.add(new Point(3, 3));
        myList.add(new Point(4, 4));
        myList = Collections.unmodifiableList(myList);
        
        return myList;
    }

    /**
     * Let’s rewrite our example using JDK 9<br>
     * 
     * JDK 9 addresses this through factory methods for collections.
     * This feature makes use of a change introduced in JDK 8
     * that enabled static methods to be included in interfaces. That
     * change means that you can add the necessary methods at
     * the top-level interfaces (Set, List, and Map) rather than having
     * to add them to a large group of classes that implement
     * those interfaces.
     * 
     * @return
     */
    public List<Point> newStyleList() {
        List<Point> list = List.of(new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4));
        return list;
    }

}
