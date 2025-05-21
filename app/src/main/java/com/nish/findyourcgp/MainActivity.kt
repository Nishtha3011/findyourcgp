package com.nish.findyourcgp


import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sub1Credits: EditText
    private lateinit var sub1Marks: EditText
    private lateinit var sub2Credits: EditText
    private lateinit var sub2Marks: EditText
    private lateinit var sub3Credits: EditText
    private lateinit var sub3Marks: EditText
    private lateinit var sub4Credits: EditText
    private lateinit var sub4Marks: EditText
    private lateinit var sub5Credits: EditText
    private lateinit var sub5Marks: EditText

    private lateinit var outputCGPA: TextView
    private lateinit var calcButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // <-- Replace with your layout file name here

        // Initialize views
        sub1Credits = findViewById(R.id.sub1credits)
        sub1Marks = findViewById(R.id.sub1marks)
        sub2Credits = findViewById(R.id.sub2credits)
        sub2Marks = findViewById(R.id.sub2marks)
        sub3Credits = findViewById(R.id.sub3credits)
        sub3Marks = findViewById(R.id.sub3marks)
        sub4Credits = findViewById(R.id.sub4credits)
        sub4Marks = findViewById(R.id.sub4marks)
        sub5Credits = findViewById(R.id.sub5credits)
        sub5Marks = findViewById(R.id.sub5marks)

        outputCGPA = findViewById(R.id.outputCGPA)
        calcButton = findViewById(R.id.outbtn)

        calcButton.setOnClickListener {
            calculateCGPA()
        }
    }

    private fun calculateCGPA() {
        val creditsList = listOf(
            sub1Credits.text.toString(),
            sub2Credits.text.toString(),
            sub3Credits.text.toString(),
            sub4Credits.text.toString(),
            sub5Credits.text.toString()
        )
        val marksList = listOf(
            sub1Marks.text.toString(),
            sub2Marks.text.toString(),
            sub3Marks.text.toString(),
            sub4Marks.text.toString(),
            sub5Marks.text.toString()
        )

        var totalCredits = 0.0
        var weightedGradePoints = 0.0

        for (i in 0..4) {
            val creditStr = creditsList[i]
            val marksStr = marksList[i]

            if (creditStr.isEmpty() || marksStr.isEmpty()) {
                Toast.makeText(this, "Please enter credits and marks for all subjects", Toast.LENGTH_SHORT).show()
                return
            }

            val credits = creditStr.toDoubleOrNull()
            val marks = marksStr.toDoubleOrNull()

            if (credits == null) {
                Toast.makeText(this, "Invalid credits input for Subject ${i + 1}", Toast.LENGTH_SHORT).show()
                return
            }
            if (credits <= 0) {
                Toast.makeText(this, "Credits must be greater than zero for Subject ${i + 1}", Toast.LENGTH_SHORT).show()
                return
            }

            if (marks == null) {
                Toast.makeText(this, "Invalid marks input for Subject ${i + 1}", Toast.LENGTH_SHORT).show()
                return
            }
            if (marks < 0 || marks > 100) {
                Toast.makeText(this, "Marks must be between 0 and 100 for Subject ${i + 1}", Toast.LENGTH_SHORT).show()
                return
            }

            val gradePoint = marksToGradePoint(marks)
            totalCredits += credits
            weightedGradePoints += gradePoint * credits
        }

        if (totalCredits == 0.0) {
            outputCGPA.text = "Total credits cannot be zero."
            return
        }

        val cgpa = weightedGradePoints / totalCredits
        outputCGPA.text = "Your CGPA is %.2f".format(cgpa)
    }

    private fun marksToGradePoint(marks: Double): Double {
        return when {
            marks >= 90 -> 10.0
            marks >= 80 -> 9.0
            marks >= 70 -> 8.0
            marks >= 60 -> 7.0
            marks >= 50 -> 6.0
            marks >= 40 -> 5.0
            else -> 0.0
        }
    }
}
