package com.vango.testownik.ui.main;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dagger.hilt.android.AndroidEntryPoint;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.vango.testownik.MainActivity;
import com.vango.testownik.R;
import com.vango.testownik.databinding.MainFragmentBinding;
import com.vango.testownik.model.MainModel;
import com.vango.testownik.ui.QuizFragment;
import com.vango.testownik.ui.SettingsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

@AndroidEntryPoint
public class MainFragment extends Fragment implements MainAdapter.OnNoteListener{
    @Inject
    public MainFragment(){
    }
     private MainViewModel mViewModel;
    private MainFragmentBinding binding;
    ArrayList<MainModel> mainModels;
    MainAdapter mainAdapter;
    List<String> namee;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding= MainFragmentBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        ActionBar actionBar =((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#B0CAFF")));
        actionBar.setDisplayHomeAsUpEnabled(false);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setHasOptionsMenu(true);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setRecyclerView();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.Ustawienia){
            ((MainActivity)getActivity()).replaceFragment(SettingsFragment.class,"Settings");
        }
        return super.onOptionsItemSelected(item);
    }

    public void setRecyclerView(){
        Integer[] logo={R.drawable.miernictwo,R.drawable.air,R.drawable.telekomuna
                ,R.drawable.pps,R.drawable.pps2,R.drawable.izs,R.drawable.po};
        namee=new ArrayList<>();
        String []names={"Miernictwo","Pair","Pt","Pps","Pps2","Izs","Po"}; // db names for retrofit
        namee= Arrays.asList(names);
        mainModels=new ArrayList<>();
        for (int i = 0; i <logo.length; i++) {
            MainModel model = new MainModel(logo[i],names[i]);
            mainModels.add(model);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                getContext(),LinearLayoutManager.HORIZONTAL,false);
        binding.recycler.setLayoutManager(layoutManager);
        binding.recycler.setItemAnimator(new DefaultItemAnimator());
        mainAdapter= new MainAdapter(getContext(),mainModels,this);
        binding.recycler.setAdapter(mainAdapter);
    }

    @Override
    public void onNoteClick(int position) {
        ((MainActivity)getActivity()).replaceFragment(QuizFragment.class,namee.get(position));

    }
}