package com.lifotech.topurls

/**
  * Abstract class for analyzing URLs
  *
  * @author SK Singh
  * @since 1.0
  */
abstract class AbstractUrlAnalyzer {


  def getLines(fileNames: Array[String]): Vector[String] = {
    val allLines = fileNames.flatMap { fileName =>
      scala.io.Source
        .fromFile(fileName)
        .getLines
    }

    allLines.toVector
  }


  /**
    * Gets map of URL and its impression count
    *
    * @param allLines containing URL, impression count and timestamp
    * @return 
    */
  def urlCountMap(allLines: Vector[String]): Map[String, Long] = {


    val impressionCountList = UrlImpressionCountList(allLines)

    val impressionCountGroupByURLList = impressionCountList.groupBy(_.URL)

    impressionCountGroupByURLList.mapValues {
      _.map(_.impressionCount).sum
    }
  }


  /**
    * Gets list of [[UrlImpressionCount]] objects
    *
    * @param allLines
    * @return
    */
  def UrlImpressionCountList(allLines: Vector[String]): Vector[UrlImpressionCount] = {
    val lineParts = allLines.map {
      _.split(",")
    }

    lineParts.map {
      line => UrlImpressionCount(line(0), line(1).toLong, line(2).toLong)
    }
  }


}

/** Case class for modelling Url Impression Count */
case class UrlImpressionCount(val URL: String, val impressionCount: Long, val timeStamp: Long)
