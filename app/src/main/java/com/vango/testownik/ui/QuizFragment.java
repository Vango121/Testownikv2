package com.vango.testownik.ui;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.hilt.android.AndroidEntryPoint;
import es.dmoral.toasty.Toasty;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.vango.testownik.MainActivity;
import com.vango.testownik.R;
import com.vango.testownik.databinding.QuizFragmentBinding;
import com.vango.testownik.model.Answer;
import com.vango.testownik.model.QuizModel;
import com.vango.testownik.ui.main.MainFragment;
import com.vango.testownik.util.QuizNames;
import com.vango.testownik.util.QuizViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

@AndroidEntryPoint
public class QuizFragment extends Fragment implements ButtonHandler{

    private QuizFragmentBinding binding;
    public static String whichQuiz;
    private QuizViewModel mViewModel;
    private boolean[] buttonsChecked = {false,false,false,false};
    QuizModel currentquestion;
    List<Answer>currentAnswers;
    @Inject
    QuizViewModelFactory quizViewModelFactory;

    public static QuizFragment newInstance() {
        return new QuizFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding= QuizFragmentBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        ActionBar actionBar =((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#5C92FD")));
         whichQuiz = getArguments().getString("Quiz");

        Toast.makeText(getContext(), whichQuiz, Toast.LENGTH_SHORT).show();
//        mViewModel = new ViewModelProvider(this,quizViewModelFactory).get(QuizViewModel.class);
        binding.setHandler(this);
        binding.setViewmodel(mViewModel);
//        mViewModel.getQuestions(whichQuiz);
//        mViewModel.questions.observe(getViewLifecycleOwner(), quizModel -> {
//            mViewModel.nextQuestion();
//        });
//        mViewModel.nextQuestion.observe(getViewLifecycleOwner(),question->{
//            setQuestion(question);
//            currentquestion=question;
//                });
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            mViewModel.saveCount(whichQuiz);
            ((MainActivity)getActivity()).replaceFragment(MainFragment.class,"");

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    int rowcount;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider(this,quizViewModelFactory).get(QuizViewModel.class);
        mViewModel.getCount(whichQuiz);
        mViewModel.getQuestions(whichQuiz);
        mViewModel.rowCount.observe(getViewLifecycleOwner(), integer ->{
            rowcount=integer;
            Log.i("rowcount",integer+"");
                }
        );
        mViewModel.questionsLiveData().observe(getViewLifecycleOwner(),event->{
            List<QuizModel> quizModel= event.getContentIfNotHandled();
            Log.i("quizModel",quizModel.size()+"");
            mViewModel.setQuestions(quizModel);
            mViewModel.getNumberOfQuestions();
            if(binding.buttonA.getText().equals("Button")){
                mViewModel.nextQuestion();
            }
        });
        mViewModel.retrofit.observe(getViewLifecycleOwner(),quizModels -> {
            if (rowcount == 0) {
                mViewModel.insert(whichQuiz);
                Log.i("insert","insert");
            }
            else {
                mViewModel.updateRetrofit(quizModels,whichQuiz);
            }
        });
//        mViewModel.questionsLiveData.observe(getViewLifecycleOwner(), quizModel -> {
//            quizModel.getContentIfNotHandled()
//            mViewModel.setQuestions(quizModel);
//            mViewModel.getNumberOfQuestions();
//            if (rowcount == 0) {
//                mViewModel.insert(whichQuiz);
//            }
//            else {
//                mViewModel.update(whichQuiz);
//            }
//            //mViewModel.nextQuestion();
//
//        });
        mViewModel.nextQuestion.observe(getViewLifecycleOwner(),question->{
            setQuestion(question);
            currentquestion=question;
        });
        mViewModel.questionCount.observe(getViewLifecycleOwner(), questionCount->{
            binding.animateProgressBar.setMax(questionCount);
        });
        mViewModel.goodAnswers.observe(getViewLifecycleOwner(), goodAnswers->{
            binding.animateProgressBar.setProgress(goodAnswers);
        });
        if(whichQuiz.equals(QuizNames.miernictwo)){
            mViewModel.questionsMiernictwo.observe(getViewLifecycleOwner(), miernictwos -> {
                mViewModel.cast();
            });
        }
        else if(whichQuiz.equals(QuizNames.pair)){
            mViewModel.questionsPair.observe(getViewLifecycleOwner(), pair->{
                mViewModel.cast();
            });
        }

//        mViewModel.questions.observe(getViewLifecycleOwner(), quizModel -> {
//            Log.i("tag",quizModel.get(0).getQuestion());
//        });
    }
    public void setQuestion(QuizModel question){
        List<Button> buttons = new ArrayList<>();
        buttons.add(binding.buttonA);
        buttons.add(binding.buttonB);
        buttons.add(binding.buttonC);
        buttons.add(binding.buttonD);
        Random rand = new Random();
        int buttonNumber;
        binding.question.setText(question.getQuestion());
        binding.licznik.setText("Pozostałe powtórzenia: "+question.getCount());
        buttonNumber=rand.nextInt(4);
        buttons.get(buttonNumber).setText(question.getAnswerA().getAnswer());
        buttons.get(buttonNumber).setTag(question.getAnswerA());
        buttons.remove(buttonNumber);
        buttonNumber=rand.nextInt(3);
        buttons.get(buttonNumber).setText(question.getAnswerB().getAnswer());
        buttons.get(buttonNumber).setTag(question.getAnswerB());
        buttons.remove(buttonNumber);
        buttonNumber=rand.nextInt(2);
        buttons.get(buttonNumber).setText(question.getAnswerC().getAnswer());
        buttons.get(buttonNumber).setTag(question.getAnswerC());
        buttons.remove(buttonNumber);
        buttons.get(0).setText(question.getAnswerD().getAnswer());
        buttons.get(0).setTag(question.getAnswerD());
    }

    @Override
    public void checkAnswers(View view){
        Toasty.Config.getInstance().allowQueue(false).apply();
        Button[] buttons = {binding.buttonA,binding.buttonB,binding.buttonC,binding.buttonD};
        boolean isCorrect = mViewModel.checkAnswers(buttons,buttonsChecked);
        for (int i = 0; i <buttons.length; i++) {
            Answer answer = (Answer) buttons[i].getTag();
            if(answer.getCorrect())buttons[i].setBackgroundColor(Color.GREEN);
        }
        if(isCorrect){
            Toasty.success(getContext(),"Poprawna odp",Toasty.LENGTH_SHORT,true).show();
        }
        else {
            Toasty.error(getContext(),"Bledna odp",Toast.LENGTH_SHORT).show();
        }
        binding.Check.setVisibility(View.GONE);
        binding.Next.setVisibility(View.VISIBLE);
    }

//    private boolean buttonA = false;
//    private boolean buttonB = false;
//    private boolean buttonC = false;
//    private boolean buttonD = false;
    @Override
    public void buttonClick(View view){
        switch (view.getId()){
            case R.id.buttonA: {
                if(buttonsChecked[0]){
                    binding.buttonA.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    buttonsChecked[0]=false;
                }
                else {
                    binding.buttonA.setBackgroundColor(getResources().getColor(R.color.buttonClicked));
                    buttonsChecked[0] = true;
                }
                break;
            }
            case R.id.buttonB: {
                if(buttonsChecked[1]){
                    binding.buttonB.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                    buttonsChecked[1]=false;
                }
                else {
                    buttonsChecked[1]=true;
                    binding.buttonB.setBackgroundColor(getResources().getColor(R.color.buttonClicked));
                }
                break;
            }
            case R.id.buttonC: {
                if(buttonsChecked[2]){
                    buttonsChecked[2]=false;
                    binding.buttonC.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                }
                else{
                    buttonsChecked[2]=true;
                    binding.buttonC.setBackgroundColor(getResources().getColor(R.color.buttonClicked));
                }
                break;
            }
            case R.id.buttonD: {
                if(buttonsChecked[3]){
                    buttonsChecked[3]=false;
                    binding.buttonD.setBackgroundColor(getResources().getColor(R.color.buttonColor));
                }
                else {
                    buttonsChecked[3]=true;
                    binding.buttonD.setBackgroundColor(getResources().getColor(R.color.buttonClicked));
                }
                break;
            }
        }
    }

    @Override
    public void buttonNext(View view) {
        mViewModel.nextQuestion();
        Button[] buttons = {binding.buttonA,binding.buttonB,binding.buttonC,binding.buttonD};
        for (int i = 0; i <buttons.length; i++) {
            buttons[i].setBackgroundColor(getResources().getColor(R.color.buttonColor));
        }
        for (int i = 0; i <buttonsChecked.length; i++) {
           buttonsChecked[i]=false;
        }
        binding.Next.setVisibility(View.GONE);
        binding.Check.setVisibility(View.VISIBLE);
    }
}