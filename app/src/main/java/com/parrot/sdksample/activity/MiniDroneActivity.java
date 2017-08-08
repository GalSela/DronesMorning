package com.parrot.sdksample.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.*;
import android.os.Handler;


import com.parrot.arsdk.arcommands.ARCOMMANDS_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM;
import com.parrot.arsdk.arcommands.ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM;
import com.parrot.arsdk.arcontroller.ARCONTROLLER_DEVICE_STATE_ENUM;
import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceService;
import com.parrot.sdksample.R;
import com.parrot.sdksample.drone.MiniDrone;

public class MiniDroneActivity extends AppCompatActivity {
    private static final String TAG = "MiniDroneActivity";
    private MiniDrone mMiniDrone;
    private ImageView bsmh;
    private ProgressDialog mConnectionProgressDialog;
    private ProgressDialog mDownloadProgressDialog;

    private TextView head;
    private TextView welcome;
    private Button btnStartIt;
    private Button btnMission1;
    private Button btnMission2;
    private Button btnMission3;
    private int mNbMaxDownload;
    private int mCurrentDownloadIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minidrone);
        initIHM();


        Intent intent = getIntent();
        ARDiscoveryDeviceService service = intent.getParcelableExtra(DeviceListActivity.EXTRA_DEVICE_SERVICE);
        mMiniDrone = new MiniDrone(this, service);
        mMiniDrone.addListener(mMiniDroneListener);



    }
    public void download()
    {
        // Download pictures
        mMiniDrone.getLastFlightMedias();

        mDownloadProgressDialog = new ProgressDialog(MiniDroneActivity.this, R.style.AppCompatAlertDialogStyle);
        mDownloadProgressDialog.setIndeterminate(true);
        mDownloadProgressDialog.setMessage("Fetching medias");
        mDownloadProgressDialog.setCancelable(false);
        mDownloadProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mMiniDrone.cancelGetLastFlightMedias();
            }
        });
        mDownloadProgressDialog.show();
    }
    public void take_off()
    {
        // Start flying
        mMiniDrone.takeOff();
    }
    public void land()
    {
        // Stop flying
        mMiniDrone.land();
    }
    public void message(String mes)
    {

    }
    public void take_picture() {
        // Stop flying
        final Handler handler0 = new Handler();
        handler0.postDelayed(new Runnable(){
            @Override
            public void run()
            {
                mMiniDrone.takePicture();


            }
        }, 1000);
        final Handler handler1 = new Handler();
        handler0.postDelayed(new Runnable(){
            @Override
            public void run()
            {
                int i = 0;
            }
        }, 1000);
    }
    public void move_left(int count) {
        // Move left
        for (int i = 0; i < count; i++)
            mMiniDrone.setYaw((byte) -50);
        mMiniDrone.setYaw((byte) 0);

    }
    public void move_right(int count) {
        // Move right
        for (int i = 0; i < count; i++)
            mMiniDrone.setYaw((byte) 50);
        mMiniDrone.setYaw((byte) 0);
    }

    public void roll_right() {
        // Roll right
        mMiniDrone.setFlag((byte) 1);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run()
            {
                mMiniDrone.setRoll((byte) 50);
            }
        }, 1700);
        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable(){
            @Override
            public void run()
            {
                mMiniDrone.setRoll((byte) 0);
            }
        }, 5000);
    }


    public void go_forward() {
        // Roll right
        mMiniDrone.setFlag((byte) 1);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run()
            {

                mMiniDrone.setPitch((byte) 50);
            }
        }, 1000);

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable(){
            @Override
            public void run()
            {

                mMiniDrone.setPitch((byte) 0);

                mMiniDrone.setGaz((byte)50);

                mMiniDrone.setPitch((byte) 0);

                take_picture();

            }
        }, 3500);
    }

    public void roll_left() {
        mMiniDrone.setFlag((byte) 1);
        final Handler rollLeft = new Handler();
        rollLeft.postDelayed(new Runnable(){
            @Override
            public void run()
            {

                mMiniDrone.setYaw((byte) -50);
            }
        }, 1000);

        final Handler stopRollLeft = new Handler();
        stopRollLeft.postDelayed(new Runnable(){
            @Override
            public void run()
            {

                mMiniDrone.setYaw((byte) 0);
            }
        }, 2000);


    }

    public void mission1()  {
        // Children HERE


        take_off();

        final Handler take_picture = new Handler();
        take_picture.postDelayed(new Runnable(){
            @Override
            public void run()
            {

                take_picture();

            }
        }, 1000);

        final Handler downloadLand = new Handler();
        downloadLand.postDelayed(new Runnable(){
            @Override
            public void run()
            {

                download();

                land();

            }
        }, 2000);
    }


    public void mission2()  {
        // Children HERE


        take_off();
        final Handler forward = new Handler();
        forward.postDelayed(new Runnable(){
            @Override
            public void run()
            {

                go_forward();

            }
        }, 1000);

        final Handler take_picture = new Handler();
        take_picture.postDelayed(new Runnable(){
            @Override
            public void run()
            {

                take_picture();

            }
        }, 4000);

        final Handler downloadLand = new Handler();
        downloadLand.postDelayed(new Runnable(){
            @Override
            public void run()
            {

                download();

                land();
            }
        }, 6000);
    }



    public void mission3()  {
        // Children HERE


        take_off();
       final Handler forward1 = new Handler();
                forward1.postDelayed(new Runnable(){
                    @Override
                    public void run()
                    {
                        go_forward();

                    }
        }, 2000);

        final Handler roll_left = new Handler();
        roll_left.postDelayed(new Runnable(){
            @Override
            public void run()
            {
                roll_left();

            }
        }, 4000);

        final Handler forward2 = new Handler();
        forward2.postDelayed(new Runnable(){
            @Override
            public void run()
            {
                go_forward();

            }
        }, 6000);

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable(){
            @Override
            public void run()
            {

                download();

                land();
            }
        }, 8500);
    }


    @Override
    protected void onStart() {
        super.onStart();

        // show a loading view while the minidrone is connecting
        if ((mMiniDrone != null) && !(ARCONTROLLER_DEVICE_STATE_ENUM.ARCONTROLLER_DEVICE_STATE_RUNNING.equals(mMiniDrone.getConnectionState())))
        {
            mConnectionProgressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
            mConnectionProgressDialog.setIndeterminate(true);
            mConnectionProgressDialog.setMessage("Connecting ...");
            mConnectionProgressDialog.setCancelable(false);
            mConnectionProgressDialog.show();

            // if the connection to the MiniDrone fails, finish the activity
            if (!mMiniDrone.connect()) {
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mMiniDrone != null)
        {
            mConnectionProgressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
            mConnectionProgressDialog.setIndeterminate(true);
            mConnectionProgressDialog.setMessage("Disconnecting ...");
            mConnectionProgressDialog.setCancelable(false);
            mConnectionProgressDialog.show();

            if (!mMiniDrone.disconnect()) {
                finish();
            }
        } else {
            finish();
        }
    }

    @Override
    public void onDestroy()
    {
        mMiniDrone.dispose();
        super.onDestroy();
    }

    private void initIHM() {



        findViewById(R.id.btnEmerg).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mMiniDrone.emergency();
            }
        });

        findViewById(R.id.ligth_on).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mMiniDrone.ligthsOn();
            }
        });

        findViewById(R.id.ligth_off).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mMiniDrone.ligthsOff();
            }
        });

        findViewById(R.id.take_off).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                take_off();
            }
        });

        findViewById(R.id.up).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        mMiniDrone.setGaz((byte) 50);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        mMiniDrone.setGaz((byte) 0);
                        break;

                    default:

                        break;
                }

                return true;
            }
        });

        findViewById(R.id.down).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        mMiniDrone.setGaz((byte) -50);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        mMiniDrone.setGaz((byte) 0);
                        break;

                    default:

                        break;
                }

                findViewById(R.id.left).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                v.setPressed(true);
                                mMiniDrone.setFlag((byte) 1);
                                mMiniDrone.setRoll((byte) -50);
                                break;

                            case MotionEvent.ACTION_UP:
                                v.setPressed(false);
                                mMiniDrone.setFlag((byte) 1);
                                mMiniDrone.setRoll((byte) 0);
                                break;

                            default:

                                break;
                        }

                        return true;
                    }
                });

                findViewById(R.id.right).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                v.setPressed(true);
                                mMiniDrone.setFlag((byte) 1);
                                mMiniDrone.setRoll((byte) 50);
                                break;

                            case MotionEvent.ACTION_UP:
                                v.setPressed(false);
                                mMiniDrone.setFlag((byte) 1);
                                mMiniDrone.setRoll((byte) 0);
                                break;

                            default:

                                break;
                        }

                        return true;
                    }
                });

                findViewById(R.id.forward).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                v.setPressed(true);
                                mMiniDrone.setFlag((byte) 1);
                                mMiniDrone.setPitch((byte) 50);
                                break;

                            case MotionEvent.ACTION_UP:
                                v.setPressed(false);
                                mMiniDrone.setFlag((byte) 1);
                                mMiniDrone.setPitch((byte) 0);
                                break;

                            default:

                                break;
                        }

                        return true;
                    }
                });

                findViewById(R.id.backward).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                v.setPressed(true);
                                mMiniDrone.setFlag((byte) 1);
                                mMiniDrone.setPitch((byte) -50);
                                break;

                            case MotionEvent.ACTION_UP:
                                v.setPressed(false);
                                mMiniDrone.setFlag((byte) 1);
                                mMiniDrone.setPitch((byte) 0);
                                break;

                            default:

                                break;
                        }

                        return true;
                    }
                });

                return true;
            }
        });

        findViewById(R.id.yaw_left).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        mMiniDrone.setYaw((byte) -50);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        mMiniDrone.setYaw((byte) 0);
                        break;

                    default:

                        break;
                }

                return true;
            }
        });

        findViewById(R.id.yaw_right).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        mMiniDrone.setYaw((byte) 50);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        mMiniDrone.setYaw((byte) 0);
                        break;

                    default:

                        break;
                }

                return true;
            }
        });

        findViewById(R.id.land).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        mMiniDrone.land();
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        break;

                    default:

                        break;
                }

                return true;
            }
        });

        findViewById(R.id.camera).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        take_picture();
                        break;

                    case MotionEvent.ACTION_UP:
                        break;

                    default:

                        break;
                }

                return true;
            }
        });

        findViewById(R.id.download).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        download();
                        land();
                        break;

                    case MotionEvent.ACTION_UP:
                        break;

                    default:

                        break;
                }

                return true;
            }
        });





       /* mDownloadBt = (Button)findViewById(R.id.downloadBt);
        mDownloadBt.setEnabled(false);
        mDownloadBt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mMiniDrone.getLastFlightMedias();

                mDownloadProgressDialog = new ProgressDialog(MiniDroneActivity.this, R.style.AppCompatAlertDialogStyle);
                mDownloadProgressDialog.setIndeterminate(true);
                mDownloadProgressDialog.setMessage("Fetching medias");
                mDownloadProgressDialog.setCancelable(false);
                mDownloadProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mMiniDrone.cancelGetLastFlightMedias();
                    }
                });
                mDownloadProgressDialog.show();
            }
        });

        findViewById(R.id.gazUpBt).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        mMiniDrone.setGaz((byte) 50);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        mMiniDrone.setGaz((byte) 0);
                        break;

                    default:

                        break;
                }

                return true;
            }
        });

        findViewById(R.id.up).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        mMiniDrone.setGaz((byte) 50);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        mMiniDrone.setGaz((byte) 0);
                        break;

                    default:

                        break;
                }

                return true;
            }
        });

        findViewById(R.id.yawLeftBt).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        mMiniDrone.setYaw((byte) -50);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        mMiniDrone.setYaw((byte) 0);
                        break;

                    default:

                        break;
                }

                return true;
            }
        });

        findViewById(R.id.yawRightBt).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        mMiniDrone.setYaw((byte) 50);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        mMiniDrone.setYaw((byte) 0);
                        break;

                    default:

                        break;
                }

                return true;
            }
        });

        findViewById(R.id.forwardBt).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        mMiniDrone.setPitch((byte) 50);
                        mMiniDrone.setFlag((byte) 1);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        mMiniDrone.setPitch((byte) 0);
                        mMiniDrone.setFlag((byte) 0);
                        break;

                    default:

                        break;
                }

                return true;
            }
        });

        findViewById(R.id.backBt).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        mMiniDrone.setPitch((byte) -50);
                        mMiniDrone.setFlag((byte) 1);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        mMiniDrone.setPitch((byte) 0);
                        mMiniDrone.setFlag((byte) 0);
                        break;

                    default:

                        break;
                }

                return true;
            }
        });

        findViewById(R.id.rollLeftBt).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        mMiniDrone.setRoll((byte) -50);
                        mMiniDrone.setFlag((byte) 1);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        mMiniDrone.setRoll((byte) 0);
                        mMiniDrone.setFlag((byte) 0);
                        break;

                    default:

                        break;
                }

                return true;
            }
        });

        findViewById(R.id.rollRightBt).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setPressed(true);
                        mMiniDrone.setRoll((byte) 50);
                        mMiniDrone.setFlag((byte) 1);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.setPressed(false);
                        mMiniDrone.setRoll((byte) 0);
                        mMiniDrone.setFlag((byte) 0);
                        break;

                    default:

                        break;
                }

                return true;
            }
        });
        */

    }

    private final MiniDrone.Listener mMiniDroneListener = new MiniDrone.Listener() {
        @Override
        public void onDroneConnectionChanged(ARCONTROLLER_DEVICE_STATE_ENUM state) {
            switch (state)
            {
                case ARCONTROLLER_DEVICE_STATE_RUNNING:
                    mConnectionProgressDialog.dismiss();
                    break;

                case ARCONTROLLER_DEVICE_STATE_STOPPED:
                    // if the deviceController is stopped, go back to the previous activity
                    mConnectionProgressDialog.dismiss();
                    finish();
                    break;

                default:
                    break;
            }
        }

        @Override
        public void onBatteryChargeChanged(int batteryPercentage) {
            int i = 0;
        }

        @Override
        public void onPilotingStateChanged(ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_ENUM state) {
            switch (state) {
                case ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_LANDED:
                    //btnStartIt.setText("Start");
                    //btnStartIt.setEnabled(true);
                    break;
                case ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_FLYING:
                case ARCOMMANDS_MINIDRONE_PILOTINGSTATE_FLYINGSTATECHANGED_STATE_HOVERING:
                    //btnStartIt.setText("Now Flying...");
                    //btnStartIt.setEnabled(false);
                    break;
                default:
                    int i = 0;
                    //btnStartIt.setEnabled(true);
            }
        }

        @Override
        public void onPictureTaken(ARCOMMANDS_MINIDRONE_MEDIARECORDEVENT_PICTUREEVENTCHANGED_ERROR_ENUM error) {
            Log.i(TAG, "Picture has been taken");
        }

        @Override
        public void onMatchingMediasFound(int nbMedias) {
            mDownloadProgressDialog.dismiss();

            mNbMaxDownload = nbMedias;
            mCurrentDownloadIndex = 1;

            if (nbMedias > 0) {
                mDownloadProgressDialog = new ProgressDialog(MiniDroneActivity.this, R.style.AppCompatAlertDialogStyle);
                mDownloadProgressDialog.setIndeterminate(false);
                mDownloadProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mDownloadProgressDialog.setMessage("Downloading medias");
                mDownloadProgressDialog.setMax(mNbMaxDownload * 100);
                mDownloadProgressDialog.setSecondaryProgress(mCurrentDownloadIndex * 100);
                mDownloadProgressDialog.setProgress(0);
                mDownloadProgressDialog.setCancelable(false);
                mDownloadProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mMiniDrone.cancelGetLastFlightMedias();
                    }
                });
                mDownloadProgressDialog.show();
            }
        }

        @Override
        public void onDownloadProgressed(String mediaName, int progress) {
            mDownloadProgressDialog.setProgress(((mCurrentDownloadIndex - 1) * 100) + progress);
        }

        @Override
        public void onDownloadComplete(String mediaName) {
            mCurrentDownloadIndex++;
            mDownloadProgressDialog.setSecondaryProgress(mCurrentDownloadIndex * 100);

            if (mCurrentDownloadIndex > mNbMaxDownload) {
                mDownloadProgressDialog.dismiss();
                mDownloadProgressDialog = null;
            }
        }
    };
}
