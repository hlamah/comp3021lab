package base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.io.Serializable;

public class Folder implements Comparable<Folder>, Serializable{

    private ArrayList<Note> notes;
    private String name;

    public Folder(String name) {
        notes = new ArrayList<Note>();
        this.name = name;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Folder other = (Folder) obj;
        return Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        int nText = 0;
        int nImage = 0;

        for(Note n : notes) {
            if (n instanceof TextNote) {
                nText++;
            }
            else if (n instanceof ImageNote) {
                nImage++;
            }
        }
        return name + ":" + nText + ":" + nImage;
    }

    @Override
    public int compareTo(Folder o) {
        // smaller name -> smaller
        return this.name.compareTo(o.name);
    }

    public void sortNotes() { Collections.sort(notes); }

    public ArrayList<Note> searchNotes(String keywords) {
        ArrayList<Note> result = new ArrayList<Note>();

        String[] separatedKeywordArray = keywords.toLowerCase().split(" ");

        for (Note n : notes) {
            boolean isContained = true;
            for (int i = 0; i < separatedKeywordArray.length; ++i) {
                if (separatedKeywordArray[i+1].equals("or")) {
                    if((i+2) < separatedKeywordArray.length) { // whether the kw to the right of OR exist
                        if (n instanceof ImageNote) {
                            if (n.getTitle().toLowerCase().contains(separatedKeywordArray[i]) || n.getTitle().toLowerCase().contains(separatedKeywordArray[i+2])) {
                                isContained = true;
                            }
                            else {
                                isContained = false;
                                break;
                            }
                            i += 2; // skip OR and right kw
                        }
                        else if (n instanceof TextNote) {
                            if (n.getTitle().toLowerCase().contains(separatedKeywordArray[i]) || n.getTitle().toLowerCase().contains(separatedKeywordArray[i+2]) || ((TextNote) n).content.toLowerCase().contains(separatedKeywordArray[i]) || ((TextNote) n).content.toLowerCase().contains(separatedKeywordArray[i+2])) {
                                isContained = true;
                            }
                            else {
                                isContained = false;
                                break;
                            }
                            i += 2;
                        }
                    }
                }

                else { // the kw after is not OR
                    if (n instanceof ImageNote) {
                        if (n.getTitle().toLowerCase().contains(separatedKeywordArray[i])) {
                            isContained = true;
                        }
                        else {
                            isContained = false;
                            break;
                        }
                    }
                    else if (n instanceof TextNote) {
                        if (n.getTitle().toLowerCase().contains(separatedKeywordArray[i]) || ((TextNote) n).content.toLowerCase().contains(separatedKeywordArray[i])) {
                            isContained = true;
                        }
                        else {
                            isContained = false;
                            break;
                        }
                    }
                }
            }

            if (isContained)
                result.add(n);
        }
        return result;
    }

}
