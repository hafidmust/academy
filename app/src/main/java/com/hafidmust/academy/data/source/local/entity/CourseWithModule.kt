package com.hafidmust.academy.data.source.local.entity

import ModuleEntity
import androidx.room.Embedded
import androidx.room.Relation
import com.hafidmust.academy.data.CourseEntity

data class CourseWithModule (
    @Embedded
    var mCourse: CourseEntity,
    @Relation(parentColumn = "courseId", entityColumn = "courseId")
    var mModules: List<ModuleEntity>
)
