package com.mega.megacards;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class SettingsHolder {
    Context content;
    public SettingsHolder(Context content) {
        this.content = content;
    }
    void exportSettings(String exportDir) {
        File srcDir = this.content.getDir("settingsDir", content.MODE_PRIVATE); //Creating an internal dir;
        File dstDir = new File(exportDir);
        deleteDirContent(dstDir, 0);
        copyDir(srcDir, dstDir);
    }
    void importSettings(String importDir) {
        File srcDir = new File(importDir);
        File dstDir = this.content.getDir("settingsDir", content.MODE_PRIVATE); //Creating an internal dir;
        deleteDirContent(dstDir, 0);
        copyDir(srcDir, dstDir);
    }

    private void deleteDirContent(File dir, int level) {
        if(dir.exists()) {
            if(dir.isDirectory()) {
                String files[] = dir.list();
                for(String file : files) {
                    deleteDirContent(new File(dir.getPath() + "/" + file), level + 1);
                }
                if(level > 0) {
                    dir.delete();
                }
            }
            else {
                dir.delete();
            }
        }
    }

    private void copyDir(File sourceDir, File destDir) {
        if(!destDir.exists()) {
            destDir.mkdirs();
        }

        String files[] = sourceDir.list();
        for(String file : files) {
            File src = new File(sourceDir.getPath() + "/" + file);
            File dst = new File(destDir.getPath() + "/" + file);
            if(src.isDirectory()) {
                copyDir(src, dst);
            }
            else {
                copyFile(src, dst);
            }
        }
    }

    private void copyFile(File src, File dst) {
        InputStream in = null;
        try {
            in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dst);
            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeSettings(ThumbTable thumbTable) {
        File mydir = this.content.getDir("settingsDir", content.MODE_PRIVATE); //Creating an internal dir;
        try {
            File settings = new File(mydir.getPath() + "/settings.txt");
            if(settings.exists()) {
                settings.delete();
            }

            FileOutputStream fw = new FileOutputStream(settings, true);
            int item = 0;
            for ( String key : thumbTable.tabs() ) {
                ArrayList<MainActivity.thumbHolder> list = thumbTable.get(key);
                if(list.size() == 0) {
                    // Empty list - add dummy holder
                    fw.write(("Item:" + Integer.toString(item) + "\n").getBytes());
                    fw.write(("Tab:" + key + "\n").getBytes());
                    fw.write(("Image:" + "" + "\n").getBytes());
                    fw.write(("Thumb:" + "" + "\n").getBytes());
                    fw.write(("Title:" + ""  + "\n").getBytes());
                    fw.write(("Stop:" + "\n").getBytes());
                }
                else for (MainActivity.thumbHolder holder : list) {
                    fw.write(("Item:" + Integer.toString(item) + "\n").getBytes());
                    fw.write(("Tab:" + holder.tab + "\n").getBytes());
                    fw.write(("Image:" + holder.fileName + "\n").getBytes());
                    fw.write(("Thumb:" + holder.iconName + "\n").getBytes());
                    fw.write(("Title:" + holder.title + "\n").getBytes());
                    fw.write(("Stop:" + "\n").getBytes());
                }
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void readSettings(ThumbTable thumbTable) {
        File mydir = this.content.getDir("settingsDir", content.MODE_PRIVATE); //Creating an internal dir;
        File settings = new File(mydir.getPath() + "/settings.txt");
        if(settings.exists()) {
            try {
                BufferedReader fr = new BufferedReader(new FileReader(settings));
                String line;
                MainActivity.thumbHolder holder = null;
                while((line = fr.readLine()) != null) {
                    String key = line.substring(0, line.indexOf(':'));
                    String value = line.substring(line.indexOf(':') + 1);
                    switch(key) {
                        case "Item":
                            holder = new MainActivity.thumbHolder((MainActivity)content);
                            break;
                        case "Image":
                            holder.fileName = value;
                            break;
                        case "Thumb":
                            holder.iconName = value;
                            break;
                        case "Title":
                            holder.title = value;
                            break;
                        case "Tab":
                            holder.tab = value;
                            break;
                        case "Stop":
                            if(!holder.iconName.equals("") && !holder.fileName.equals("")) {
                                holder.thumb = BitmapFactory.decodeFile(holder.iconName);
                                thumbTable.add(holder);
                            }
                            // else - dummy. Don't add it
                            break;
                    }
                }
                if(thumbTable.size() == 0) {
                    thumbTable.addTab("");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                String m = ex.getMessage();
                ex.printStackTrace();
            }
        }
    }
}
