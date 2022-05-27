/*
 * *
 *  * Created by RAJAT PAYAL on 25 MAY 2020
 *  * Copyright (c) 2022.year . All rights reserved.
 *  * Last modified 25 MAY 2020
 *
 *
 */

package com.example.simple_notes.acti_pack;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.text.Html;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.simple_notes.Database.NoteDatabase;
import com.example.simple_notes.R;
import com.example.simple_notes.adapters.NoteAdapter;
import com.example.simple_notes.entity.Note;
import com.example.simple_notes.listeners.NotesListeners;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements NotesListeners {


    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_UPDATE_NOTE = 2;
    public static final int REQUEST_CODE_SHOW_NOTES = 3;
    public static final int REQUEST_CODE_SELECT_IMAGE = 4;
    public static final int REQUEST_CODE_STORAGE_PERMISSION = 5;

    private RecyclerView notesRecyclerView;
    private List<Note> noteList;
    private NoteAdapter notesAdapter;

    private int noteClickedPosition = -1;

    private AlertDialog dialogAddURL;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageAddNoteMain = findViewById(R.id.imageAddNoteMain);
        imageAddNoteMain.setOnClickListener(v -> startActivityForResult(
                new Intent(getApplicationContext(), note_activity.class), REQUEST_CODE_ADD_NOTE)
        );

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        noteList = new ArrayList<>();
        notesAdapter = new NoteAdapter(noteList, this);
        notesRecyclerView.setAdapter(notesAdapter);

        //location
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(notesRecyclerView);
        getNotes(REQUEST_CODE_SHOW_NOTES, false);

        EditText inputSearch = findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notesAdapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (noteList.size() != 0) {
                    notesAdapter.searchNotes(s.toString());
                }
            }
        });

        findViewById(R.id.imageAddNote).setOnClickListener(v -> startActivityForResult(
                new Intent(getApplicationContext(), note_activity.class), REQUEST_CODE_ADD_NOTE));

        findViewById(R.id.imageAddImage).setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
            } else {
                selectImage();
            }
        });

        findViewById(R.id.imageAddWebLink).setOnClickListener(v -> showAddURLDialog());
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImage();
            } else {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            }

