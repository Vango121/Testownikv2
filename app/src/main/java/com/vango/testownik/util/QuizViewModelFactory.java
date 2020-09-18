package com.vango.testownik.util;

import com.vango.testownik.repository.Repository;
import com.vango.testownik.ui.QuizViewModel;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class QuizViewModelFactory implements ViewModelProvider.Factory {
    private Repository repository;
    @Inject
    public QuizViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(QuizViewModel.class)) {
            return (T) new QuizViewModel(repository,null);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
