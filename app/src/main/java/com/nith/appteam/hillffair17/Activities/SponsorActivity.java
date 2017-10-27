package com.nith.appteam.hillffair17.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nith.appteam.hillffair17.Adapters.Notification;
import com.nith.appteam.hillffair17.Models.NewsFeed;
import com.nith.appteam.hillffair17.Models.SponsorArrayModel;
import com.nith.appteam.hillffair17.Notification.NotificationActivity;
import com.nith.appteam.hillffair17.R;
import com.nith.appteam.hillffair17.Adapters.SponsorAdapter;
import com.nith.appteam.hillffair17.Models.SponsorItem;
import com.nith.appteam.hillffair17.Utils.APIINTERFACE;
import com.nith.appteam.hillffair17.Utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SponsorActivity extends AppCompatActivity {
    private RecyclerView rvSponsor;
    private SponsorAdapter sponsorAdapter;
    private Toolbar tbSponsor;
    private ArrayList<SponsorItem> sponsorItems;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor);
//        String BASE_URL="https://s3.ap-south-1.amazonaws.com/hillffair2016/images/";
        String BASE_URL="https://github.com/";
        rvSponsor = findViewById(R.id.rvSponsor);
        progressBar = findViewById(R.id.sponsor_progressbar);
        sponsorItems = new ArrayList<>();

//        sponsorItems.add(new SponsorItem("SkyCandle.in",BASE_URL+"appteam-nith.png"));
//        sponsorItems.add(new SponsorItem("Board Of School Education, H.P.",BASE_URL+"appteam-nith.png"));
//        sponsorItems.add(new SponsorItem("Tata Shaktee",BASE_URL+"appteam-nith.png"));
//        sponsorItems.add(new SponsorItem("Cad Desk",BASE_URL+"appteam-nith.png"));
//        //  sponsorItems.add(new SponsorItem("",BASE_URL+"appteam-nith4.png"));
//        sponsorItems.add(new SponsorItem("HPSEDC",BASE_URL+"appteam-nith.png"));
//        sponsorItems.add(new SponsorItem("Ratan Jewellers",BASE_URL+"appteam-nith.png"));
//        sponsorItems.add(new SponsorItem("Chankya The Guru",BASE_URL+"appteam-nith.png"));
//        sponsorItems.add(new SponsorItem("L'OREAL",BASE_URL+"appteam-nith.png"));
//
//        sponsorAdapter = new SponsorAdapter(sponsorItems,SponsorActivity.this);
        rvSponsor.setAdapter(sponsorAdapter);

        tbSponsor = (Toolbar)findViewById(R.id.tbSponsor);
        tbSponsor.setTitle("Our Sponsors");
        setSupportActionBar(tbSponsor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar.setVisibility(View.VISIBLE);
        LinearLayoutManager lvmanager = new LinearLayoutManager(this);
        lvmanager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSponsor.setLayoutManager(lvmanager);
        getSponsors();
    }

    public void getSponsors() {
        APIINTERFACE mapi = Utils.getRetrofitService();
        Call<SponsorArrayModel> mService = mapi.getSponsor();
        mService.enqueue(new Callback<SponsorArrayModel>() {
            @Override
            public void onResponse(Call<SponsorArrayModel> call, Response<SponsorArrayModel> response) {
                try{
                    if(response.isSuccess())
                    {
                        if(response.body().getSponsors().size()>0)
                        {
                            sponsorItems = response.body().getSponsors();
                            sponsorAdapter = new SponsorAdapter(sponsorItems,getApplicationContext());
                        }
                        else
                        {
                            Toast.makeText(SponsorActivity.this, "No item recieved from server", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                        rvSponsor.setAdapter(sponsorAdapter);
                    }
                    else
                    {
                        Toast.makeText(SponsorActivity.this, "Error occured in server", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(SponsorActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    Log.d("Exception Sponsor",e.toString());
                }
            }

            @Override
            public void onFailure(Call<SponsorArrayModel> call, Throwable t) {

            }
        });
    }

    public void openBottomSheet(View v) {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        TextView txtBackup = (TextView) view.findViewById(R.id.txt_backup);
        TextView txtDetail = (TextView) view.findViewById(R.id.txt_detail);
        TextView txtOpen = (TextView) view.findViewById(R.id.txt_open);
        final TextView txtUninstall = (TextView) view.findViewById(R.id.txt_uninstall);
        final Dialog mBottomSheetDialog = new Dialog(SponsorActivity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
        txtBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ContributorsActivity.this, "Clicked Backup", Toast.LENGTH_SHORT).show();
                Intent i1 = new Intent(SponsorActivity.this, NotificationActivity.class);
                startActivity(i1);
                finish();
                mBottomSheetDialog.dismiss();
            }
        });
        txtDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SponsorActivity.this, WallIntroActivity.class));
                mBottomSheetDialog.dismiss();
            }
        });
        txtOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ContributorsActivity.this, "Clicked Open", Toast.LENGTH_SHORT).show();
                Intent i3 = new Intent(SponsorActivity.this, LeaderBoardActivity.class);
                startActivity(i3);
                finish();
                mBottomSheetDialog.dismiss();
            }
        });
        txtUninstall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
    }
}
