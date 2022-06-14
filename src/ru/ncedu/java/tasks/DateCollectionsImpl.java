package ru.ncedu.java.tasks;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

public class DateCollectionsImpl implements DateCollections {

    int dateStyle;

    Map<String,Element> mainMap;

    public DateCollectionsImpl() {
        dateStyle = DateFormat.MEDIUM;
    }

    public void setDateStyle(int dateStyle) {
        this.dateStyle = dateStyle;
    }

    public Calendar toCalendar(String dateString) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateFormat.getDateInstance(dateStyle).parse(dateString));
        return calendar;
    }

    public String toString(Calendar date) {
        return DateFormat.getDateInstance(dateStyle).format(date.getTime());
    }

    public void initMainMap(int elementsNumber, Calendar firstDate) {
        mainMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int result;
                try {
                    result = DateFormat.getDateInstance(dateStyle).parse(o1).
                            compareTo(DateFormat.getDateInstance(dateStyle).parse(o2));
                } catch (ParseException e) {
                    result = o1.compareTo(o2);
                }
                return result;
            }
        });
        Calendar date = (Calendar) firstDate.clone();
        for (int i = 0; i < elementsNumber; ++i) {
            mainMap.put(toString(date), new Element((Calendar) date.clone(), new Random().nextInt(2000)));
            date.add(Calendar.DAY_OF_MONTH, 110);
        }
    }

    public void setMainMap(Map<String, Element> map) {
        mainMap = map;
    }

    public Map<String, Element> getMainMap() {
        return mainMap;
    }

    public SortedMap<String, Element> getSortedSubMap() {
        Calendar fromKey = Calendar.getInstance();
        fromKey.add(Calendar.DAY_OF_MONTH, 1);
        if (mainMap instanceof SortedMap) {
            return ((SortedMap<String, Element>) mainMap).tailMap(toString(fromKey));
        } else
        {
            SortedMap<String, Element> map = new TreeMap<>(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    int result;
                    try {
                        result = DateFormat.getDateInstance(dateStyle).parse(o1).
                                compareTo(DateFormat.getDateInstance(dateStyle).parse(o2));
                    } catch (ParseException e) {
                        result = o1.compareTo(o2);
                    }
                    return result;
                }
            });
            map.putAll(mainMap);
            return map.tailMap(toString(fromKey));
        }
    }


    public List<Element> getMainList() {

        List<Element> list = new LinkedList<>();
        list.addAll(mainMap.values());
        return list;
    }

    public void sortList(List<Element> list) {
        Collections.sort(list, new Comparator<Element>() {
            @Override
            public int compare(Element o1, Element o2) {
                int result;
                try {
                    result = DateFormat.getDateInstance(dateStyle).parse(o1.getDeathDate().toString()).
                            compareTo(DateFormat.getDateInstance(dateStyle).parse(o2.getDeathDate().toString()));
                } catch (ParseException e) {
                    result = o1.getDeathDate().toString().compareTo(o2.getDeathDate().toString());
                }
                return result;
            }
        });
    }

    public void removeFromList(List<Element> list) {
        Element element;
        for (Iterator<Element> iterator = list.iterator(); iterator.hasNext(); ) {
            element = iterator.next();
            if (element.getBirthDate().get(Calendar.MONTH) == Calendar.DECEMBER ||
                    element.getBirthDate().get(Calendar.MONTH) == Calendar.JANUARY ||
                    element.getBirthDate().get(Calendar.MONTH) == Calendar.FEBRUARY) {
                iterator.remove();
            }
        }
    }
}
