package com.lifotech.topurls

import org.scalatest.FunSuite


class UrlAnalyzerTest extends FunSuite {

  def getResource(r: String): String = com.google.common.io.Resources.getResource(r).getPath

  test("top 5 urls with highest impression count") {
    val fileNames: Array[String] = Array(getResource("file0.txt"), getResource("file1.txt"), getResource("file2.txt"))
    val topUrls: Vector[String] = new UrlAnalyzer().topUrlsWithHighestImpressionCount(fileNames, 5)

    val expectedTopUrls = Vector(
      "http://example.com/214-laborum-ullamco",
      "http://xyz.abc/28-qui-exercitation",
      "http://cnn.com",
      "http://yahoo.com",
      "http://xyz.com/30-excepteur-Lorem"
    )

    assert(topUrls == expectedTopUrls)


  }

  test("5 oldest urls") {
    val fileNames: Array[String] = Array(getResource("file0.txt"), getResource("file1.txt"), getResource("file2.txt"))
    val topOldUrls: Vector[String] = new UrlAnalyzer().topUrlsWithOlderUnixTs(fileNames, 5)

    val expectedOldUrls = Vector(
      "http://xyz.com/24-adipisicing-sunt",
      "http://cnn.com",
      "http://example.com/214-laborum-ullamco",
      "http://example.com/308-labore-ad",
      "http://example.com/271-minim-nisi")

    assert(topOldUrls == expectedOldUrls)
  }


}
