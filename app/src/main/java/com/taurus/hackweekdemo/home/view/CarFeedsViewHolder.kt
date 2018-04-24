package com.taurus.hackweekdemo.home.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.common.config.GservicesValue.init
import com.squareup.picasso.Picasso
import com.taurus.hackweekdemo.R
import com.taurus.hackweekdemo.core.extensions.into
import com.taurus.hackweekdemo.home.data.CarItem
import com.taurus.hackweekdemo.home.navigator.Navigator
import kotlinx.android.synthetic.main.car_feeds_list_item.view.*

internal class CarFeedsViewHolder(
        itemView: View,
        private val navigator: Navigator
) : RecyclerView.ViewHolder(itemView) {

    private val itemLayout: View = itemView.rootView
    private val carPhoto: ImageView = itemView.image_view_car_photo
    private val makeModelTextView: TextView = itemView.text_view_model_make
    private val descriptionTextView: TextView = itemView.text_view_description

    private lateinit var carItem: CarItem

    init {
        itemView.setOnClickListener {
            navigator.launchDetailActivity(it, carItem)
        }
    }

    fun render(carItem: CarItem) {

        this.carItem = carItem

        Picasso.with(itemView.context)
                .load(carItem.photoUrl)
                .into(carPhoto, {
                    onSuccess {
                        animateImageView()
                    }
                })

    }

    private fun animateImageView() {
        val anim = AnimationUtils.loadAnimation(itemView.context, R.anim.photo_fade_in)
        carPhoto.startAnimation(anim)
    }

}
