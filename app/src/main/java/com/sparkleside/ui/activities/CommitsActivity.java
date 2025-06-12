package com.sparkleside.ui.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.sparkleside.R;
import com.sparkleside.databinding.ActivityCommitsBinding;
import com.sparkleside.ui.base.BaseActivity;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CommitsActivity extends BaseActivity {
    private ActivityCommitsBinding binding;
    private final ArrayList<CommitItem> commitList = new ArrayList<>();
    private CommitsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivityCommitsBinding.inflate(getLayoutInflater());
        configureTransitions(R.id.coordinator);
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());
        setupToolbar();
        setupRecycler();
        loadCommits();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Commits");
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void setupRecycler() {
        adapter = new CommitsAdapter();
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(adapter);
        int spacing = (int) (5 * getResources().getDisplayMetrics().density);
        binding.recycler.addItemDecoration(new SpacingItemDecoration(spacing));
    }

    private void loadCommits() {
        new Thread(() -> {
            try {
                URL url = new URL("https://api.github.com/repositories/822718957/commits");
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setRequestProperty("Accept", "application/vnd.github.v3+json");
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) sb.append(line);
                br.close();
                Log.d("HTTPResponse", sb.toString());
                JSONArray commits = new JSONArray(sb.toString());
                commitList.clear();
                for (int i = 0; i < Math.min(commits.length(), 50); i++) {
                    JSONObject commit = commits.getJSONObject(i);
                    JSONObject commitData = commit.getJSONObject("commit");
                    JSONObject author = commit.getJSONObject("author");
                    String message = commitData.getString("message");
                    String sha = commit.getString("sha");
                    String htmlUrl = commit.getString("html_url");
                    String authorName = author.has("login") ? author.getString("login") : commitData.getJSONObject("author").getString("name");
                    String date = commitData.getJSONObject("author").getString("date");
                    String avatarUrl = "https://github.com/" + authorName + ".png";
                    String githubUrl = "https://github.com/" + authorName;
                    commitList.add(new CommitItem(message, sha, htmlUrl, authorName,
                            avatarUrl, githubUrl, date));
                }

                runOnUiThread(() -> adapter.notifyDataSetChanged());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    static class CommitItem {
        final String message, sha, htmlUrl, authorName, avatarUrl, githubUrl, date;

        CommitItem(String message, String sha, String htmlUrl, String authorName,
                   String avatarUrl, String githubUrl, String date) {
            this.message = message;
            this.sha = sha;
            this.htmlUrl = htmlUrl;
            this.authorName = authorName;
            this.avatarUrl = avatarUrl;
            this.githubUrl = githubUrl;
            this.date = date;
        }
    }

    class CommitsAdapter extends RecyclerView.Adapter<CommitsAdapter.CommitViewHolder> {

        @Override @NonNull
        public CommitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_commit, parent, false);
            return new CommitViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CommitViewHolder holder, int position) {
            CommitItem item = commitList.get(position);
            holder.messageText.setText(item.message);
            holder.authorText.setText(item.authorName);
            holder.shaText.setText(item.sha.substring(0, 7));
            if (position == 0) { holder.commitCard.setBackgroundResource(R.drawable.shape_top); }
            if (position == 29) { holder.commitCard.setBackgroundResource(R.drawable.shape_bottom); }
            try {
                SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                SimpleDateFormat output = new SimpleDateFormat("MMM dd, HH:mm", Locale.US);
                Date date = input.parse(item.date);
                holder.dateText.setText(output.format(date));
            } catch (Exception e) {
                holder.dateText.setText(item.date);
            }

            if (!item.avatarUrl.isEmpty()) {
                Glide.with(CommitsActivity.this)
                        .load(item.avatarUrl)
                        .transform(new CircleCrop())
                        .placeholder(R.drawable.scallops)
                        .into(holder.avatarImage);
            } else {
                holder.avatarImage.setImageResource(R.drawable.scallops);
            }

            holder.avatarImage.setOnClickListener(v -> {
                if (!item.githubUrl.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.githubUrl));
                    startActivity(intent);
                }
            });

            holder.commitCard.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.htmlUrl));
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return commitList.size();
        }

        class CommitViewHolder extends RecyclerView.ViewHolder {
            final LinearLayout commitCard;
            final ImageView avatarImage;
            final TextView messageText, authorText, dateText, shaText;
            CommitViewHolder(@NonNull View itemView) {
                super(itemView);
                commitCard = itemView.findViewById(R.id.commit_card);
                avatarImage = itemView.findViewById(R.id.avatar_image);
                messageText = itemView.findViewById(R.id.message_text);
                authorText = itemView.findViewById(R.id.author_text);
                dateText = itemView.findViewById(R.id.date_text);
                shaText = itemView.findViewById(R.id.sha_text);
            }
        }
    }

    static class SpacingItemDecoration extends RecyclerView.ItemDecoration {
        private final int spacing;

        SpacingItemDecoration(int spacing) {
            this.spacing = spacing;
        }

        @Override
        public void getItemOffsets(@NonNull android.graphics.Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            outRect.bottom = spacing;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = spacing;
            }
            outRect.left = spacing;
            outRect.right = spacing;
        }
    }
}