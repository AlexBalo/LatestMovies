package com.balocco.movies.common.extension

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

fun FragmentManager.replaceFragment(layoutId: Int,
                                    fragment: Fragment,
                                    addToBackStack: Boolean) {
    val transaction = beginTransaction()
    transaction.replace(layoutId, fragment)
    if (addToBackStack) {
        transaction.addToBackStack(fragment.tag)
    }
    transaction.commit()
}