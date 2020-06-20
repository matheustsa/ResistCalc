package com.mtsa.fragments

import android.R
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Column
import com.anychart.enums.Anchor
import com.anychart.enums.HoverMode
import com.anychart.enums.Position
import com.anychart.enums.TooltipPositionMode
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.mtsa.resistcalc.DIC2
import com.mtsa.resistcalc.databinding.FragDicGraficosBinding


class FragDICGraficos : Fragment() {
    private lateinit var binding: FragDicGraficosBinding
    private lateinit var fragmentInterface: FragmentInterface
    private lateinit var dic: DIC2

    interface FragmentInterface {
        fun getDICFromActivity(): DIC2
    }

    private fun getDIC(): DIC2 {
        return fragmentInterface.getDICFromActivity()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentInterface)
            fragmentInterface = context
        else
            throw ClassCastException()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragDicGraficosBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        setAnyChart()
        setAnyChart2()
//        setMPChart()

    }

    private fun setAnyChart() {
        dic = getDIC()

        val chart = binding.anyChartView
        chart.visibility = View.VISIBLE

        val barChart = AnyChart.bar()
        val dataEntry: MutableList<DataEntry> = ArrayList()

        for ((index, value) in dic.media.withIndex()) {
            dataEntry.add(ValueDataEntry("T${index+1}", value))
        }
        barChart.data(dataEntry.reversed())
        barChart.isVertical(false)

        chart.setChart(barChart)
    }

    private fun setAnyChart2() {
        dic = getDIC()

        val chart = binding.anyChartView
        chart.visibility = View.VISIBLE

        val cartesian: Cartesian = AnyChart.column()

        val data: MutableList<DataEntry> = ArrayList()
        for ((index, value) in dic.media.withIndex()) {
            data.add(ValueDataEntry("T${index+1}", value))
        }

        val column: Column = cartesian.column(data)

        column.tooltip()
            .titleFormat("{%X}")
            .position(Position.CENTER_BOTTOM)
            .anchor(Anchor.CENTER_BOTTOM)
            .offsetX(0.0)
            .offsetY(5.0)
            .format("\${%Value}{groupsSeparator: }")

        cartesian.animation(true)
        cartesian.title("Média dos Tratamentos")

        cartesian.yScale().minimum(0.0)

//        cartesian.yAxis(0).labels().format("\${%Value}{groupsSeparator: }")

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
        cartesian.interactivity().hoverMode(HoverMode.BY_X)

        cartesian.xAxis(0).title("Tratamentos")
        cartesian.yAxis(0).title("Média")

        cartesian

        chart.setChart(cartesian)
    }

    private fun setMPChart() {
        val chart = binding.MPChart

        chart.visibility = View.VISIBLE

        val entries = ArrayList<BarEntry>()
        entries.add(BarEntry(1f, 0f))
        entries.add(BarEntry(2f, 1f))
        entries.add(BarEntry(3f, 2f))
        entries.add(BarEntry(4f, 3f))
        entries.add(BarEntry(5f, 4f))
        entries.add(BarEntry(6f, 5f))

        val barDataSet = BarDataSet(entries, "Tratamentos")

        val labels = ArrayList<String>()
        labels.add("T1")
        labels.add("T2")
        labels.add("T3")
        labels.add("T4")
        labels.add("T5")
        labels.add("T6")

        val data = BarData(barDataSet)

        chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        chart.xAxis.granularity = 1f
        chart.xAxis.isGranularityEnabled = true
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE;

        chart.data = data

        val description: Description = chart.description
        description.isEnabled = true
        description.text = "CHART DESCRIPTION"

//        barDataSet.color = Color.BLUE


        chart.animateY(500)
    }
}