package com.example.facedectector;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE && resultCode==RESULT_OK)
        {
            assert data != null;
            Uri uri=data.getData();
            try {

                analyseimage(MediaStore.Images.Media.getBitmap(getContentResolver(), uri));
           //     process();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //  private Facing camerafacing = Facing.FRONT;
    ImageView imageView;
    final int PICK_IMAGE=1;
    //CameraView cameraView;
    BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView bottmRecyclerView;
    FrameLayout bottom_sheet_button;
    private ArrayList<Face_detection_Model> faceDetectionModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        faceDetectionModelArrayList = new ArrayList<>();
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet));
        imageView = findViewById(R.id.face_detection_image_type);

      //  cameraView.setLifecycleOwner(getViewLifecycleOwner());

       // Button toggle = findViewById(R.id.togglt_button);
         bottom_sheet_button = findViewById(R.id.bottom_sheet_button);
        bottmRecyclerView = findViewById(R.id.bottom_sheet_recyclerView);



        bottom_sheet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imagei=new Intent();
                imagei.setType("image/*");
                imagei.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(imagei,"select pic"),PICK_IMAGE);


            }
        });
        bottmRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bottmRecyclerView.setAdapter(new Face_detector_Adapter(faceDetectionModelArrayList));


    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                assert result != null;
                Uri imageuri = result.getUri();
                try {
                    analyseimage(MediaStore.Images.Media.getBitmap(getContentResolver(), imageuri));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    private void analyseimage(final Bitmap bitmap) {
        if (bitmap == null) {
            Toast.makeText(this, "There was an error", Toast.LENGTH_SHORT).show();


        } else {
            imageView.setImageBitmap(bitmap);
            faceDetectionModelArrayList.clear();
            Objects.requireNonNull(bottmRecyclerView.getAdapter()).notifyDataSetChanged();
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
          showProgress();
            FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
            FirebaseVisionFaceDetectorOptions firebaseVisionFaceDetectorOptions =
                    new FirebaseVisionFaceDetectorOptions.Builder()
                            .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                            .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                            .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                            .build();
            FirebaseVisionFaceDetector firebaseVisionFaceDetector = FirebaseVision.getInstance()
                    .getVisionFaceDetector(firebaseVisionFaceDetectorOptions);
            firebaseVisionFaceDetector.detectInImage(firebaseVisionImage)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionFace>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionFace> firebaseVisionFaces) {
                          //  Log.d("bbb",bitmap.toString());
                            Bitmap bitmap1 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                           // imageView.setBackgroundColor(Color.RED);
                            detectImages(firebaseVisionFaces, bitmap1);
hideProgress();

                            bottmRecyclerView.getAdapter().notifyDataSetChanged();
                            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        //    Log.d("aaa","bbbbbbbbbbbbbbbbbbbbbbbb");
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "There was an error", Toast.LENGTH_SHORT).show();
hideProgress();
                }
            });


        }
    }

    private void detectImages(List<FirebaseVisionFace> firebaseVisionFaces, Bitmap bitmap1) {
        if (firebaseVisionFaces == null || bitmap1 == null) {
            Toast.makeText(this, "There was an error", Toast.LENGTH_SHORT).show();
            return;
        } //Toast.makeText(this, "There was an error", Toast.LENGTH_SHORT).show();

    //   Log.d("aaa",bitmap1.toString());
        imageView.setImageBitmap(bitmap1);

        Canvas canvas=new Canvas(bitmap1);
        Paint facepaint=new Paint();
        facepaint.setColor(Color.GREEN);
        facepaint.setStyle(Paint.Style.STROKE);
        facepaint.setStrokeWidth(5f);

        Paint faceTEXTpaint=new Paint();
        faceTEXTpaint.setColor(Color.BLUE);
        faceTEXTpaint.setTextSize(30f);
        faceTEXTpaint.setTypeface(Typeface.SANS_SERIF);

        Paint landmarkpaint=new Paint();
        landmarkpaint.setColor(Color.RED);
        landmarkpaint.setStyle(Paint.Style.FILL);
        landmarkpaint.setStrokeWidth(8f);

        for (int i=0;i<firebaseVisionFaces.size();i++)
        {
            canvas.drawRect(firebaseVisionFaces.get(i).getBoundingBox(),facepaint);
            canvas.drawText("FACE:"+i+1,
                    (firebaseVisionFaces.get(i).getBoundingBox().centerX()-
                            (float)firebaseVisionFaces.get(i).getBoundingBox().width()/2)+8f,
                    (firebaseVisionFaces.get(i).getBoundingBox().centerY()-
                            (float) firebaseVisionFaces.get(i).getBoundingBox().height()/2)-8f,facepaint);
FirebaseVisionFace face=firebaseVisionFaces.get(i);
if(face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EYE)!=null)
{
    FirebaseVisionFaceLandmark lefteye=face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EYE);
    assert lefteye!=null;
    canvas.drawCircle(
            lefteye.getPosition().getX(),
            lefteye.getPosition().getY(),
            8f,
            landmarkpaint
    );
}
            if(face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EYE)!=null)
            {
                FirebaseVisionFaceLandmark lefteye=face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EYE);
                assert lefteye!=null;
                canvas.drawCircle(
                        lefteye.getPosition().getX(),
                        lefteye.getPosition().getY(),
                        8f,
                        landmarkpaint
                );
            }

            if(face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE)!=null)
            {
                FirebaseVisionFaceLandmark lefteye=face.getLandmark(FirebaseVisionFaceLandmark.NOSE_BASE);
                assert lefteye!=null;
                canvas.drawCircle(
                        lefteye.getPosition().getX(),
                        lefteye.getPosition().getY(),
                        8f,
                        landmarkpaint
                );
            }

            if(face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR)!=null)
            {
                FirebaseVisionFaceLandmark lefteye=face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EAR);
                assert lefteye!=null;
                canvas.drawCircle(
                        lefteye.getPosition().getX(),
                        lefteye.getPosition().getY(),
                        8f,
                        landmarkpaint
                );
            }


            if(face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR)!=null)
            {
                FirebaseVisionFaceLandmark lefteye=
                        face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EAR);
                assert lefteye!=null;
                canvas.drawCircle(
                        lefteye.getPosition().getX(),
                        lefteye.getPosition().getY(),
                        8f,
                        landmarkpaint
                );
            }
            if(face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_LEFT)!=null &&
                    face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_RIGHT)!=null &&
                    face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_BOTTOM)!=null)
            {
                FirebaseVisionFaceLandmark lm=
                        face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_LEFT);
                FirebaseVisionFaceLandmark rm=
                        face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_RIGHT);
                FirebaseVisionFaceLandmark bm=
                        face.getLandmark(FirebaseVisionFaceLandmark.MOUTH_BOTTOM);
                canvas.drawLine(lm.getPosition().getX(),
                        lm.getPosition().getY(),
                        bm.getPosition().getX(),
                        bm.getPosition().getY(),
                        landmarkpaint);
                canvas.drawLine(bm.getPosition().getX(),
                        bm.getPosition().getY(),
                        rm.getPosition().getX(),
                        rm.getPosition().getY(),
                        landmarkpaint);

            }
            faceDetectionModelArrayList.add(new Face_detection_Model
                    ("smiling Probability"+face.getSmilingProbability(),i+1));
            faceDetectionModelArrayList.add(new Face_detection_Model
                    ("left eye open Probability"+face.getLeftEyeOpenProbability(),i+1));

            faceDetectionModelArrayList.add(new Face_detection_Model
                    ("right eye open Probability"+face.getRightEyeOpenProbability(),i+1));



        }
        imageView.setImageBitmap(bitmap1);
    //    imageView.setBackgroundColor(Color.BLACK);



      //  process(bitmap1);


    }

    private void showProgress() {
        findViewById(R.id.bottom_sheet_button).setVisibility(View.GONE);
        findViewById(R.id.bottom_sheet_progressbar).setVisibility(View.VISIBLE);
    }

    private void hideProgress() {
        findViewById(R.id.bottom_sheet_button).setVisibility(View.VISIBLE);
        findViewById(R.id.bottom_sheet_progressbar).setVisibility(View.GONE);
    }





}