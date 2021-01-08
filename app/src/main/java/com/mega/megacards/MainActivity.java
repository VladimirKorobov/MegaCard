package com.mega.megacards;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Map<Integer, Object[]> intendMap = new HashMap<Integer, Object[]>();
    private int ADD_SCREENSHOT_CODE = 11;
    private Boolean onLongClickPressed = false;

    private static final int ALL_PERMISSIONS_RESULT = 1011;
    ArrayList<String> permissions;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();

    class thumbHolder {
        String fileName;
        Bitmap thumb;
    }

    GridView gridView;
    ArrayList<thumbHolder> thumbList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridview);
        thumbList = new ArrayList<>();
        permissions = new ArrayList<>();
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        permissionsToRequest = new ArrayList<String>(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(
                        permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                        ALL_PERMISSIONS_RESULT);
            }
        }
        //OpenFileDirectory("/storage/emulated/0/Pictures/Screenshots/");
        OpenAppDirectory();
        gridView.setAdapter(new imageAdapter(getApplicationContext(), thumbList));
        //item click listner
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                int color = getItemColor(position);
                if(color == getSelection()) {
                    setItemColor(position, getBackground());
                }
                else {
                    Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                    thumbHolder holder = (thumbHolder) gridView.getAdapter().getItem(position);

                    intent.putExtra("fileName", holder.fileName);
                    startActivity(intent);
                }
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView parent, View view,
                                           int position, long id) {
                setItemColor(position, getSelection());
                return true;
            }
        });
    }

    private int getItemColor(int position) {
        View view = gridView.getChildAt(position);
        ImageView imageView;
        imageView = (ImageView) view.findViewById(R.id.image);
        return ((ColorDrawable)imageView.getBackground()).getColor();
    }

    private void setItemColor(int position, int color) {
        View view = gridView.getChildAt(position);
        ImageView imageView;
        imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setBackgroundColor(color);
    }
    private Boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel(
                                    "\"These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()]),
                                                        ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }

    public int getBackground() {
        final int color = ContextCompat.getColor(this, R.color.colorBackground);
        return color;
    }
    public int getSelection() {
        final int color = ContextCompat.getColor(this, R.color.colorSelection);
        return color;
    }


    private void OpenAppDirectory() {
        File mydir = this.getDir("imagedir", this.MODE_PRIVATE); //Creating an internal dir;
        OpenFileDirectory(mydir);
    }

    private void OpenFileDirectory(File dir) {

        if (dir.isDirectory()) {
            thumbList.clear();
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            File[] files = dir.listFiles((FilenameFilter) null);

            float dip = 100f;
            Resources r = getResources();
            float maxWidth = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dip,
                    r.getDisplayMetrics()
            );
            for (File file : files) {
                if (file.isFile()) try {
                    InputStream stream = new FileInputStream(file);
                    BitmapFactory.decodeStream(stream, null, bitmapOptions);

                    int maxSize = Math.max(bitmapOptions.outWidth, bitmapOptions.outHeight);
                    int sampleSize = (int) ((double) maxSize / maxWidth + 0.5);
                    // size of thumbnail == 32 here
                    /*
                    while(maxSize > 32) {
                        maxSize /= 2;
                        sampleSize *= 2;
                    }
                    */

                    bitmapOptions.inJustDecodeBounds = false;
                    bitmapOptions.inSampleSize = sampleSize;

                    thumbHolder holder = new thumbHolder();
                    holder.fileName = file.getPath();
                    holder.thumb = BitmapFactory.decodeFile(holder.fileName, bitmapOptions);
                    thumbList.add(holder);

                    bitmapOptions.inSampleSize = 0;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_addscreenshot:
                AddScreenshot();
                break;
            case R.id.id_deletescreenshot:
                DeleteScreenshots();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

    private void DeleteScreenshots() {
        //SparseBooleanArray checked = gridView.getCheckedItemPositions();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete all selected screenshots");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int count = gridView.getAdapter().getCount();
                if(count > 0) {
                    for (int i = 0; i < count; i++) {
                        int color = getItemColor(i);
                        if (color == getSelection()) {
                            thumbHolder holder = (thumbHolder) gridView.getAdapter().getItem(i);
                            try {
                                File f = new File(holder.fileName);
                                f.delete();
                                //deleteFile(holder.fileName);
                            }
                            catch(Exception ex) {
                                String s = ex.getMessage();
                                s = "";

                            }

                        }
                    }
                    OpenAppDirectory();
                    gridView.setAdapter(new imageAdapter(getApplicationContext(), thumbList));
                }
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // I do not need any action here you might
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void AddScreenshot() {
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(chooseFile, ADD_SCREENSHOT_CODE);    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ADD_SCREENSHOT_CODE) {
                if (resultData != null) {
                    BufferedReader reader = null;
                    try {
                        Uri uri = resultData.getData();
                        String src = getFileName(uri);
                        InputStream in = getContentResolver().openInputStream(uri);
                        File mydir = this.getDir("imagedir", this.MODE_PRIVATE); //Creating an internal dir;
                        OutputStream out = new BufferedOutputStream(new FileOutputStream(mydir.getPath() + "/" + src));
                        CopyFile(in, out);
                        OpenAppDirectory();
                        gridView.setAdapter(new imageAdapter(getApplicationContext(), thumbList));
                    }
                    catch(Exception ex) {

                    }
                }
            }
        }
    }

    private void CopyFile(InputStream in, OutputStream out) {
        try {
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }
}
