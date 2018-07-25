package com.example.a300142288.yogaproject;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class WatchLesson extends AppCompatActivity {
////////////////////////////
VideoView vView;
    ProgressBar vProgressBar;
    Button playPButton;
    int playing;
    int duration;
    int currentPosition;  //store this on the dataBase, linked to video int # (videoToPlay
    private MediaController mediaController;
    //i+j integers for hash map location. Hadn't planned on using them permanently, but
    //they error out when trying to rename throughout activity, so i+j stays
    int i, j;


    //video Attribution. Creative commons https://www.youtube.com/attribution?v=v-W7ho_2xqI

    //////////////////////////////
    UsersDataSource userDataSource;
    SelectDataSource selectDataSource;
    public static final String LOGTAG = "YOGALOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_lesson);

        userDataSource = new UsersDataSource(this);
        selectDataSource = new SelectDataSource(this);

        TextView txtTitle = (TextView)findViewById(R.id.txtWatchTitle);
        TextView txtDesc = (TextView)findViewById(R.id.txtDesc);
        final SharedPreferences selectionDetails = this.getSharedPreferences("selectionDetails", MODE_PRIVATE);
        final SharedPreferences userDetails = this.getSharedPreferences("userdetails", MODE_PRIVATE);
        int q = userDetails.getInt("userid",99);
        int w = userDetails.getInt("userWatching",99);
        Log.i(LOGTAG, "userid: "+ q + "userwatching" + w);



        //if database is not populated insert data

        if( !selectDataSource.isPopulated()){
            Log.i(LOGTAG,"populating lessons db");
            genLesson();
        }


        //get userID and check if they have watched videos before, if so call the last video they were watching
        int k = userDetails.getInt("userid", 0);
        int isWatching = userDetails.getInt("userWatching",0);
        Log.i(LOGTAG,"checking if wtching");


        //if user is watching (clicked continue) and they have watched previously import
        //the last watched video to play, if they are nt watching (clicked item in list)
        //use the list index to play video
        if(isWatching == 1){
            Log.i(LOGTAG," wtching");
            if(userDataSource.checkWatched(k)){
                Log.i(LOGTAG,"watched");
                i=userDataSource.getLevel(k);
                j=userDataSource.getPose(k);
                userDataSource.createLastWatched(k,i,j);

            }//checkwatched
        } else if(isWatching == 0){
            i = selectionDetails.getInt("selectionLevel", 0);
            j = selectionDetails.getInt("selectionPose", 0);
            Log.i(LOGTAG, "iswatching0 + level/pose:"+i+" " + j);
            userDataSource.createLastWatched(k,i,j);
        }



        //get title + description of video selected for loading
        Lesson lesson= selectDataSource.findVideo(i,j);
        lesson.setLevel(i);
        lesson.setPose(j);

        //set description and title of page
        txtTitle.setText("Lesson " + i + " Pose " + j);
        txtDesc.setText(lesson.getDesc());

        ///////////////////////////////////////////////////////////////////




        vView = (VideoView) findViewById(R.id.videoView);
        try {
           //get title passed from database as resource id & pass to player
            int id = this.getResources().getIdentifier(lesson.getTitle(), "raw", this.getPackageName());
            vView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));


        }catch (Exception e){
            Toast.makeText(this, "Video undefined. Error:1", Toast.LENGTH_SHORT).show();
        }


        playPButton = (Button) findViewById(R.id.btnPlayPause);

        // Set the media controller buttons
        if (mediaController == null) {
            mediaController = new MediaController(WatchLesson.this);

            // Set the videoView that acts as the anchor for the MediaController.
            mediaController.setAnchorView(vView);

            // Set MediaController for VideoView
            vView.setMediaController(mediaController);
        }

        vView.requestFocus();


        playPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    switch (playing) {
                        case 0:
                            //vView.seekTo(33333);
                            vView.start();
                            duration = vView.getDuration();
                            String duratTimeFormat = milliSecondsToTimer(duration);


                            playing = 1;
                            Toast.makeText(WatchLesson.this, String.valueOf(duratTimeFormat), Toast.LENGTH_SHORT).show();
                            break;
                        case 1:

                            vView.pause();
                            currentPosition = vView.getCurrentPosition();
                            String currentTimeFormat = milliSecondsToTimer(currentPosition);
                            /**Save currentPosition to dataBase */

                            Toast.makeText(WatchLesson.this, String.valueOf(currentTimeFormat), Toast.LENGTH_SHORT).show();

                            playing = 0;
                            break;
                    }
                }catch(Exception e) {
                    Toast.makeText(WatchLesson.this, "Video not defined. Error:2", Toast.LENGTH_SHORT).show();
                }
            }
        });

        vProgressBar = (ProgressBar) findViewById(R.id.progressBar2);
        vProgressBar.setProgress(0);
        vProgressBar.setMax(100);














