# Holidays 

[![Build Status](https://travis-ci.org/sameerparekh/holidays.svg?branch=master)](https://travis-ci.org/sameerparekh/holidays)

A simple library to check if a date is a holiday.

## Installation

Add the following to your sbt build (Scala 2.12.x):

```scala
libraryDependencies += "com.github.sameerparekh" %% "holidays" % "0.1.0"
```

## Usage

* Import the library.
* Configure an implicit holiday list (`HolidaysForYear`)
* Check your `LocalDate` to see if it is a holiday.

```scala
import com.github.sameerparekh.holidays._
import org.joda.time.LocalDate

implicit val holidayList: HolidaysForYear = USAFederalHolidays

println {
  if (LocalDate.now().isHoliday) "Today is a holiday" 
  else "Today is not a holiday"
}
```

## Extension

Pull requests welcome.

### Add a holiday

* Create a new instance of a `HolidayFromYear`, which is a function from a year to a `LocalDate`. A
number of helper functions are available.

### Add a new holiday list

* Create a new instance of a `HolidaysForYear`, which is a set of `HolidayFromYear`.