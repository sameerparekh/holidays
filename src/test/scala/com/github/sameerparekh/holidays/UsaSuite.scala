package com.github.sameerparekh.holidays

import com.github.nscala_time.time.Imports.{LocalDate, _}
import org.joda.time.DateTimeConstants.JANUARY
import org.scalatest.{FunSuite, MustMatchers}

class UsaSuite extends FunSuite with MustMatchers {
  def daysInYear(year: Int): Seq[LocalDate] = {
    def listDays(startDay: LocalDate): Stream[LocalDate] = {
      startDay #:: listDays(startDay + 1.day)
    }
    listDays(new LocalDate(year, JANUARY, 1)).takeWhile(_.getYear == year)
  }

  test("2018 USA holidays should be correct") {
    val holidays = daysInYear(2018).filter(_.isHoliday(USAFederalHolidays)).map(_.toString)

    holidays must contain theSameElementsAs Seq(
      "2018-01-01",
      "2018-01-15",
      "2018-02-19",
      "2018-05-28",
      "2018-07-04",
      "2018-09-03",
      "2018-10-08",
      "2018-11-12",
      "2018-11-22",
      "2018-12-25",
    )
  }

  test("USA 2019 Holidays should be correct") {
    val holidays = daysInYear(2019).filter(_.isHoliday(USAFederalHolidays)).map(_.toString)

    holidays must contain theSameElementsAs Seq(
      "2019-01-01",
      "2019-01-21",
      "2019-02-18",
      "2019-05-27",
      "2019-07-04",
      "2019-09-02",
      "2019-10-14",
      "2019-11-11",
      "2019-11-28",
      "2019-12-25",
    )
  }

}
