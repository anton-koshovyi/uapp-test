package com.uapp_llc.test.comparator

import java.util.Comparator

class NotNullComparator<T> private constructor(

    private val leftNotNull: Boolean,

    private val rightNotNull: Boolean

) : Comparator<T> {

  override fun compare(left: T, right: T): Int {
    val result: Boolean =
        if (leftNotNull && rightNotNull) {
          (left != null) && (right != null)
        } else if (leftNotNull) {
          left != null
        } else {
          right != null
        }

    return if (result) 0 else 1
  }


  companion object {

    private val left: NotNullComparator<Any> = NotNullComparator(leftNotNull = true, rightNotNull = false)
    private val right: NotNullComparator<Any> = NotNullComparator(leftNotNull = false, rightNotNull = true)
    private val bot: NotNullComparator<Any> = NotNullComparator(leftNotNull = true, rightNotNull = true)

    fun <T> leftNotNull(): NotNullComparator<T> {
      return left as NotNullComparator<T>
    }

    fun <T> rightNotNull(): NotNullComparator<T> {
      return right as NotNullComparator<T>
    }

    fun <T> bothNotNull(): NotNullComparator<T> {
      return bot as NotNullComparator<T>
    }

  }

}