package com.stefanini.chucknorrisio.presentation

import com.stefanini.chucknorrisio.JokeActivity
import com.stefanini.chucknorrisio.datasource.JokeRemoteDataSource
import com.stefanini.chucknorrisio.model.Joke

class JokePresenter(jokeActivity: JokeActivity,  data: JokeRemoteDataSource): JokeRemoteDataSource.JokeCallback {

    var view: JokeActivity = jokeActivity
    var dataSource: JokeRemoteDataSource = data

    fun findJokeBy(category: String) {
        this.view.showProgessBar()
        this.dataSource.findJokeBy(this, category)
    }

    override fun onSucess(response: Joke?) {
        if (response != null) {
            view.showJoke(response)
        }
    }

    override fun onError(message: String?) {
        if (message != null) {
            view.showFailure(message)
        }
    }

    override fun onComplete() {
        view.stopProgressBar()
    }

}
