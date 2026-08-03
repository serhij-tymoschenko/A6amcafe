package dev.tymoshenko.a6amcafe.app.di

import dev.tymoshenko.a6amcafe.ui.screens.feed.MashiesFeedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel<MashiesFeedViewModel> {
        MashiesFeedViewModel(get())
    }
}