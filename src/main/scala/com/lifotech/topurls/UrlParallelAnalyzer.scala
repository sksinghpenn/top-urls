package com.lifotech.topurls

import java.util.concurrent.TimeUnit

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise}
import scala.util.{Failure, Success}

/**
  * Subclass of [[UrlAnalyzer]]. The class handles the processing in parallel  fashion.
  *
  * @author SK Singh
  * @since 1.0
  */
class UrlParallelAnalyzer extends UrlAnalyzer {

  // define the  timeout to process all the files
  val timeOutPeriod = Duration(1, TimeUnit.HOURS)

  override def topUrlsWithHighestImpressionCount(fileNames: Array[String], topCount: Int): Vector[String] = {
    val allLines = processFilesUsingFuture(fileNames)
    val topUrls = topUrlsByImpressionCount(allLines, topCount)
    topUrls
  }


  override def topUrlsWithOlderUnixTs(fileNames: Array[String], olderCount: Int): Vector[String] = {
    val allLines = processFilesUsingFuture(fileNames)
    topOlderUrls(allLines, olderCount)
  }


  /**
    * Function gets the all the lines in the input files by reading the files in parallel fashion
    * using [[scala.concurrent.Future]] and [[scala.concurrent.Promise]]
    *
    * The time out has been setup for an hour if the files could not be read in the given time out period.
    *
    * @param fileNames
    * @return
    */
  private def processFilesUsingFuture(fileNames: Array[String]): Vector[String] = {


    val listOfLines: ArrayBuffer[List[String]] = scala.collection.mutable.ArrayBuffer()

    // Promise is used to know if the processing of the file is complete by Future
    // Array is used to signal the processing complete for the multiple files
    val signal = new Array[Promise[String]](fileNames.size)

    val arraySize = fileNames.size



    // initialize the Promise object
    for (i <- 0 until arraySize) signal(i) = Promise[String]()


    for (i <- 0 until arraySize) yield {
      val fileName = fileNames(i)
      // read the each file asynchronously non-blocking
      val future = Future {
        scala.io.Source
          .fromFile(fileName)
          .getLines.toList
      }
      // use onComplete to update the promise object about the completion
      future.onComplete {
        case Success(map) => {
          listOfLines.append(map)
          signal(i).complete(Success("done"))
        }
        case Failure(e) => {
          throw new RuntimeException("Error in processing " + e.getMessage)
        }
      }
      // Await.ready to wait for each process (non-blocking) to complete
      // time out has been setup for an hour
      Await.ready(signal(i).future, timeOutPeriod)
    }

    listOfLines.flatten.toVector

  }


}
