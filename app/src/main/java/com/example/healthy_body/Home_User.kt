package com.example.healthy_body

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.util.ArrayList

class Home_User : AppCompatActivity() {

    private val test = 50
    private val test2 = 50
    private val peercenData = intArrayOf(test, test2)
    private val pnameFood = arrayOf("Food", "Workout")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home__user)
        setupPieChart()
    }

    private fun setupPieChart() {
        val pieEntries = ArrayList<PieEntry>()
        for (i in peercenData.indices) {
            pieEntries.add(
                PieEntry(
                    peercenData[i].toFloat(),
                    pnameFood[i]
                )
            )//ค่าที่เก็บจะต้องเป็น array
        }
        val dataSet = PieDataSet(pieEntries, "test")
        val colors = ArrayList<Int>()

        colors.add(Color.RED)
        colors.add(Color.BLUE)
        dataSet.colors = colors
        val data = PieData(dataSet)
        val chart = findViewById(R.id.chart) as PieChart
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.data = data
        chart.isDrawHoleEnabled = false
        chart.setCenterTextSizePixels(500f)
        data.setValueTextSize(30f);
        chart.animateY(500)
        chart.invalidate()
    }
}
