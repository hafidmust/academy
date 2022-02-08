package com.hafidmust.academy.ui.reader

interface CourseReaderCallback {
    fun moveTo(position : Int, moduleId : String)
}