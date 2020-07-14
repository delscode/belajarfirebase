package com.phdb.suksesdbfirebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class  HeroAdapter(val mCtx: Context, val layoutResId : Int, val heroList: List<Hero>)
    : ArrayAdapter<Hero> (mCtx, layoutResId, heroList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater : LayoutInflater = LayoutInflater.from(mCtx)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val textViewName = view.findViewById<TextView>(R.id.tvHeroName)
        val hero = heroList[position]

        textViewName.text = hero.name

        return view
    }
}