package ru.ncedu.java.tasks;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class StringFilterImpl implements StringFilter {

    HashSet<String> collection = new HashSet<>();

    public void add(String s) {
        if (s != null) {
            collection.add(s.toLowerCase());
        } else {
            collection.add(null);
        }
    }

    public boolean remove(String s) {
        return collection.remove(s);
    }

    public void removeAll() {
        collection.clear();
    }

    public Collection<String> getCollection() {
        return collection;
    }

    public Iterator<String> getStringsContaining(final String chars) {
        return filt(new Filter() {
            @Override
            public boolean isAdequate(String s) {
                return s != null && (chars == null || chars.equals("") || s.contains(chars));
            }
        });
    }

    public Iterator<String> getStringsStartingWith(final String begin) {
        return filt(new Filter() {
            @Override
        public boolean isAdequate(String s) {
                return s != null && (begin == null || begin.equals("") || s.startsWith(begin.toLowerCase()));
            }
        });
    }

    public Iterator<String> getStringsByNumberFormat(final String format) {
        return filt(new Filter() {
            @Override
            public boolean isAdequate(String s) {
                if (format == null || format.equals("")) {
                    return true;
                } else if (s == null || s.length() != format.length()) {
                    return false;
                } else {
                    for (int i = 0; i < s.length(); ++i) {
                        if (s.charAt(i) != format.charAt(i)) {
                            if (format.charAt(i) != '#') {
                                return false;
                            } else {
                                switch (s.charAt(i)) {
                                    case ('0'):
                                    case ('1'):
                                    case ('2'):
                                    case ('3'):
                                    case ('4'):
                                    case ('5'):
                                    case ('6'):
                                    case ('7'):
                                    case ('8'):
                                    case ('9'):
                                        break;
                                    default:
                                        return false;
                                }
                            }
                        }
                    }
                    return true;
                }
            }
        });
    }

    public Iterator<String> getStringsByPattern(final String pattern) {
        return filt(new Filter() {
            @Override
            public boolean isAdequate(String s) {
                if (pattern == null || pattern.equals("")) {
                    return true;
                } else if (s == null) {
                    return false;
                } else {
                    int astPos1 = pattern.indexOf('*');
                    int astPos2 = pattern.lastIndexOf('*');
                    if (astPos1 == astPos2) {
                        if (astPos1 == -1) {
                            return s.equals(pattern);
                        } else {
                            return s.startsWith(pattern.substring(0,astPos1)) &&
                                    s.endsWith(pattern.substring(astPos1 + 1));
                        }
                    } else {
                        return s.startsWith(pattern.substring(0,astPos1)) &&
                                s.contains(pattern.substring(astPos1 + 1, astPos2)) &&
                                s.endsWith(pattern.substring(astPos2 + 1, pattern.length()));
                    }
                }
            }
        });
    }

    private Iterator<String> filt (Filter filter) {
        HashSet<String> subCollection = new HashSet<>();
        for (String item : collection) {
            if (filter.isAdequate(item)) {
                subCollection.add(item);
            }
        }
        return subCollection.iterator();
    }

    private interface Filter {
        boolean isAdequate (String s);
    }

}
