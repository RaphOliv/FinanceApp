package com.hacksprint.financeapp.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.hacksprint.financeapp.R

class Presentation: AppCompatActivity() {
    private lateinit var indicator: LinearLayout
    private lateinit var skip: Button
    private lateinit var btnNext: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.presentation)


        val viewPager: ViewPager2 = findViewById(R.id.vp_presentation)
        indicator = findViewById(R.id.indicator_presentation)
        skip = findViewById(R.id.btn_presentation_skip)
        btnNext = findViewById(R.id.btn_presentation)

        //configura o viewpager2 com o adapter
        val adapter = FrameAdapter(this)
        viewPager.adapter = adapter

        //registra as mudaças de tela dos fragments
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicator(position)

                if (position == adapter.itemCount - 1) {
                    skip.visibility = View.INVISIBLE
                    indicator.visibility = View.INVISIBLE
                    btnNext.text = "Salvar"
                }else if(position ==3){
                    btnNext.text = "Iniciar"
                } else {
                    skip.visibility = View.VISIBLE
                }
            }

        })

        skip.setOnClickListener{
            val currentItem = viewPager.currentItem
            val lastaitem = viewPager.adapter?.itemCount?.minus(1)

            if (currentItem < lastaitem!!) {
                viewPager.currentItem = lastaitem
            }
        }

        btnNext.setOnClickListener{
            val currentItem = viewPager.currentItem
            val lastaitem = viewPager.adapter?.itemCount?.minus(1)

            if (currentItem == lastaitem) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else{
                viewPager.setCurrentItem(currentItem + 1, true)
            }
        }

        val numPages = adapter.itemCount
        for (i in 0 until numPages) {
            val circleIndicator = ImageView(this)
            val Params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(8, 0, 8, 0)
            }
            circleIndicator.layoutParams = Params
            circleIndicator.setImageResource(
                if (i == 0) R.drawable.circle_indicator_selecionado else R.drawable.circle_indicator_n_selecionado
            )
            indicator.addView(circleIndicator)
        }
    }

    private fun updateIndicator(position: Int) {
        // Limpa todos os indicadores
        for (i in 0 until indicator.childCount) {
            val indicatorView = indicator.getChildAt(i) as ImageView
            indicatorView.setImageResource(R.drawable.circle_indicator_n_selecionado)
        }

        // Define o indicador atual como selecionado
        val selectedIndicatorView = indicator.getChildAt(position) as ImageView
        selectedIndicatorView.setImageResource(R.drawable.circle_indicator_selecionado)
    }



}