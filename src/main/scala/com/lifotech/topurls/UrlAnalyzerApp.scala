package com.lifotech.topurls

/**
  * Main driver class to run the [[UrlAnalyzer]]
  * Provide file names as arguments
  *
  * @author SK Singh
  * @since 1.0
  */
object UrlAnalyzerApp extends App {

  args match {
    case args if args.length > 0 => {
      val topUrls: Vector[String] = new UrlAnalyzer().topUrlsWithHighestImpressionCount(args, 100)
      println("********** Top 100 URLs **********")

      topUrls.foreach(println)

      println("")

      val oldUrls: Vector[String] = new UrlAnalyzer().topUrlsWithHighestImpressionCount(args, 100)
      println("********** Top 100 OLD URLs **********")
      oldUrls.foreach(println)

    }
    case _ => {
      println("no input files are provided. Please provide file names as arguments")
    }
  }


}
