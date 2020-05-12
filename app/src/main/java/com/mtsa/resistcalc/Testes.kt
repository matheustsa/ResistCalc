package com.mtsa.resistcalc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mtsa.utils.Utils.coefVariacao
import com.mtsa.utils.Utils.desvioPadrao
import com.mtsa.utils.Utils.roundTo
import com.mtsa.utils.Utils.variancia
import kotlinx.android.synthetic.main.act__testes.*
import org.apache.commons.math3.distribution.FDistribution
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.POIFSFileSystem
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.FormulaEvaluator
import java.io.FileInputStream
import java.io.FileOutputStream
import kotlin.math.pow


class Testes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act__testes)

        val k = 6
        val n = 10
        val alfa = 0.05
        val gl1 = k-1
        val gl2 = k*(n-1)
        val glTotal = gl1+gl2

        val lista = listOf(
            listOf(20.4, 22.6, 23.4, 24.6, 22.4, 22.6, 34.6, 25.6, 26.1, 29.2),
            listOf(18.6, 18.9, 19.6, 19.2, 20.4, 24.6, 23.1, 22.1, 18.5, 19.1),
            listOf(18.6, 18.9, 19.6, 22.4, 22.6, 26.4, 27.4, 26.4, 26.4, 22.4),
            listOf(17.5, 18.5, 16.2, 14.3, 18.9, 19.6, 14.6, 22.5, 21.3, 19.5),
            listOf(21.4, 21.8, 22.6, 22.4, 22.6, 34.6, 26.8, 24.6, 24.6, 24.5),
            listOf(22.4, 22.6, 34.6, 24.6, 22.6, 23.6, 18.6, 18.9, 19.6, 24.6)
        )

        val media = mutableListOf<Double>()
        val desvioPadrao = mutableListOf<Double>()
        val variancia = mutableListOf<Double>()
        val coeficienteVariacao = mutableListOf<Double>()

        for ((index, T) in lista.withIndex()) {
            media.add(roundTo(T.average(), 4)!!)
            desvioPadrao.add(desvioPadrao(T))
            variancia.add(variancia(T))
            coeficienteVariacao.add(coefVariacao(media[index], desvioPadrao[index]))
        }

        val mediaGlobal = (lista.sumByDouble { it.sum() }/lista.size)/10

        val QM_Dentro = roundTo(variancia.sum()/k, 4)!!
//       N * (m0 - m_global)^2 + (m1 - m_global)^2 + ...

        var QM_Entre = 0.0
        for (value in media) {
            QM_Entre += (value - mediaGlobal).pow(2)
        }
        QM_Entre *= n
        QM_Entre = roundTo(QM_Entre, 4)!!

        val F_Calculado = roundTo(QM_Entre / QM_Dentro, 4)
        val F_Critico = FDistribution(gl1.toDouble(), gl2.toDouble()).inverseCumulativeProbability(1.0 - alfa)

        val results = "k: $k  -  n: $n  -  alfa: $alfa\n" +
                "gl1: $gl1  -  gl2: $gl2  -  glTotal: $glTotal\n" +
                "\n" +
                "media: $media\n" +
                "desvio padrão: $desvioPadrao\n" +
                "variância: $variancia\n" +
                "coef. variação: $coeficienteVariacao\n" +
                "\n" +
                "media global: $mediaGlobal\n" +
                "QM DENTRO: $QM_Dentro\n" +
                "QM ENTRE: $QM_Entre\n" +
                "\n" +
                "Fcalc: $F_Calculado\n" +
                "Fcrit: $F_Critico"

        actTestes_txvTeses.text = results
        print(results)


//        FUNCIONANDO
//        val filepath = "$filesDir/workbook.xls"
//        writeToExcelFile(filepath, alfa, gl1, gl2)
//        readFromExcelFile(filepath)

//        println("\n\nFiles List:\n")
//        val files: Array<String> = fileList()
//        println(files.asList())
//        println("\n\n")

    }


    private fun writeToExcelFile(
        filepath: String,
        alfa: Double,
        gl1: Int,
        gl2: Int
    ) {

        //Instantiate Excel workbook:
        val wb = HSSFWorkbook()
        FileOutputStream(filepath).use { fileOut -> wb.write(fileOut) }

        //Instantiate Excel worksheet:
        val ws = wb.createSheet()
        //Row index specifies the row in the worksheet (starting at 0):
        //Cell index specifies the column within the chosen row (starting at 0):

        //Write text value to cell located at ROW_NUMBER / COLUMN_NUMBER:
//        ws.createRow(row).createCell(column).setCellValue("TEST")
        ws.createRow(0).createCell(0).cellFormula = "F.INV.RT($alfa,$gl1,$gl2)"
//        ws.createRow(0).createCell(0).cellFormula = "SUM(1,2)"
        ws.getRow(0).getCell(0).setCellType(CellType.FORMULA)


        //Write file:
        val outputStream = FileOutputStream(filepath)
        wb.write(outputStream)
        wb.close()
    }

    /**
     * Reads the value from the cell at the first row and first column of worksheet.
     */
    private fun readFromExcelFile(filepath: String) {
        val inputStream = FileInputStream(filepath)
        //Instantiate Excel workbook using existing file:
        val wb = HSSFWorkbook(POIFSFileSystem(inputStream))


        //Row index specifies the row in the worksheet (starting at 0):
        val row = 0
        //Cell index specifies the column within the chosen row (starting at 0):
        val column = 0

        //Get reference to first sheet:
        val ws = wb.getSheetAt(0)
        val cell = ws.getRow(0).getCell(0)

        val evaluator: FormulaEvaluator = wb.creationHelper.createFormulaEvaluator()

        val content = evaluator.evaluate(cell)

        println(content.numberValue)

//        println(cell.numericCellValue)
//        println(cell.cellFormula)
    }

}