///////////////////////////////////////////////////////////////////////////

    }//oncreate

    protected void sharedPref(int levelPos, int posePos){
        SharedPreferences selectionDetails = this.getSharedPreferences("selectionDetails", MODE_PRIVATE);
        SharedPreferences.Editor edit = selectionDetails.edit();

        edit.clear();
        edit.putInt("selectionLevel", levelPos);
        edit.putInt("selectionPose",posePos);
        edit.commit();
        //Toast.makeText(this, "Login details are saved.. userid: "+i , Toast.LENGTH_SHORT).show();
    }

    public void genLesson(){
        Lesson lesson = new Lesson();
        lesson.setTitle("mountainpose");
        lesson.setDesc("Stand tall with feet together, shoulders relaxed, weight evenly distributed through your soles, arms at sides.\n" +
                "Take a deep breath and raise your hands overhead, palms facing each other with arms straight. Reach up toward the sky with your fingertips.");
        lesson.setLevel(0);
        lesson.setPose(0);
        lesson = selectDataSource.create(lesson);

        lesson.setTitle("downwarddog");
        lesson.setDesc("Start on all fours with hands directly under shoulders, knees under hips.\n" +
                "Walk hands a few inches forward and spread fingers wide, pressing palms into mat.\n" +
                "Curl toes under and slowly press hips toward ceiling, bringing your body into an inverted V, pressing shoulders away from ears. Feet should be hip-width apart, knees slightly bent.\n" +
                "Hold for 3 full breaths.");
        lesson.setLevel(0);
        lesson.setPose(1);
        lesson = selectDataSource.create(lesson);

        lesson.setTitle("warrior");
        lesson.setDesc("Stand with legs 3 to 4 feet apart, turning right foot out 90 degrees and left foot in slightly.\n" +
                "Bring your hands to your hips and relax your shoulders, then extend arms out to the sides, palms down.\n" +
                "Bend right knee 90 degrees, keeping knee over ankle; gaze out over right hand. Stay for 1 minute.\n" +
                "Switch sides and repeat.");
        lesson.setLevel(0);
        lesson.setPose(2);
        lesson = selectDataSource.create(lesson);

        lesson.setTitle("treepose");
        lesson.setDesc("Stand with arms at sides.\n" +
                "Shift weight onto left leg and place sole of right foot inside left thigh, keeping hips facing forward.\n" +
                "Once balanced, bring hands in front of you in prayer position, palms together.\n" +
                "On an inhalation, extend arms over shoulders, palms separated and facing each another. Stay for 30 seconds.\n" +
                "Lower and repeat on opposite side.");
        lesson.setLevel(0);
        lesson.setPose(3);
        lesson = selectDataSource.create(lesson);

        lesson.setTitle("bridgepose");
        lesson.setDesc("Lie on floor with knees bent and directly over heels.\n" +
                "Place arms at sides, palms down. Exhale, then press feet into floor as you lift hips.\n" +
                "Clasp hands under lower back and press arms down, lifting hips until thighs are parallel to floor, bringing chest toward chin. Hold for 1 minute.");
        lesson.setLevel(1);
        lesson.setPose(0);
        lesson = selectDataSource.create(lesson);

        lesson.setTitle("trianglepose");
        lesson.setDesc("Extend arms out to sides, then bend over your right leg.\n" +
                "Stand with feet about 3 feet apart, toes on your right foot turned out to 90 degrees, left foot to 45 degrees.\n" +
                "Allow your right hand to touch the floor or rest on your right leg below or above the knee, and extend the fingertips of your left hand toward the ceiling.\n" +
                "Turn your gaze toward the ceiling, and hold for 5 breaths.\n" +
                "Stand and repeat on opposite side.");
        lesson.setLevel(1);
        lesson.setPose(1);
        lesson = selectDataSource.create(lesson);

        lesson.setTitle("seatedtwist");
        lesson.setDesc("Sit on the floor with your legs extended.\n" +
                "Cross right foot over outside of left thigh; bend left knee. Keep right knee pointed toward ceiling.\n" +
                "Place left elbow to the outside of right knee and right hand on the floor behind you.\n" +
                "Twist right as far as you can, moving from your abdomen; keep both sides of your butt on the floor. Stay for 1 minute.\n" +
                "Switch sides and repeat.");
        lesson.setLevel(1);
        lesson.setPose(2);
        lesson = selectDataSource.create(lesson);

        lesson.setTitle("cobra");
        lesson.setDesc("Lie facedown on the floor with thumbs directly under shoulders, legs extended with the tops of your feet on the floor.\n" +
                "Tighten your pelvic floor, and tuck hips downward as you squeeze your glutes.\n" +
                "Press shoulders down and away from ears.\n" +
                "Push through your thumbs and index fingers as you raise your chest toward the wall in front of you.\n" +
                "Relax and repeat.");
        lesson.setLevel(2);
        lesson.setPose(0);
        lesson = selectDataSource.create(lesson);

        lesson.setTitle("pidgeonpose");
        lesson.setDesc("Begin in a full push-up position, palms aligned under shoulders.\n" +
                "Place left knee on the floor near shoulder with left heel by right hip.\n" +
                "Lower down to forearms and bring right leg down with the top of the foot on the floor (not shown).\n" +
                "Keep chest lifted to the wall in front of you, gazing down.");
        lesson.setLevel(2);
        lesson.setPose(1);
        lesson = selectDataSource.create(lesson);

        lesson.setTitle("crowpose");
        lesson.setDesc("Get into downward dog position (palms pressed into mat, feet hip-width apart) and walk feet forward until knees touch your arms.\n" +
                "Bend your elbows, lift heels off floor, and rest knees against the outside of your upper arms. Keep toes on floor, abs engaged and legs pressed against arms. Hold for 5 to 10 breaths.");
        lesson.setLevel(2);
        lesson.setPose(2);
        lesson = selectDataSource.create(lesson);

    }//genList


    /////////////////////////////////////////////////////////////
//Method used for saving to the dataBase when user goes back
    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {

            //Here we want to save on Database, the current position of the video.
            Toast.makeText(this, "Going back", Toast.LENGTH_SHORT).show();
        }
        return super.onKeyDown(keyCode, event);
    }
*/

    // Convert millisecond to string.
    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

////////////////////////////////////////////////////////////////
}//class
