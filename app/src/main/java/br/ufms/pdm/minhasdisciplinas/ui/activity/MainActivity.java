package br.ufms.pdm.minhasdisciplinas.ui.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import br.ufms.pdm.minhasdisciplinas.databinding.ActivityMainBinding;
import br.ufms.pdm.minhasdisciplinas.ui.tab.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private static final String TITULO_APPBAR = "Minhas Disciplinas";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTitle(TITULO_APPBAR);

        ViewPager viewPager = configuraViewPager();
        configuraTabs(viewPager);
    }

    @NonNull
    private ViewPager configuraViewPager() {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.activityMainViewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        return viewPager;
    }

    private void configuraTabs(ViewPager viewPager) {
        TabLayout tabs = binding.activityMainTabs;
        tabs.setupWithViewPager(viewPager);
    }
}