package com.stefanini.chucknorrisio.presentation

import com.stefanini.chucknorrisio.Colors
import com.stefanini.chucknorrisio.MainActivity
import com.stefanini.chucknorrisio.datasource.CategoryRemoteDataSource
import com.stefanini.chucknorrisio.model.CategoryItem


class CategoryPresenter(mainActivity: MainActivity, data: CategoryRemoteDataSource) : CategoryRemoteDataSource.ListCategoriesCallback {

     var view: MainActivity = mainActivity
     var dataSource: CategoryRemoteDataSource = data

    fun requestAll() {
        this.view.showProgessBar()
        this.dataSource.findAll(this)
    }


    override fun onSucess(response: List<String>?) {
        val categoryItems : MutableList<CategoryItem> = ArrayList()
        if (response != null) {
            for (vai in response) {
                categoryItems.add(CategoryItem(vai, Colors.randomColor()))
            }
        }

        view.showCategories(categoryItems)

    }

    override fun onError(message: String?) {
        if (message != null) {
            view.showFailure(message)
        }
    }


    override fun onComplete(){
        view.stopProgressBar()
    }



}