//            if(requestCode==100 && grantResults.length>0&&(grantResults[0]+grantResults[1]==PackageManager.PERMISSION_GRANTED)) {
//                getCurrentLocation(view);
//            }else{
//                Toast.makeText(getApplicationContext(),"Premission denaied",Toast.LENGTH_SHORT);
//
//            }
        }
    }

    private String getPathFromUri(Uri contentUri) {
        String filePath;
        Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            filePath = contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex("_data");
            filePath = cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }

    @Override
    public void onNoteClicked(Note note, int position) {
        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(), note_activity.class);
        intent.putExtra("isViewOrUpdate", true);
        intent.putExtra("note", note);
        startActivityForResult(intent, REQUEST_CODE_UPDATE_NOTE);
    }

    private void getNotes(final int requestCode, final boolean isNoteDeleted) {

        @SuppressLint("StaticFieldLeak")
        class GetNoteTask extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NoteDatabase.getNoteDatabase(getApplicationContext())
                        .notedao().getAllNOtes();
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                if (requestCode == REQUEST_CODE_SHOW_NOTES) {
                    noteList.addAll(notes);
                    notesAdapter.notifyDataSetChanged();
                } else if (requestCode == REQUEST_CODE_ADD_NOTE) {
                    noteList.add(0, notes.get(0));
                    notesAdapter.notifyItemInserted(0);
                    notesRecyclerView.smoothScrollToPosition(0);
                } else if (requestCode == REQUEST_CODE_UPDATE_NOTE) {
                    noteList.remove(noteClickedPosition);
                    if (isNoteDeleted) {
                        notesAdapter.notifyItemRemoved(noteClickedPosition);
                    } else {
                        noteList.add(noteClickedPosition, notes.get(noteClickedPosition));
                        notesAdapter.notifyItemChanged(noteClickedPosition);
                    }
                }
            }
        }

        new GetNoteTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            getNotes(REQUEST_CODE_ADD_NOTE, false);
        } else if (requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode == RESULT_OK) {
            if (data != null) {
                getNotes(REQUEST_CODE_UPDATE_NOTE, data.getBooleanExtra("isNoteDeleted", false));
            }
        } else if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    try {
                        String selectedImagePath = getPathFromUri(selectedImageUri);
                        Intent intent = new Intent(getApplicationContext(), note_activity.class);
                        intent.putExtra("isFromQuickActions", true);
                        intent.putExtra("quickActionType", "image");
                        intent.putExtra("imagePath", selectedImagePath);
                        startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void showAddURLDialog() {
        if (dialogAddURL == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            View view = LayoutInflater.from(this)
                    .inflate(R.layout.layout_add_url, findViewById(R.id.layoutAddUrlContainer));
            builder.setView(view);

            dialogAddURL = builder.create();
            if (dialogAddURL.getWindow() != null) {
                dialogAddURL.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }





//            if (ActivityCompat.checkSelfPermission(MainActivity.this
//                    , Manifest.permission.ACCESS_FINE_LOCATION) == getPackageManager().PERMISSION_GRANTED) {
//                getLocation();
//
//            } else {
//                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
//
//            }
            final EditText inputURL = view.findViewById(R.id.inputURL);
            final EditText inputURL2= view.findViewById(R.id.inputURL2);
            inputURL.requestFocus();
            inputURL2.requestFocus();

            view.findViewById(R.id.textAdd).setOnClickListener(v -> {

           if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED
           && ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED){

               getCurrentLocation(view);
               String inputURLStr = inputURL.getText().toString();
               String inputURLStr2=inputURL2.getText().toString();
               Intent intent = new Intent(getApplicationContext(), note_activity.class);
               intent.putExtra("isFromQuickActions", true);
               intent.putExtra("quickActionType", "URL");
               intent.putExtra("URL", "Latitude"+inputURLStr+","+"Longitude"+inputURLStr2);
               startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
               dialogAddURL.dismiss();
           }else{
               ActivityCompat.requestPermissions(MainActivity.this
               ,new String[]{
                       Manifest.permission.ACCESS_FINE_LOCATION,
                               Manifest.permission.ACCESS_COARSE_LOCATION},100);

           }
//
//                final String inputURLStr = inputURL.getText().toString().trim();
//                //code..
//
//                if (inputURLStr.isEmpty()) {
//                    Toast.makeText(MainActivity.this, "Enter URL", Toast.LENGTH_SHORT).show();
//                } else if (!Patterns.WEB_URL.matcher(inputURLStr).matches()) {
//                    Toast.makeText(MainActivity.this, "Enter valid URL", Toast.LENGTH_SHORT).show();
//                } else {
//                    dialogAddURL.dismiss();
//                    Intent intent = new Intent(getApplicationContext(), note_activity.class);
//                    intent.putExtra("isFromQuickActions", true);
//                    intent.putExtra("quickActionType", "URL");
//                    intent.putExtra("URL", inputURLStr);
//                    startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
//
//                }
            });





            view.findViewById(R.id.textCancel).setOnClickListener(v -> dialogAddURL.dismiss());
        }

        dialogAddURL.show();

    }



    @SuppressLint("MissingPermission")
    private void getCurrentLocation(View view) {

        LocationManager locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE
        );
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location=task.getResult();
                     if(location !=null){
                         LocationRequest locationRequest=new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                 .setInterval(1000).setFastestInterval(1000)
                                 .setFastestInterval(1);

                         LocationCallback locationCallback=new LocationCallback() {
                             @Override
                             public void onLocationResult(@NonNull LocationResult locationResult) {
                                 Location location1=locationResult.getLastLocation();
                                 final EditText inputURL = view.findViewById(R.id.inputURL);
                                 final EditText inputURL2=view.findViewById(R.id.inputURL2);
                                inputURL.setText(String.valueOf(location1.getLatitude()));
                                inputURL2.setText(String.valueOf(location1.getLongitude()));
//                                 String inputURLStr = inputURL.getText().toString();
//                                 String inputURLStr2=inputURL2.getText().toString();
//                                Intent intent = new Intent(getApplicationContext(), note_activity.class);
//                    intent.putExtra("isFromQuickActions", true);
//                    intent.putExtra("quickActionType", "URL");
//                    intent.putExtra("URL", inputURLStr);
//                    intent.putExtra("URL2",inputURLStr2);
//                    startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);

                             }
                         };
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                                locationCallback, Looper.myLooper());

                     }
                }
            });
        }else{
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

















//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//     fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//         @Override
//         public void onComplete(@NonNull Task<Location> task) {
//                         Location location=task.getResult();
//            if(location !=null)
//            try {
//                Geocoder geocoder=new Geocoder(MainActivity.this,Locale.getDefault());
//                List<Address>addresses=geocoder.getFromLocation(
//                        location.getLatitude(),location.getLongitude(),1
//                );
//                //            final EditText inputURL = view.findViewById(R.id.inputURL);
////            inputURL.requestFocus();
//                final EditText inputURL = findViewById(R.id.inputURL);
//                inputURL.setText(Html.fromHtml(
//                        "<font color= '#6200E'><b>Latitude:</b></br></font>"+addresses.get(0).getLatitude()
//                ));
//            }catch (IOException e){
//            e.printStackTrace();
//            }
//
//            }
//
//     });
//        }

//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//            Location location=task.getResult();
//            if(location !=null)
//            try {
//                Geocoder geocoder=new Geocoder(MainActivity.this,Locale.getDefault());
//                List<Address>addresses=geocoder.getFromLocation(
//                        location.getLatitude(),location.getLongitude(),1
//                );
//                //            final EditText inputURL = view.findViewById(R.id.inputURL);
////            inputURL.requestFocus();
//                final EditText inputURL = findViewById(R.id.inputURL);
//                inputURL.setText(Html.fromHtml(
//                        "<font color= '#6200E'><b>Latitude:</b></br></font>"+addresses.get(0).getLatitude()
//                ));
//            }catch (IOException e){
//            e.printStackTrace();
//            }
//
//            }
//        });
    }


    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN | ItemTouchHelper.START |ItemTouchHelper.END,0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPosition =viewHolder.getAdapterPosition();
            int toPosition =target.getAdapterPosition();

            Collections.swap(noteList,fromPosition,toPosition);
            recyclerView.getAdapter().notifyItemMoved(fromPosition,toPosition);


            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

}
