package com.github.sameerparekh

import com.github.nscala_time.time.Imports._
import org.joda.time.DateTimeConstants._

import scala.util.{Failure, Success, Try}

package object holidays {
  type HolidayFromYear = Int => LocalDate
  type HolidaysForYear = Set[HolidayFromYear]

  implicit class RichLocalDate(date: LocalDate) {
    def isHoliday(implicit holidaysForYear: HolidaysForYear): Boolean = {
      holidaysForYear.map(_(date.getYear)).contains(date)
    }
  }

  val NewYears: HolidayFromYear = year => {
    moveToWeekday(new LocalDate(year, JANUARY, 1))
  }

  val MLKDay: HolidayFromYear = year => {
    getDayInRange(nthWeekdayOfMonthRange(3), MONDAY, JANUARY, year).get
  }

  val PresidentsDay: HolidayFromYear = year => {
    getDayInRange(nthWeekdayOfMonthRange(3), MONDAY, FEBRUARY, year).get
  }

  val FamilyDay: HolidayFromYear = year => {
    getDayInRange(nthWeekdayOfMonthRange(3), MONDAY, FEBRUARY, year).get
  }

  val VictoriaDay: HolidayFromYear = year => {
    getDayInRange(18 to 24, MONDAY, MAY, year).get
  }

  val MemorialDay: HolidayFromYear = year => {
    getDayInRange(lastWeekdayOfMonthRange(MAY, year), 1, 5, year).get
  }

  val CanadaDay: HolidayFromYear = year => {
    moveToWeekday(new LocalDate(year, JULY, 1))
  }

  val IndependenceDay: HolidayFromYear = year => {
    moveToWeekday(new LocalDate(year, JULY, 4))
  }

  val CivicHoliday: HolidayFromYear = year => {
    getDayInRange(nthWeekdayOfMonthRange(1), MONDAY, AUGUST, year).get
  }

  val LaborDay: HolidayFromYear = year => {
    getDayInRange(nthWeekdayOfMonthRange(1), MONDAY, SEPTEMBER, year).get
  }

  val ColumbusDay: HolidayFromYear = year => {
    getDayInRange(nthWeekdayOfMonthRange(2), MONDAY, OCTOBER, year).get
  }

  val VeteransDay: HolidayFromYear = year => {
    moveToWeekday(new LocalDate(year, NOVEMBER, 11))
  }

  val CanadianThanksgivingDay: HolidayFromYear = year => {
    getDayInRange(nthWeekdayOfMonthRange(2), MONDAY, OCTOBER, year).get
  }

  val RemembranceDay: HolidayFromYear = year => {
    moveToWeekday(new LocalDate(year, NOVEMBER, 11))
  }

  val ThanksgivingDay: HolidayFromYear = year => {
    getDayInRange(nthWeekdayOfMonthRange(4), THURSDAY, NOVEMBER, year).get
  }

  val AfterThanksgivingDay: HolidayFromYear = year => {
    ThanksgivingDay(year) + 1.day
  }

  val ChristmasEve: HolidayFromYear = year => {
    moveToWeekday(new LocalDate(year, DECEMBER, 24))
  }

  val ChristmasDay: HolidayFromYear = year => {
    moveToWeekday(new LocalDate(year, DECEMBER, 25))
  }

  val BoxingDay: HolidayFromYear = year => {
    moveToWeekday(new LocalDate(year, DECEMBER, 26))
  }

  val NewYearsEve: HolidayFromYear = year => {
    moveToWeekday(new LocalDate(year, DECEMBER, 31))
  }

  val USAFederalHolidays: HolidaysForYear =
    Set(
      NewYears,
      MLKDay,
      PresidentsDay,
      MemorialDay,
      IndependenceDay,
      LaborDay,
      ColumbusDay,
      VeteransDay,
      ThanksgivingDay,
      ChristmasDay,
    )

  /**
    * Given a range of dates within a specific month, return the date which is on a given
    * weekday.
    */
  private def getDayInRange(
    range: Seq[Int],
    weekDay: Int,
    month: Int,
    year: Int
  ): Try[LocalDate] = {
    val matches = range.map { day =>
      new LocalDate(year, month, day)
    }.collect {
      case dt if dt.getDayOfWeek == weekDay => dt
    }

    if (matches.size != 1) {
      Failure(new RuntimeException(s"Unable to find a $weekDay of $month in $range"))
    } else {
      Success(matches.head)
    }
  }

  /**
    * Given 'n', returns the range of dates which will contain the nth weekday of
    * the month.
    */
  private def nthWeekdayOfMonthRange(n: Int): Seq[Int] = {
    ((n - 1) * 7) + 1 to n * 7
  }

  /**
    * Given a specific month, return the last 7 dates in that month.r
    */
  private def lastWeekdayOfMonthRange(month: Int, year: Int): Seq[Int] = {
    val lastDayOfMonth = new LocalDate(year, month, 1)
      .dayOfMonth
      .withMaximumValue
      .getDayOfMonth

    (lastDayOfMonth - 6) to lastDayOfMonth
  }

  /**
    * Given a date, return the closest weekday (Monday-Friday)
    */
  private def moveToWeekday(dt: LocalDate): LocalDate = {
    // Move Saturdays to Friday and Sundays to Monday
    dt.getDayOfWeek match {
      case SUNDAY =>
        dt + 1.day
      case SATURDAY =>
        dt - 1.day
      case _ =>
        dt
    }
  }
}
