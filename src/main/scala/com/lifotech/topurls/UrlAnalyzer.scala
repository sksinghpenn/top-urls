package com.lifotech.topurls

/**
  * Url Analyzer class has utility functions   to find out various statistics URLs
  *
  * @author SK Singh
  * @since 1.0
  */
class UrlAnalyzer extends AbstractUrlAnalyzer {

  /**
    * Gets the top URLs with the highest impression count
    *
    * @param fileNames
    * @param topCount
    * @return
    */
  def topUrlsWithHighestImpressionCount(fileNames: Array[String], topCount: Int): Vector[String] = {
    val allLines = getLines(fileNames)
    topUrlsByImpressionCount(allLines, topCount)
  }

  /**
    * Gets the top count of the URLs
    *
    * @param allLines
    * @param topCount
    * @return
    */
  protected[this] def topUrlsByImpressionCount(allLines: Vector[String], topCount: Int): Vector[String] = {
    val urlCount: Map[String, Long] = urlCountMap(allLines)

    import scala.collection.mutable.LinkedHashMap
    val urlCountMapSorted = LinkedHashMap(urlCount.toSeq.sortBy(_._2).reverse: _*)
    urlCountMapSorted.take(topCount).keys.toVector

  }

  /**
    * Gets the oldest URLs based on the timestamp
    *
    * @param fileNames
    * @param olderCount
    * @return
    */
  def topUrlsWithOlderUnixTs(fileNames: Array[String], olderCount: Int): Vector[String] = {
    val allLines = getLines(fileNames)
    topOlderUrls(allLines, olderCount)
  }

  /**
    * Gets the top older URLs
    *
    * @param allLines
    * @param olderCount
    * @return
    */
  protected[this] def topOlderUrls(allLines: Vector[String], olderCount: Int): Vector[String] = {
    val urlListOrderByTimeStamp = UrlImpressionCountList(allLines).sortBy(_.timeStamp)

    val map = scala.collection.mutable.LinkedHashMap.empty[String, Long]

    urlListOrderByTimeStamp.foreach {
      line => if (!(map.contains(line.URL))) map += (line.URL -> line.timeStamp)
    }


    map.take(olderCount)
      .keys
      .toVector
  }

}

