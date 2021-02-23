package com.example.yemeksiparisuygulamasi

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        animationView.setAnimation("splash_animation.json")
        animationView.playAnimation()
        animationView.addAnimatorListener( object : Animator.AnimatorListener{
            override fun onAnimationStart(animation: Animator?) {
                animationView.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?) {
                animationView.animate().alpha(0.0f).setDuration(1000)
                animationView.visibility = View.GONE

                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
//
            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }

        })
    }
}