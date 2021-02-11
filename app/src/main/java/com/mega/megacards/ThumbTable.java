package com.mega.megacards;

import java.util.ArrayList;

public class ThumbTable {
    private class ThumbTableItem {
        public ThumbTableItem(String tab) {
            this.tab = tab;
        }
        String tab;
        ArrayList<MainActivity.thumbHolder> thumbHolderList = new ArrayList<MainActivity.thumbHolder>();
    }
    private ArrayList<ThumbTableItem> thumbItemList = new ArrayList<ThumbTableItem>();

    public ArrayList<MainActivity.thumbHolder> get(String tab) {
        for(ThumbTableItem item: thumbItemList) {
            if(item.tab.equals(tab)) {
                return item.thumbHolderList;
            }
        }
        return null;
    }

    public int size() {
        return thumbItemList.size();
    }

    public boolean hasTab(String tab) {
        for(ThumbTableItem item: thumbItemList) {
            if(item.tab.equals(tab)) {
                return true;
            }
        }
        return false;
    }

    public String[] tabs() {
        String[] tabs = new String[thumbItemList.size()];
        for(int i = 0; i < thumbItemList.size(); i ++) {
            tabs[i] = thumbItemList.get(i).tab;
        }
        return tabs;
    }

    public void add(MainActivity.thumbHolder thumbItem) {
        for(int i = 0; i < thumbItemList.size(); i ++) {
            if(thumbItemList.get(i).tab.equals(thumbItem.tab)) {
                thumbItemList.get(i).thumbHolderList.add(thumbItem);
                return;
            }
        }
        thumbItemList.add(new ThumbTableItem(thumbItem.tab));
        thumbItemList.get(thumbItemList.size() - 1).thumbHolderList.add(thumbItem);
    }

    public void remove(MainActivity.thumbHolder thumbItem) {
        for(int i = 0; i < thumbItemList.size(); i ++) {
            if(thumbItemList.get(i).tab.equals(thumbItem.tab)) {
                thumbItemList.get(i).thumbHolderList.remove(thumbItem);
                break;
            }
        }
    }

    public boolean deleteTab(String tab) {
        for(int i = 0; i < thumbItemList.size(); i ++) {
            if(thumbItemList.get(i).tab.equals(tab)) {
                if(thumbItemList.size() != 0)
                {
                    return false;
                }
                else
                {
                    thumbItemList.remove(i);
                    break;
                }
            }
        }
        return true;
    }

    public void addTab(String tab){
        for (int i = 0; i < thumbItemList.size(); i++) {
            if (thumbItemList.get(i).tab.equals(tab)) {
                // tab already exists
                return;
            }
        }
        thumbItemList.add(new ThumbTableItem(tab));
    }

    public void move(String tab, int newPos) {
        int curTabPos = 0;
        if(newPos < 0)
            newPos = 0;
        else if(newPos > thumbItemList.size())
            newPos = thumbItemList.size();

        for (; curTabPos < thumbItemList.size(); curTabPos++) {
            ThumbTableItem item = thumbItemList.get(curTabPos);
            if (item.tab.equals(tab)) {
                if(curTabPos == newPos)
                    break;
                thumbItemList.remove(item);
                thumbItemList.add(newPos, item);
            }
        }
    }

    public String getTab(int index) {
        if(index >= 0 && index < size()) {
            return thumbItemList.get(index).tab;
        }
        return "";
    }
}
